package com.yulicahyani.eraport.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.yulicahyani.eraport.data.source.local.entity.UserEntity
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.LoginResponse
import com.yulicahyani.eraport.databinding.ActivityLoginBinding
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    lateinit var prefHelper: PrefHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
        supportActionBar?.hide()
        showProgressBar(false)

        prefHelper = PrefHelper(this)

        activityLoginBinding.btnLogin.setOnClickListener {
            showProgressBar(true)
            if (TextUtils.isEmpty(activityLoginBinding.usernameEt.text.toString())) {
                activityLoginBinding.usernameEt.error = "Please enter username"
                showProgressBar(false)
                return@setOnClickListener
            } else if (TextUtils.isEmpty(activityLoginBinding.etPassword.text.toString())) {
                activityLoginBinding.etPassword.error = "Please enter password"
                showProgressBar(false)
                return@setOnClickListener
            }

            val client = ApiConfig.getApiService().login(activityLoginBinding.usernameEt.text.toString(), activityLoginBinding.etPassword.text.toString())
            client.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ){
                    if (response.isSuccessful) {
                        showProgressBar(false)
                        Log.e("LoginActivity", response.toString())
                        if(response.body()?.status == 1){
                            Toast.makeText(
                                this@LoginActivity,
                                response.body()?.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()

                            val userResponse = response.body()?.user?.get(0)
                            val user = UserEntity(
                                id_user = userResponse?.id_user?.toIntOrNull(),
                                id_sekolah = userResponse?.id_sekolah?.toIntOrNull(),
                                email = userResponse?.email.toString(),
                                username = userResponse?.username.toString(),
                                password = userResponse?.password.toString(),
                                firstname = userResponse?.firstname.toString(),
                                lastname = userResponse?.lastname.toString(),
                                role = userResponse?.role.toString(),
                                nama_sekolah =  userResponse?.nama_sekolah.toString(),
                                alamat_sekolah =  userResponse?.alamat.toString(),
                            )

                            saveSession(user)
                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }else{
                            Toast.makeText(
                            this@LoginActivity,
                                response.body()?.message.toString(),
                            Toast.LENGTH_LONG).show()
                        }

                    } else {
                        Log.e("", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("", "onFailure: ${t.message.toString()}")
                }

            })

        }
    }


    override fun onStart() {
        super.onStart()
        if (prefHelper.getBoolean( Constant.PREF_IS_LOGIN )) {
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun saveSession(user : UserEntity){
        prefHelper.put( Constant.PREF_ID_USER, user.id_user)
        prefHelper.put( Constant.PREF_ID_SEKOLAH, user.id_sekolah)
        prefHelper.put( Constant.PREF_EMAIL, user.email)
        prefHelper.put( Constant.PREF_USERNAME, user.username )
        prefHelper.put( Constant.PREF_PASSWORD, user.password )
        prefHelper.put( Constant.PREF_FIRSTNAME, user.firstname )
        prefHelper.put( Constant.PREF_LASTNAME, user.lastname )
        prefHelper.put( Constant.PREF_ROLE, user.role )
        prefHelper.put( Constant.PREF_SEKOLAH, user.nama_sekolah )
        prefHelper.put( Constant.PREF_ALAMAT_SEKOLAH, user.alamat_sekolah )
        prefHelper.put( Constant.PREF_IS_LOGIN, true)
    }


    private fun showProgressBar(state: Boolean) {
        if (state) {
            activityLoginBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityLoginBinding.progressBar.visibility = View.GONE
        }
    }
}
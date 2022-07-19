package com.yulicahyani.eraport.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.ui.login.LoginActivity


class SplashScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
              if (onBoardingFinished()){
                  val intent = Intent(activity, LoginActivity::class.java)
                  startActivity(intent)
                  activity?.finish()
              }else{
                  findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
              }
        }, 3000)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}
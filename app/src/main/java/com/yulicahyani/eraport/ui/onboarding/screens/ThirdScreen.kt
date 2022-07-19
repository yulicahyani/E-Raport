package com.yulicahyani.eraport.ui.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.FragmentThirdScreenBinding
import com.yulicahyani.eraport.ui.login.LoginActivity


class ThirdScreen : Fragment() {
    private lateinit var thirdScreenBinding: FragmentThirdScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        thirdScreenBinding = FragmentThirdScreenBinding.inflate(layoutInflater, container, false)

        thirdScreenBinding.finish.setOnClickListener {
            onOboardingFinished()
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginActivity)
            activity?.finish()
        }
        return thirdScreenBinding.root
    }

    private fun onOboardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}
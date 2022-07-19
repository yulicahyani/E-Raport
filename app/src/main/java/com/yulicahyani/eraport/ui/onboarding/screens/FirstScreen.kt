package com.yulicahyani.eraport.ui.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.FragmentFirstScreenBinding
import com.yulicahyani.eraport.databinding.FragmentViewPagerBinding

class FirstScreen : Fragment() {
    private lateinit var firstScreenBinding: FragmentFirstScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firstScreenBinding = FragmentFirstScreenBinding.inflate(layoutInflater, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        firstScreenBinding.nextone.setOnClickListener {
            viewPager?.currentItem = 1
        }
        firstScreenBinding.skipOne.setOnClickListener {
            onOboardingFinished()
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginActivity)
            activity?.finish()
        }
        return firstScreenBinding.root
    }

    private fun onOboardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}
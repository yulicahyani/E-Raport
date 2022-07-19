package com.yulicahyani.eraport.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.FragmentViewPagerBinding
import com.yulicahyani.eraport.ui.onboarding.screens.FirstScreen
import com.yulicahyani.eraport.ui.onboarding.screens.SecondScreen
import com.yulicahyani.eraport.ui.onboarding.screens.ThirdScreen


class ViewPagerFragment : Fragment() {
    private lateinit var fragmentViewPager: FragmentViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentViewPager = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle)
        fragmentViewPager.viewPager.adapter = adapter

        return fragmentViewPager.root
    }

}
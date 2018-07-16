package com.example.dyslexia.art

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> IntroLayout()
            1 -> IntroLayout2()
            2 -> IntroLayout3()
            3 -> IntroLayout4()
            else -> {
                return IntroLayout5()
            }
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "所長的話"
            1 -> "歷史沿革"
            2 -> "重要事紀"
            3 -> "宗旨展望"
            else -> {
                return "發展計畫"
            }
        }
    }

}
package com.example.coolcalculator

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()!!.hide()



        val adapter = MyViewPagerAdapter(supportFragmentManager)


        adapter.addFragment(EngiCalcActivity())
        adapter.addFragment(Menu())
        adapter.addFragment(GraphActivity())
        (viewPager as ViewPager).adapter = adapter
        viewPager.setCurrentItem(1)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                    1 -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    2 -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            }
        })
    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){

        private val fragmentList : MutableList<Fragment> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment){
            fragmentList.add(fragment)
        }


    }

}

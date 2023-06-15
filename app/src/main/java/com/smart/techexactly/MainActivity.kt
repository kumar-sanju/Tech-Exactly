package com.smart.techexactly

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.smart.techexactly.adapters.ViewPagerAdapter
import com.smart.techexactly.fragments.MyFragmentFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout=findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2=findViewById<ViewPager2>(R.id.view_pager_2)

        val adapter= ViewPagerAdapter(supportFragmentManager,lifecycle)
        viewPager2.adapter=adapter

        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Applications"
//                    return MyFragmentFragment.newInstance(Applications)
                }
                1->{
                    tab.text="Settings"
                }
            }
        }.attach()


    }
}
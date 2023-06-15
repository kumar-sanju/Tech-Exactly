package com.smart.techexactly.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smart.techexactly.fragments.MyFragmentFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return   when(position){
            0->{
                MyFragmentFragment()
//                return MyFragmentFragment.newInstanceValue("1", "Applications")
            }
            1->{
                MyFragmentFragment()
            }
            else->{
                Fragment()
            }

        }
    }
}
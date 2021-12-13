package com.example.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.animation.databinding.ActivityScreenSlidePagerAcitivtyBinding
import com.google.android.material.tabs.TabLayoutMediator

class ScreenSlidePagerActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ScreenSlidePagerActiv"
        private const val NUM_PAGES = 5
    }

    private lateinit var binding: ActivityScreenSlidePagerAcitivtyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_screen_slide_pager_acitivty)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.activityScreenSlidePagerViewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.activityScreenSlidePagerTabLayout, binding.activityScreenSlidePagerViewPager) { tab, position ->
            tab.text = "Fragment ${(position + 1)}"
        }.attach()

    }

    private inner class ScreenSlidePagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            Log.d(TAG, "getItemCount")
            return NUM_PAGES
        }

        override fun createFragment(position: Int): Fragment {
            Log.d(TAG, "createFragment: position = $position")
            return SlideScreenPagerFragment.newInstance(position + 1)
        }
    }
}
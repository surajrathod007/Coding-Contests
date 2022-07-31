package com.surajrathod.codingcontests.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.surajrathod.codingcontests.R
import com.surajrathod.codingcontests.databinding.ActivityHomeBinding
import com.surajrathod.codingcontests.util.ViewPagerAdapter
import nl.joery.animatedbottombar.AnimatedBottomBar

class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)

        val fragList : ArrayList<Fragment> = arrayListOf(
            AllContestFragment(),
            ContestCategoryFragment(),
            SavedContestFragment()
        )

        binding.viewPager.adapter = ViewPagerAdapter(fragList,this)
        binding.viewPager.currentItem = 1

        binding.bottomBar.setupWithViewPager2(binding.viewPager)

//        binding.bottomBar.onTabSelected = {
//            when(it.id){
//                R.id.tab_home -> {
//                    binding.viewPager.currentItem = 0
//                }
//                R.id.tab_category -> {
//                    binding.viewPager.currentItem = 1
//                }
//                R.id.tab_saved -> {
//                    binding.viewPager.currentItem = 2
//                }
//            }
//        }



    }


}
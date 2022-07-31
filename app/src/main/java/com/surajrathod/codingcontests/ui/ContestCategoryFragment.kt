package com.surajrathod.codingcontests.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.surajrathod.codingcontests.ContestListActivity
import com.surajrathod.codingcontests.R
import com.surajrathod.codingcontests.databinding.FragmentContestCategoryBinding
import com.surajrathod.codingcontests.model.Site


class ContestCategoryFragment : Fragment(),View.OnClickListener{


    lateinit var binding : FragmentContestCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_contest_category,container,false)


        //adding click listners

        binding.apply {
            siteAtCoder.setOnClickListener(this@ContestCategoryFragment)
            siteCodeChef.setOnClickListener(this@ContestCategoryFragment)
            siteHackerEarth.setOnClickListener(this@ContestCategoryFragment)
            siteHackerRank.setOnClickListener(this@ContestCategoryFragment)
            siteSpoj.setOnClickListener(this@ContestCategoryFragment)
            siteCodeForces.setOnClickListener(this@ContestCategoryFragment)
            siteTopCoder.setOnClickListener(this@ContestCategoryFragment)
            siteCsAcademy.setOnClickListener(this@ContestCategoryFragment)
            siteLeetCode.setOnClickListener(this@ContestCategoryFragment)
        }

        return binding.root
    }

    override fun onClick(view: View?) {

        when(view?.id){

            R.id.siteLeetCode -> {
                val intent = Intent(this.activity, ContestListActivity::class.java)
                intent.putExtra("all",false)
                intent.putExtra("site", Site.LEETCODE)
                startActivity(intent)
            }
            R.id.siteHackerRank -> {
                val intent = Intent(this.activity, ContestListActivity::class.java)
                intent.putExtra("all",false)
                intent.putExtra("site", Site.HACKERRANK)
                startActivity(intent)
            }
            R.id.siteAtCoder -> {
                val intent = Intent(this.activity, ContestListActivity::class.java)
                intent.putExtra("all",false)
                intent.putExtra("site", Site.ATCODER)
                startActivity(intent)
            }
            R.id.siteCodeChef -> {
                val intent = Intent(this.activity, ContestListActivity::class.java)
                intent.putExtra("all",false)
                intent.putExtra("site", Site.CODECHEF)
                startActivity(intent)
            }
            R.id.siteCodeForces -> {
                val intent = Intent(this.activity, ContestListActivity::class.java)
                intent.putExtra("all",false)
                intent.putExtra("site", Site.CODEFORCES)
                startActivity(intent)
            }
            R.id.siteCsAcademy -> {
                val intent = Intent(this.activity, ContestListActivity::class.java)
                intent.putExtra("all",false)
                intent.putExtra("site", Site.CSACADEMY)
                startActivity(intent)
            }
            R.id.siteHackerEarth -> {
                val intent = Intent(this.activity, ContestListActivity::class.java)
                intent.putExtra("all",false)
                intent.putExtra("site", Site.HACKEREARTH)
                startActivity(intent)
            }
            R.id.siteSpoj -> {
                val intent = Intent(this.activity, ContestListActivity::class.java)
                intent.putExtra("all",false)
                intent.putExtra("site", Site.CODEFORCES_GYM)
                startActivity(intent)
            }
            R.id.siteTopCoder -> {
                val intent = Intent(this.activity, ContestListActivity::class.java)
                intent.putExtra("all",false)
                intent.putExtra("site", Site.TOPCODER)
                startActivity(intent)
            }



        }
    }


}
package com.surajrathod.codingcontests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.surajrathod.codingcontests.adapter.ContestAdapter
import com.surajrathod.codingcontests.db.ContestDatabase
import com.surajrathod.codingcontests.model.Contest
import com.surajrathod.codingcontests.model.ContestList
import com.surajrathod.codingcontests.repo.ContestRepo
import com.surajrathod.codingcontests.viewmodel.ContestViewModel
import com.surajrathod.codingcontests.viewmodel.ContestViewModelFactory

class ContestListActivity : AppCompatActivity() {

    lateinit var viewModel : ContestViewModel
    var repo = ContestRepo()
    //var rv = findViewById<RecyclerView>(R.id.rvContest)
    lateinit var rv : RecyclerView
    lateinit var contestDb : ContestDatabase
    lateinit var title : TextView
    lateinit var toolbar : Toolbar
    lateinit var lottie_not_found : LottieAnimationView
    lateinit var loading : LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contest_list)

        toolbar = findViewById(R.id.toolbar)
        title = findViewById(R.id.txtToolbarTitle)
        lottie_not_found = findViewById(R.id.not_found)
        loading = findViewById(R.id.loading_list)

        contestDb = ContestDatabase.getDatabase(this)
        val site : String? = intent.getStringExtra("site")
        val all = intent.getBooleanExtra("all",true) as Boolean
        rv = findViewById(R.id.rvContest)
        rv.setHasFixedSize(true)
        viewModel = ViewModelProvider(this,ContestViewModelFactory(repo)).get(ContestViewModel::class.java)


        title.text = site
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)



        repo.mutableProgress.observe(this,{
            if(!it){
                loading.visibility = View.GONE
            }
        })
        if(all){
            viewModel.getAllSites().observe(this,{
                rv.adapter = ContestAdapter(it,this@ContestListActivity,contestDb)
            })
        }else{
            viewModel.getAllSites().observe(this,{

                if(it.filter { it.site == site }.isEmpty()){
                    lottie_not_found.visibility = View.VISIBLE
                    Toast.makeText(this,"No Contest On $site",Toast.LENGTH_LONG).show()

                }else{
                    rv.adapter = ContestAdapter(it.filter { it.site == site },this@ContestListActivity,contestDb)
                }

            })

        }



    }

    override fun onSupportNavigateUp(): Boolean {
        //return super.onSupportNavigateUp()
        this.finish()
        return true
    }
}
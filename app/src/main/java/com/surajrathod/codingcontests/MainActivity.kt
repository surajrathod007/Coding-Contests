package com.surajrathod.codingcontests

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.surajrathod.codingcontests.adapter.ContestAdapter
import com.surajrathod.codingcontests.databinding.ActivityMainBinding
import com.surajrathod.codingcontests.db.ContestDatabase
import com.surajrathod.codingcontests.model.Site
import com.surajrathod.codingcontests.repo.ContestRepo
import com.surajrathod.codingcontests.ui.channelID
import com.surajrathod.codingcontests.ui.notificationID
import com.surajrathod.codingcontests.util.SetAlarm
import com.surajrathod.codingcontests.viewmodel.ContestViewModel
import com.surajrathod.codingcontests.viewmodel.ContestViewModelFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : ContestViewModel
    val repo = ContestRepo()


    lateinit var p : ProgressDialog
    lateinit var contestdb : ContestDatabase

    //var msg = ""

    lateinit var binding : ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        p = ProgressDialog(this)
        contestdb = ContestDatabase.getDatabase(this)
        viewModel = ViewModelProvider(this,ContestViewModelFactory(repo)).get(ContestViewModel::class.java)

        binding.rvAllContest.setHasFixedSize(true)

        binding.rvHackerRank.setHasFixedSize(true)

        loaddata()







        binding.txtMore.setOnClickListener {
            val intent = Intent(this@MainActivity,ContestListActivity::class.java)
            intent.putExtra("all",true)
            startActivity(intent)

           // SetAlarm(this).sendNotification("Hii","Sorry","2024-06-17T13:36:00.000Z")
        }

        binding.txtHackerRank.setOnClickListener {
            val intent = Intent(this@MainActivity,ContestListActivity::class.java)
            intent.putExtra("all",false)
            intent.putExtra("site",Site.HACKERRANK)
            startActivity(intent)

//            val check = (PendingIntent.getBroadcast(
//                applicationContext,
//                notificationID,
//                intent,
//                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//            )!= null)
//
//            Toast.makeText(this,"$check",Toast.LENGTH_LONG).show()
        }

        binding.txtCodeForces.setOnClickListener {
            val intent = Intent(this@MainActivity,ContestListActivity::class.java)
            intent.putExtra("all",false)
            intent.putExtra("site",Site.CODEFORCES)
            startActivity(intent)
        }


    }

    fun loaddata(){

        viewModel.getAllSites().observe(this,{
            binding.rvAllContest.adapter = ContestAdapter(it.subList(0,3),this@MainActivity,contestdb)
            binding.rvHackerRank.adapter = ContestAdapter(it.filter { it.site == Site.HACKERRANK },this@MainActivity,contestdb)
            binding.rvCodeForces.adapter = ContestAdapter(it.filter { it.site == Site.CODEFORCES || it.site == Site.CODEFORCES_GYM},this@MainActivity,contestdb)
        })

        if(p.isShowing){
            p.hide()
        }


    }

    override fun onStart() {
        super.onStart()

    }


}
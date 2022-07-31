package com.surajrathod.codingcontests.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.surajrathod.codingcontests.ContestListActivity
import com.surajrathod.codingcontests.R
import com.surajrathod.codingcontests.databinding.ContestItemBinding
import com.surajrathod.codingcontests.db.ContestDatabase
import com.surajrathod.codingcontests.db.ContestEntity
import com.surajrathod.codingcontests.model.Contest
import com.surajrathod.codingcontests.model.Site
import com.surajrathod.codingcontests.util.SetAlarm
import kotlinx.coroutines.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class ContestAdapter(val data: List<Contest>, val context: Context, val db: ContestDatabase) :
    RecyclerView.Adapter<ContestAdapter.ViewHolder>() {



    inner class ViewHolder(val binding: ContestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var txtLive: TextView = binding.txtStatus
        var txtStartDate: TextView = binding.txtStartdate
        var txtEndDate: TextView = binding.txtEndDate
        var imgSite: ImageView = binding.imgSite
        var imgAlarm: ImageView = binding.imgSetAlarm

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Contest) {
            binding.contest = item

            txtStartDate.text = Site.getNewDate(item.start_time, context)
            txtEndDate.text = Site.getNewDate(item.end_time, context)

            CoroutineScope(Dispatchers.IO).launch {
                if (db.contestDao().isExists(item.name,item.start_time)) {
                    withContext(Dispatchers.Main) {
                        imgAlarm.setImageResource(R.drawable.ic_baseline_alarm_on_24)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        imgAlarm.setImageResource(R.drawable.ic_baseline_add_alert_24)
                    }
                }
            }


            when (item.site) {
                Site.HACKERRANK -> {
                    imgSite.setImageResource(R.drawable.site_hackerrank)
                }
                Site.CODECHEF -> {
                    imgSite.setImageResource(R.drawable.site_codechef)
                }
                Site.ATCODER -> {
                    imgSite.setImageResource(R.drawable.site_atcoder)
                }
                Site.CODEFORCES -> {
                    imgSite.setImageResource(R.drawable.site_codeforces)
                }
                Site.CODEFORCES_GYM -> {
                    imgSite.setImageResource(R.drawable.site_codeforces)
                }
                Site.CSACADEMY -> {
                    imgSite.setImageResource(R.drawable.site_csacademy)
                }
                Site.HACKEREARTH -> {
                    imgSite.setImageResource(R.drawable.site_hackerearth)
                }
                Site.KICKSTART -> {
                    imgSite.setImageResource(R.drawable.site_coding)
                }
                Site.LEETCODE -> {
                    imgSite.setImageResource(R.drawable.ic_leet_code_logo)
                }
                Site.TOPCODER -> {
                    imgSite.setImageResource(R.drawable.site_topcoder)
                }
                Site.TOPH -> {
                    imgSite.setImageResource(R.drawable.site_coding)
                }
            }

            if (item.status.equals(Site.Status.LIVE)) {
                txtLive.text = "Live !"
                txtLive.background = context.resources.getDrawable(R.drawable.bg_live)
            } else {
                txtLive.text = "Coming..."
                txtLive.background = context.resources.getDrawable(R.drawable.bg_coming)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContestItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.root.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data[position].url))
            context.startActivity(intent)
        }


        holder.binding.imgSetAlarm.setOnClickListener {


            try {

                //val db = ContestDatabase.getDatabase(context.applicationContext).contestDao()
                val d = data[position].start_time


                CoroutineScope(Dispatchers.IO).launch {
                    if (db.contestDao().isExists(data[position].name,data[position].start_time)) {
                        db.contestDao().remove(data[position].name,data[position].start_time)
                        withContext(Dispatchers.Main) {
                            holder.imgAlarm.setImageResource(R.drawable.ic_baseline_add_alert_24)

                            //remove reminder
                            withContext(Dispatchers.Main){
                                SetAlarm(context).removeAlarm(
                                    data[position].name,
                                    data[position].site
                                )
                            }
                            Toast.makeText(context, "Reminder Removed", Toast.LENGTH_LONG).show()
                        }
                    } else {

                        try {
                            val e = ContestEntity(
                                0,
                                duration = data[position].duration,
                                end_time = data[position].end_time,
                                in_24_hours = data[position].in_24_hours,
                                name = data[position].name,
                                site = data[position].site,
                                start_time = data[position].start_time,
                                status = data[position].status,
                                url = data[position].url
                            )
                            db.contestDao().insert(e)
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, e.message + "else block", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                        //set reminder
                        withContext(Dispatchers.Main) {
                            SetAlarm(context).sendNotification(
                                data[position].name,
                                data[position].site,
                                d,
                                data[position].url,
                                db
                            )
                            holder.imgAlarm.setImageResource(R.drawable.ic_baseline_alarm_on_24)
                        }
                    }
                }
//
//                if(CoroutineScope(Dispatchers.IO).db.contestDao().isExists(data[position].name)){
//                    CoroutineScope(Dispatchers.IO).launch {
//                        db.contestDao().delete(data[position])
//                    }
//                    holder.imgAlarm.setImageResource(R.drawable.ic_baseline_add_alert_24)
//                    //cancel alarm
//
//                }else{
//                    CoroutineScope(Dispatchers.IO).launch {
//                        db.contestDao().insert(data[position])
//                    }
//
//                    SetAlarm(context).sendNotification(data[position].name,data[position].site,d,data[position].url)
//                    holder.imgAlarm.setImageResource(R.drawable.ic_baseline_alarm_on_24)
//                }


            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }


        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
package com.surajrathod.codingcontests.util

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.surajrathod.codingcontests.MainActivity
import com.surajrathod.codingcontests.db.ContestDatabase
import com.surajrathod.codingcontests.model.Site
import com.surajrathod.codingcontests.ui.*
import com.surajrathod.codingcontests.ui.Notification
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class SetAlarm(val context: Context) {


    init {
        //create notification channel
        val name = "Live Contest Update"
        val desc = "You will get notification when your contest is live"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun sendNotification(title: String, desc: String, date: String, url : String,db : ContestDatabase) {
        val intent = Intent(context.applicationContext,Notification::class.java)

        //logic to save current alarm id in shared
        val id = System.currentTimeMillis().toInt()
        val shared = context.getSharedPreferences("alarm",Context.MODE_PRIVATE)
        var editor = shared.edit()
        editor.putInt(title,id)
        editor.commit()


        intent.putExtra(urlExtra,url)
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, desc + " Contest Is Live !")


        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            id,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        //PendingIntent.FLAG_IMMUTABLE or  PendingIntent.FLAG_UPDATE_CURRENT

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = Site.getTime(date)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

        showAlert(time)
    }


    fun removeAlarm(title : String,desc : String){


        //for removing an existing pending intent, the pending intent id must be same, if the regular intent is diffrent , still it works !
        val shared = context.getSharedPreferences("alarm",Context.MODE_PRIVATE)
        var id = shared.getInt(title,0)

        val intent = Intent(context.applicationContext,Notification::class.java)

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, desc + " Contest Is Live !")


        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            id,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )


        alarmManager.cancel(pendingIntent)

    }

    fun showAlert(time: Long) {
        val date = Date(time)
        val dateFormat = DateFormat.getLongDateFormat(context.applicationContext)
        val timeFormat = DateFormat.getTimeFormat(context.applicationContext)

        AlertDialog.Builder(context)
            .setTitle("Reminder Set !")
            .setMessage(
                "Contest will be on" + "\n" +
                        dateFormat.format(date) + "\n" + timeFormat.format(date)
            )
            .setPositiveButton("okay"){_,_ ->

            }
            .show()

    }



    fun getDateFormat(time : Long) : String{

        val date = Date(time)
        val dateFormat = DateFormat.getLongDateFormat(context.applicationContext)
        val timeFormat = DateFormat.getTimeFormat(context.applicationContext)

        return dateFormat.format(date) + " " + timeFormat.format(date)
    }
}
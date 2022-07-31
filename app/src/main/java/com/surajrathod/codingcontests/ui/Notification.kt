package com.surajrathod.codingcontests.ui

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.surajrathod.codingcontests.R
import com.surajrathod.codingcontests.db.ContestDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
const val contestExtra = "contestExtra"
const val urlExtra = "urlExtra"

class Notification : BroadcastReceiver() {


    //this is called when , alarm is triggered , so in this we will show notification to user ;)
    override fun onReceive(context: Context, intent: Intent) {


        val url = intent.getStringExtra(urlExtra)
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(Intent.ACTION_VIEW, Uri.parse(url)),
                    PendingIntent.FLAG_ONE_SHOT
                )
            )
            .setAutoCancel(true)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            ContestDatabase.getDatabase(context).contestDao().removeAlarm(
                intent.getStringExtra(
                    titleExtra
                )!!
            )
        }


        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }


}
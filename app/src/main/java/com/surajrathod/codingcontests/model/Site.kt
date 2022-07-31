package com.surajrathod.codingcontests.model

import android.content.Context
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import com.surajrathod.codingcontests.util.SetAlarm
import java.util.*

object Site {
    const val CODEFORCES = "CodeForces"
    const val CODEFORCES_GYM = "CodeForces::Gym"
    const val TOPCODER = "TopCoder"
    const val ATCODER = "AtCoder"
    const val CSACADEMY = "CS Academy"
    const val CODECHEF = "CodeChef"
    const val HACKERRANK = "HackerRank"
    const val HACKEREARTH = "HackerEarth"
    const val KICKSTART = "Kick Start"
    const val LEETCODE = "LeetCode"
    const val TOPH = "Toph"

    object Status{
        const val LIVE = "CODING"
        const val COMING = "BEFORE"
    }

    val date = "2024-07-30T18:30:00.000Z"


    @RequiresApi(Build.VERSION_CODES.O)
    fun getNewDate(date : String, context: Context) : String{

        return SetAlarm(context).getDateFormat(getTime(date))
    }

    fun getTime(date : String) : Long{

        val year = date.substring(0,4).toInt()
        val month = date.substring(5,7).toInt()-1
        val day = date.substring(8,10).toInt()
        val hour = date.substring(11,13).toInt()
        val minute = date.substring(14,16).toInt()

        val calendar = Calendar.getInstance()
        calendar.set(year,month,day,hour,minute)
        return calendar.timeInMillis
    }
}
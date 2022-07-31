package com.surajrathod.codingcontests.widget

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.surajrathod.codingcontests.R

class DataProvider(val context: Context,val intent : Intent) : RemoteViewsService.RemoteViewsFactory {

    var myListView : List<String> = arrayListOf("one","two","three")


    override fun onCreate() {
        //connect to data source

        SystemClock.sleep(3000)
    }

    override fun onDataSetChanged() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        //close data source
    }

    override fun getCount(): Int {
        return myListView.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val view = RemoteViews(context.packageName,R.layout.widget_list_item)
        SystemClock.sleep(500)
        view.setTextViewText(R.id.widget_item_text,"Hii")


        return view
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}
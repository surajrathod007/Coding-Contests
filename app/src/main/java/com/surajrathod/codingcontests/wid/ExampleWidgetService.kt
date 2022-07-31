package com.surajrathod.codingcontests.wid

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.surajrathod.codingcontests.R

class ExampleWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return ExampleWidgetItemFactory(applicationContext,intent)
    }

    inner class ExampleWidgetItemFactory(val context : Context,val intent : Intent) : RemoteViewsFactory{

        val list = arrayListOf("one","two","three","four")

        var  appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)


        override fun onCreate() {
            //connect to a datasource
           // SystemClock.sleep(3000)
        }

        override fun onDataSetChanged() {
            TODO("Not yet implemented")
        }

        override fun onDestroy() {
            TODO("Not yet implemented")
        }

        override fun getCount(): Int {
            return list.size
        }

        override fun getViewAt(position: Int): RemoteViews {

            val views = RemoteViews(context.packageName, R.layout.example_widget_item)
            views.setTextViewText(R.id.example_widget_item_text,"cool")
            SystemClock.sleep(500)

            return views
        }

        override fun getLoadingView(): RemoteViews {
            TODO("Not yet implemented")
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }

    }
}
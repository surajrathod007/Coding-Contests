package com.surajrathod.codingcontests.wid

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import com.surajrathod.codingcontests.R
import com.surajrathod.codingcontests.ui.HomeActivity
import com.surajrathod.codingcontests.util.Const

class ExampleWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {

        for(appWidgetId in appWidgetIds!!){

            Toast.makeText(context,"OnUpdate Called",Toast.LENGTH_SHORT).show()

            //what happen when button is clicked
            val intent = Intent(context,HomeActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context,0,intent,0)


            val shared = context?.getSharedPreferences(Const.SHARED_PREFS,Context.MODE_PRIVATE)
            val btnText = shared?.getString(Const.KEY_BUTTON_TEXT + appWidgetId,"Press Me")

            //adapter service
            val serviceIntent = Intent(context,ExampleWidgetService::class.java)
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)))


            val views = RemoteViews(context?.packageName, R.layout.example_widget)
            views.setOnClickPendingIntent(R.id.example_widget_btn,pendingIntent)
            views.setCharSequence(R.id.example_widget_btn,"setText",btnText)

            //set remote adapter
            views.setRemoteAdapter(R.id.example_widget_stack_view,serviceIntent)
            views.setEmptyView(R.id.example_widget_stack_view,R.id.example_widget_empty_view)

            //maintain state , when update is called(and user changed the size of widget, to maintain the widget size, update also called , when i re run app from android studio
            //if update is called , our widget will back to its default size , so to prevent this , we would do this , no need to use shared, data already in Bundle
            val appWidgetOptions = appWidgetManager?.getAppWidgetOptions(appWidgetId)
            resizeWidget(appWidgetOptions,views)


            appWidgetManager?.updateAppWidget(appWidgetId,views)
        }


        //super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    //called everytime , when we resize our widget
    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {

        val views = RemoteViews(context?.packageName,R.layout.example_widget)



//        val dims = "Min Width : " + minWidth + "\nMax Width : " + maxWidth + "\nMin Height : " + minHeight + "\nMax Height : " + maxHeight
//        Toast.makeText(context,dims,Toast.LENGTH_LONG).show()


        resizeWidget(newOptions,views)

        //now update widget

        appWidgetManager?.updateAppWidget(appWidgetId,views)



    }

    fun resizeWidget(appWidgetOptions : Bundle?,views : RemoteViews){

        val minWidth = appWidgetOptions?.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)              //portraite Mode : - minWidth * maxHeight , Landscape : - maxWidth * minHeight
        val minHeight = appWidgetOptions?.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)
        val maxWidth = appWidgetOptions?.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH)
        val maxHeight = appWidgetOptions?.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT)


        if(maxHeight!! > 120){
            views.setViewVisibility(R.id.example_widget_text, View.VISIBLE)
            views.setViewVisibility(R.id.example_widget_btn, View.VISIBLE)
        }else{
            views.setViewVisibility(R.id.example_widget_text, View.GONE)
            views.setViewVisibility(R.id.example_widget_btn, View.GONE)
        }

    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        Toast.makeText(context,"OnDeleted",Toast.LENGTH_SHORT).show()
    }

    override fun onEnabled(context: Context?) {
        Toast.makeText(context,"OnEnabled",Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context?) {
        Toast.makeText(context,"OnDisabled",Toast.LENGTH_SHORT).show()
    }


}
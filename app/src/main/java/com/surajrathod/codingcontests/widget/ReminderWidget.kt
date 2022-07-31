package com.surajrathod.codingcontests.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.surajrathod.codingcontests.R

/**
 * Implementation of App Widget functionality.
 */
class ReminderWidget : AppWidgetProvider() {




    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
//        appWidgetIds.forEach { appWidgetId ->
//
//            val intent = Intent(context,WidgetService::class.java).apply {
//                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
//                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
//            }
//
//            val views = RemoteViews(context.packageName,R.layout.reminder_widget).apply {
//                setRemoteAdapter(R.id.reminder_list,intent)
//            }
//
//            appWidgetManager.updateAppWidget(appWidgetId,views)
//        }
//
//        super.onUpdate(context, appWidgetManager, appWidgetIds)

        for(appWidgetId in appWidgetIds){
            updateAppWidget(context,appWidgetManager,appWidgetId)
        }

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.reminder_widget)
   // views.setTextViewText(R.id.appwidget_text, widgetText)


    val intent = Intent(context,WidgetService::class.java).apply {
        putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
    }

    views.setRemoteAdapter(R.id.reminder_list,intent)

    //setRemoteAdapter(context,views)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

fun setRemoteAdapter(context: Context, views: RemoteViews) {
//    val intent = Intent(context,WidgetService::class.java).apply {
//        putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
//        data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
//    }

    views.setRemoteAdapter(R.id.reminder_list, Intent(context,WidgetService::class.java))
}

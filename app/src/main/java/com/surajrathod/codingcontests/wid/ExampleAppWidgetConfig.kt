package com.surajrathod.codingcontests.wid

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RemoteViews
import com.surajrathod.codingcontests.R
import com.surajrathod.codingcontests.ui.HomeActivity
import com.surajrathod.codingcontests.util.Const

class ExampleAppWidgetConfig : AppCompatActivity() {


    var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    lateinit var edtText : EditText
    lateinit var btnConfirm : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_app_widget_config)

        val configIntent = intent
        val extras = configIntent.extras //Bundle
        if(extras!=null){
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        //if user leaves the activity without making changes
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId) //this thing is neccesary
        setResult(RESULT_CANCELED,resultValue)

        if(appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID )       //if we dont get actual id
        {
            finish() //close activity
        }


        edtText = findViewById(R.id.edText_button)
        btnConfirm = findViewById(R.id.btnConfirm)

    }


    fun confirmConfiguration(view: View) {

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val intent = Intent(this,HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,0)

        var btnText = edtText.text.toString()

        val serviceIntent = Intent(this,ExampleWidgetService::class.java)
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)))

        //because we can not acces widget elements directly , we have to create remote view for that
        val views = RemoteViews(this.packageName,R.layout.example_widget)
        views.setOnClickPendingIntent(R.id.example_widget_btn,pendingIntent)
        views.setCharSequence(R.id.example_widget_btn,"setText",btnText)        //setText is a built in method !

        views.setRemoteAdapter(R.id.example_widget_stack_view,serviceIntent)
        views.setEmptyView(R.id.example_widget_stack_view,R.id.example_widget_empty_view)

        appWidgetManager.updateAppWidget(appWidgetId,views)

        //now save changes in sharedPref

        val shared = getSharedPreferences(Const.SHARED_PREFS, MODE_PRIVATE)
        val editor = shared.edit()

        //for diffrent widgets , key must be unique so thats why + appWidgetId
        editor.putString(Const.KEY_BUTTON_TEXT + appWidgetId,btnText)
        editor.apply()

        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId) //this thing is neccesary
        setResult(RESULT_OK,resultValue)
        finish()
    }


}
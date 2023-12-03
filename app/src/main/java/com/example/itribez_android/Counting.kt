package com.example.itribez_android

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

class Counting : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == "BUTTON_CLICKED_ACTION") {
            // Handle "Click Me" button click here
            val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            updateCount(context, appWidgetId)
        } else if (intent?.action == "CLEAR_BUTTON_CLICKED_ACTION") {
            // Handle "Clear" button click here
            val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            clearCount(context, appWidgetId)
        }
    }

    private fun showToast(context: Context?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    private fun clearCount(context: Context?, appWidgetId: Int) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val views = RemoteViews(context?.packageName, R.layout.counting)

        // Set the count to 0 and update the TextView
        views.setTextViewText(R.id.appwidget_text, "0")

        // Save the updated count to SharedPreferences
        val prefs = context?.getSharedPreferences("CountingPrefs", Context.MODE_PRIVATE)
        val editor = prefs?.edit()
        editor?.putInt("count_$appWidgetId", 0)
        editor?.apply()

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
    private fun updateCount(context: Context?, appWidgetId: Int) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val views = RemoteViews(context?.packageName, R.layout.counting)

        // Get the current count from SharedPreferences
        val prefs = context?.getSharedPreferences("CountingPrefs", Context.MODE_PRIVATE)
        var count = prefs?.getInt("count_$appWidgetId", 0) ?: 0

        // Update the count and set it to the TextView
        count++
        views.setTextViewText(R.id.appwidget_text, count.toString())

        // Save the updated count to SharedPreferences
        val editor = prefs?.edit()
        editor?.putInt("count_$appWidgetId", count)
        editor?.apply()

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
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
    val widgetText = "0"
    val views = RemoteViews(context.packageName, R.layout.counting)

    // Set the text for the TextView
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Set an OnClickListener for the "Click Me" Button
    val clickIntent = Intent(context, Counting::class.java)
    clickIntent.action = "BUTTON_CLICKED_ACTION"
    clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    val clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    views.setOnClickPendingIntent(R.id.appwidget_button, clickPendingIntent)

    // Set an OnClickListener for the "Clear" Button
    val clearIntent = Intent(context, Counting::class.java)
    clearIntent.action = "CLEAR_BUTTON_CLICKED_ACTION"
    clearIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    val clearPendingIntent = PendingIntent.getBroadcast(context, 0, clearIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    views.setOnClickPendingIntent(R.id.appwidget_clear_button, clearPendingIntent)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
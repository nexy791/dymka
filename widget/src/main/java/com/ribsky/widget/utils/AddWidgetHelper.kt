package com.ribsky.widget.utils

//
// class AddWidgetHelper {
//
//    companion object {
//
//        fun addWidgetToHomeScreen(context: Context) {
//            val appWidgetManager = AppWidgetManager.getInstance(context)
//            val widgetProvider = ComponentName(context, WordWidget::class.java)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && appWidgetManager.isRequestPinAppWidgetSupported) {
//                val pinnedWidgetCallbackIntent = Intent(context, WordWidget::class.java)
//                val successCallback =
//                    PendingIntent.getBroadcast(
//                        context,
//                        0,
//                        pinnedWidgetCallbackIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
//                    )
//                appWidgetManager.requestPinAppWidget(
//                    widgetProvider,
//                    null,
//                    successCallback
//                )
//            } else {
//                val pickWidget = Intent(AppWidgetManager.ACTION_APPWIDGET_PICK)
//                pickWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetProvider)
//                context.startActivity(pickWidget)
//            }
//
//        }
//
//        fun isWidgetAdded(context: Context): Boolean {
//            val appWidgetManager = AppWidgetManager.getInstance(context)
//            val myWidgetProvider = ComponentName(context, WordWidget::class.java)
//            return appWidgetManager.getAppWidgetIds(myWidgetProvider).isNotEmpty()
//        }
//
//        fun setIntervalUpdate(context: Context, interval: Int = 1800000) {
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
//            val intent = Intent(context, WordWidget::class.java)
//            val pendingIntent = PendingIntent.getBroadcast(
//                context,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
//            )
//            alarmManager!!.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis() + interval,
//                interval.toLong(),
//                pendingIntent
//            )
//        }
//
//        fun disableIntervalUpdate(context: Context) {
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
//            val intent = Intent(context, WordWidget::class.java)
//            val pendingIntent = PendingIntent.getBroadcast(
//                context,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
//            )
//            alarmManager!!.cancel(pendingIntent)
//        }
//
//        fun updateAllWidgets(context: Context) {
//            val appWidgetManager = AppWidgetManager.getInstance(context)
//            val intent = Intent(context, WordWidget::class.java).apply {
//                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
//                val ids =
//                    appWidgetManager.getAppWidgetIds(ComponentName(context, WordWidget::class.java))
//                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
//            }
//            context.sendBroadcast(intent)
//        }
//
//    }
//
// }

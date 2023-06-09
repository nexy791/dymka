package com.ribsky.widget.widgets.word

// class WordWidget : AppWidgetProvider() {
//
//    private val getBestWordUseCase: GetBestWordUseCase by inject(GetBestWordUseCase::class.java)
//
//    override fun onUpdate(
//        context: Context,
//        appWidgetManager: AppWidgetManager,
//        appWidgetIds: IntArray,
//    ) {
//        for (appWidgetId in appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId)
//        }
//    }
//
//    override fun onEnabled(context: Context) {
//        setIntervalUpdate(context)
//        updateWidget(context)
//    }
//
//    private fun updateWidget(context: Context) {
//        val appWidgetManager = AppWidgetManager.getInstance(context)
//        val appWidgetIds = appWidgetManager.getAppWidgetIds(
//            ComponentName(
//                context,
//                WordWidget::class.java
//            )
//        )
//        for (appWidgetId in appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId)
//        }
//    }
//
//    override fun onDisabled(context: Context) {
//        disableIntervalUpdate(context)
//    }
//
//    private fun updateAppWidget(
//        context: Context,
//        appWidgetManager: AppWidgetManager,
//        appWidgetId: Int,
//    ) {
//
//        CoroutineScope(Dispatchers.Main).launch {
//            val word = withContext(Dispatchers.IO) {
//                getBestWordUseCase.getCurrentWord()
//            }
//            val views = RemoteViews(context.packageName, R.layout.word_widget).apply {
//                setTextViewText(R.id.tv_title, word.title)
//                setTextViewText(R.id.tv_word, word.description)
//            }
//            appWidgetManager.updateAppWidget(appWidgetId, views)
//        }
//
//
//    }
// }

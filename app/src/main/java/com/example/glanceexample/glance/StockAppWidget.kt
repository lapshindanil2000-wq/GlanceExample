package com.example.glanceexample.glance

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration

class StockAppWidget : GlanceAppWidget() {
    private var job: Job? = null

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        if (job == null) {
            job = startUpdateJob(
                Duration.ofSeconds(20).toMillis(),
                context
            )
        }
        provideContent {
            GlanceTheme {
                GlanceContent()
            }
        }
    }

    private fun startUpdateJob(timeInterval: Long, context: Context): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                PriceDataRepo.update()
                updateAll(context)
                delay(timeInterval)
            }
        }
    }

    @Composable
    fun GlanceContent() {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(GlanceTheme.colors.background)
                .padding(8.dp)
        ) {
            Text("Demo")
        }
    }
}
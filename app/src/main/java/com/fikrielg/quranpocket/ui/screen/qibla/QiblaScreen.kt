package com.fikrielg.quranpocket.ui.screen.qibla

import android.app.Activity
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.fikrielg.quranpocket.ui.component.QuranPocketAppBar
import com.just.agentweb.AgentWeb
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun QiblaScreen() {
    val activity = LocalContext.current as Activity
    val url = "https://qiblafinder.withgoogle.com"

    Scaffold(
        topBar = {
            QuranPocketAppBar(
                currentDestinationTitle = "Qibla Finder",
                navigateUp = { /*TODO*/ },
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { context ->
                        LinearLayout(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            AgentWeb.with(activity)
                                .setAgentWebParent(this, this.layoutParams)
                                .useDefaultIndicator()
                                .createAgentWeb()
                                .ready()
                                .go(url)
                        }
                    }
                )
            }
        })
}


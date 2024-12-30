package com.plcoding.goldchart.gold.presentation.home.components

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewComposable(modifier: Modifier = Modifier) {
    val embedCode = """
        <html>
            <body style="margin:0;padding:0;">
                <iframe src="https://www.tradingview.com/chart/?symbol=NASDAQ%3AAMZN" width="100%" height="500" frameborder="0" allowtransparency="true"></iframe>
            </body>
        </html>
    """

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true // Bật JavaScript nếu cần
                loadDataWithBaseURL(null, embedCode, "text/html", "UTF-8", null)
            }
        }
    )
}
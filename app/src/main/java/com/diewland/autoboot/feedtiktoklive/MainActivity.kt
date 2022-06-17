package com.diewland.autoboot.feedtiktoklive

import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.ui.PlayerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rtmpUrl1 = "rtmp://62.113.210.250/medienasa-live/ok-merseburg_high"
        val rtmpUrl2 = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4"
        val tiktokUrl = "https://www.tiktok.com/@mellerzap2/live?lang=th-TH"

        //playVideoView(rtmpUrl2)
        playExoView(rtmpUrl1)
        //playWebView(tiktokUrl)
    }

    fun playWebView(url: String) {
        val wv = findViewById<WebView>(R.id.wv_preview)
        val wvc = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        val webSettings = wv.settings
        webSettings.javaScriptEnabled = true
        // webSettings.setAppCacheEnabled(true)
        // webSettings.javaScriptCanOpenWindowsAutomatically = true
        // webSettings.allowFileAccess = true
        // webSettings.pluginState = WebSettings.PluginState.ON
        // wv.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        // wv.webViewClient = wvc
        wv.webChromeClient = WebChromeClient()
        // wv.setOnTouchListener { view, motionEvent -> true } // prevent touch
        wv.loadUrl(url)
    }

    fun playVideoView(url: String) {
        val vv = findViewById<VideoView>(R.id.vv_preview)
        vv.setVideoURI(Uri.parse(url))
        vv.requestFocus()
        vv.start()
    }

    fun playExoView(url: String) {

        // TODO RtspMediaSource -> RtmpMediaSource
        val mediaSource = RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri(url))

        // init player
        val player = SimpleExoPlayer.Builder(this).build()
        player.setMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = true

        // init player view
        val playerView = findViewById<PlayerView>(R.id.exo_preview)
        playerView.player = player
    }

}
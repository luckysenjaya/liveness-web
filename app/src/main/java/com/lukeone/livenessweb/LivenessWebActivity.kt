package com.lukeone.livenessweb

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class LivenessWebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liveness_web)
        checkCameraPermissionAndStartWebView()

    }

    private fun checkCameraPermissionAndStartWebView() {
        val writeExternalStorage =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (writeExternalStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1001)
        } else {
            openWebView()
        }
    }

    fun openWebView(){
        val wvLiveness = findViewById<WebView>(R.id.wvLiveness)
        wvLiveness.webViewClient = MyWebViewClient()
        wvLiveness.settings.javaScriptEnabled = true
        wvLiveness.settings.domStorageEnabled = true;
        wvLiveness.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wvLiveness.setWebChromeClient(object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.resources)
                }
            }

        })

        wvLiveness.loadUrl("https://d1zdp3zd8zqmm7.cloudfront.net/")
    }

    private class MyWebViewClient : WebViewClient() {


        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.d("LivenessWeb", url.toString())
        }
    }
}
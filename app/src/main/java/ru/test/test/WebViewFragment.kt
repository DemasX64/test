package ru.test.test

import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment


class WebViewFragment : Fragment() {

    lateinit var webView:WebView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_webview, container, false)
        webView = root.findViewById(R.id.webview)
        webView.webViewClient = MyWebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl("http://www.icndb.com/api/")

        webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action === MotionEvent.ACTION_UP && webView.canGoBack()
            ) {
                webView.goBack()
                return@OnKeyListener true
            }
            false
        })
        
        return root
    }


}
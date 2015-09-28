package com.google.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Roman on 26.09.2015.
 */
public class WebViewInfo extends Activity {

    WebView webView;
    Intent intent;
    String value;

    private static final String KEY_VALUE = "key_value";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);

        webView = (WebView) findViewById(R.id.webView);

        intent = getIntent();
        value = intent.getStringExtra(KEY_VALUE);
        miniBrowser(value);
    }

    private void miniBrowser(String value) {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        webView.loadUrl(value);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

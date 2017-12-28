package com.zheng.webmanger;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

/**
 * Created by 18513 on 2017/12/28.
 */

public class CusWebViewClient extends BridgeWebViewClient {

    private BridgeWebView cusWebView;
    private AbsWebView absWebView;

    public CusWebViewClient(BridgeWebView cusWebView, AbsWebView absWebView) {
        super(cusWebView);
        this.cusWebView = cusWebView;
        this.absWebView = absWebView;
        initWebChromeClient();
    }

    private void initWebChromeClient() {
        this.cusWebView.setWebChromeClient(new WebChromeClient(){

            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                absWebView.onReceivedTitle(title);
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    absWebView.onProgressChanged(newProgress);
                } else if (newProgress == 100) {
                    absWebView.onProgressChanged(newProgress);
                }
            }
        });
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        absWebView.onPageStarted();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        absWebView.onPageFinished();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        absWebView.onReceivedError();
        Log.e("----------",errorCode + "");
    }
}

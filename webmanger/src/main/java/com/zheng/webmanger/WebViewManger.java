package com.zheng.webmanger;

import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

/**
 * Created by 18513 on 2017/12/28.
 */

public class WebViewManger {
    private final String TAG = "--WebViewManger";

    private BridgeWebView cusWebView;
    //发送消息后的回调数据
    private String callHandlerString = "";
    //注册后的回调数据
    private String registerHandlerString = "";

    public WebViewManger(BridgeWebView cusWebView) {
        this.cusWebView = cusWebView;
    }

    public void setWebViewClient(CusWebViewClient cusWebViewClient){
        if(cusWebView != null && cusWebViewClient != null){
            cusWebView.setWebViewClient(cusWebViewClient);
        }
    }

    /**
     * 设置加载的网页地址
     * @param url   需要加载的网页地址
     */
    public void setLoadUrl(String url){
        cusWebView.loadUrl(url);
    }

    /**
     * 注册一个redisterName，同web端保持一致，供web端调用
     * @param redisterName
     */
    public String registerHandler(String redisterName){
        cusWebView.registerHandler(redisterName, new BridgeHandler() {
            /**
             *
             * @param data  为web传递的数据
             * @param function  web端请求成功后，返回给web端的回调通知
             */
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e(TAG,data);
                registerHandlerString = data;
                function.onCallBack("submitFromWeb exe, response data from Java");
            }
        });
        return registerHandlerString;
    }

    /**
     * Java调用web方法数据
     * @param callName  web端注册的name（web端用registerHandler注册的name）
     * @param json
     */
    public String callHandler (String callName,String json){

        cusWebView.callHandler(callName, json, new CallBackFunction() {

            /**
             * 发送消息回传的数据
             * @param data
             */
            @Override
            public void onCallBack(String data) {
                callHandlerString = data;
                Log.i(TAG, "reponse data from js " + data);
            }
        });
        return callHandlerString;
    }

    /**
     * 设置点击返回按钮，返回上一页面而不是退出浏览器
     * @param keyCode  点击返回按钮的keyCode值
     * @return
     */
    public boolean onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK && cusWebView.canGoBack()) {
            cusWebView.goBack();
            return true;
        }
        return false;
    }

    /**
     * 释放webview资源
     */
    public void onDestroy() {
        if (cusWebView != null) {
            cusWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            cusWebView.clearHistory();

            ((ViewGroup) cusWebView.getParent()).removeView(cusWebView);
            cusWebView.destroy();
            cusWebView = null;
        }
    }
}

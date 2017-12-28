package com.zheng.circle;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.google.gson.Gson;
import com.zheng.webmanger.AbsWebView;
import com.zheng.webmanger.CusWebViewClient;
import com.zheng.webmanger.WebViewManger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";

    BridgeWebView webView;

    Button button;

    int RESULT_CODE = 0;

    ValueCallback<Uri> mUploadMessage;
    private WebViewManger webViewManger;

    static class Location {
        String address;
    }

    static class User {
        String name;
        Location location;
        String testStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (BridgeWebView) findViewById(R.id.webView);


        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(this);
        webViewManger = new WebViewManger(webView);
        webViewManger.setWebViewClient(new CusWebViewClient(webView, new AbsWebView() {
            @Override
            public void onProgressChanged(int newProgress) {
                Log.e("-----",newProgress + "");
            }

            @Override
            public void onReceivedError() {
                super.onReceivedError();
            }
        }));

        webViewManger.setLoadUrl("https://www.youtube.com/");
        //加载服务器网页
        //模拟用户获取本地位置
        User user = new User();
        Location location = new Location();
        location.address = "上海";
        user.location = location;
        user.name = "Bruce";

        String functionInJs = webViewManger.callHandler("functionInJs", new Gson().toJson(user));
        Toast.makeText(this, functionInJs, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View v) {
        if (button.equals(v)) {
            String functionInJs = webViewManger.callHandler("functionInJs", "aksdjflkajfnkad,j");
            Toast.makeText(this, functionInJs, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webViewManger.onKeyDown(keyCode)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        webViewManger.onDestroy();
        super.onDestroy();
    }
}

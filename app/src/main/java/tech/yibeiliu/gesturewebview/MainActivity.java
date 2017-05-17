package tech.yibeiliu.gesturewebview;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WebView mWebview;
    private GestureOverlayView mGestureview;
    private GestureLibrary mGestureLib;
    private Toolbar mToolbar;
    private final String URL = "http://www.baidu.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
        initWebView();
        initGestureOverlay();
    }

    private void initGestureOverlay() {
        //从 raw 文件夹里取出我们预制的手势文件
        mGestureLib = GestureManager.getInstance(getBaseContext()).getGestureLib();
        //设置笔画为单笔，也就是一笔画
        mGestureview.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_SINGLE);
        //画完笔画后到消失的间隔
        mGestureview.setFadeOffset(0);
        //笔画粗细
        mGestureview.setGestureStrokeWidth(15);
        //重点啊啊啊，设置 GestureOverlayView 不拦截触摸事件，
        // 不设置的话，webview 是没法相应我们的滑动事件的
        mGestureview.setEventsInterceptionEnabled(false);
        //精髓接口，通过他我们可以判断用户的手势是不是我们想接收的手势
        mGestureview.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay,
                                           Gesture gesture) {
                //recognize()方法接收用户的手势，并返回手势库中和该手势相匹配的手势
                ArrayList<Prediction> predictions = mGestureLib
                        .recognize(gesture);
                if (predictions.size() > 0) {
                    //我们取最最相似的那个手势
                    //prediction 里面就是我们预制的 手势名称 和 相匹配的分值 两个变量
                    Prediction prediction = (Prediction) predictions.get(0);
                    //如果分数大于 1.0 并且 匹配出来的手势名称是我们预制的 “back” 字符串
                    if (prediction.score > 1.0 && prediction.name.equals("back")) {
                        if (mWebview.canGoBack()) {
                            //webview 回退上一级
                            mWebview.goBack();
                        } else {
                            Toast.makeText(MainActivity.this, "已经是首页啦", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void initWebView() {
        WebSettings settings = mWebview.getSettings();
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        mWebview.loadUrl(URL);
        mWebview.setWebViewClient(new WebViewClient());
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.edit_gesture) {
                    Intent intent = new Intent(MainActivity.this, EditGestureActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    private void initView() {
        mWebview = (WebView) findViewById(R.id.webview);
        mGestureview = (GestureOverlayView) findViewById(R.id.gestureview);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}

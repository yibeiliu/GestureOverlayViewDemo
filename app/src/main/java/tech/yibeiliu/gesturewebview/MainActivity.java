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
        mGestureLib = GestureManager.getInstance(getBaseContext()).getGestureLib();
        mGestureview.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_SINGLE);

        mGestureview.setFadeOffset(0);
        mGestureview.setGestureStrokeWidth(15);
        mGestureview.setEventsInterceptionEnabled(false);

        mGestureview.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay,
                                           Gesture gesture) {
                ArrayList<Prediction> predictions = mGestureLib
                        .recognize(gesture);
                if (predictions.size() > 0) {
                    Prediction prediction = (Prediction) predictions.get(0);
                    if (prediction.score > 1.0 && prediction.name.equals("back")) {
                        if (mWebview.canGoBack()) {
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

package tech.yibeiliu.gesturewebview;

import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditGestureActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextview;
    private Button mDone;
    private Gesture mGesture;
    private GestureOverlayView mEditoverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gesture);
        initView();
        initGestureOverlayView();
    }

    private void initGestureOverlayView() {
        mEditoverlay.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
        mEditoverlay.setFadeOffset(1000);
        mEditoverlay.setGestureStrokeWidth(15);
        mEditoverlay.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            @Override
            public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

            }

            @Override
            public void onGesture(GestureOverlayView overlay, MotionEvent event) {

            }

            @Override
            public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
                //获取修改后的手势
                mGesture = overlay.getGesture();
            }

            @Override
            public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

            }
        });
    }

    private void initView() {
        mTextview = (TextView) findViewById(R.id.textview);
        mDone = (Button) findViewById(R.id.done);
        mDone.setOnClickListener(this);
        mEditoverlay = (GestureOverlayView) findViewById(R.id.editoverlay);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done:
                if (mGesture != null) {
                    GestureManager.getInstance(getBaseContext()).changeBackGesture(mGesture);
                    finish();
                }
                break;
        }
    }
}

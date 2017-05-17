package tech.yibeiliu.gesturewebview;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.widget.Toast;

/**
 * Created by LiuPeiyi on 2017/05/17.
 */

public class GestureManager {

    private GestureOverlayView mGestureOverlay;
    private GestureLibrary mGestureLib;
    private Context mContext;
    private static volatile GestureManager instance;


    private GestureManager(Context context) {
        this.mContext = context;
        mGestureLib = GestureLibraries.fromRawResource(mContext, R.raw.gestures);
        mGestureLib.load();
    }

    public static GestureManager getInstance(Context context) {
        if (instance == null) {
            synchronized (GestureManager.class) {
                if (instance == null) {
                    instance = new GestureManager(context);
                }
            }
        }
        return instance;
    }

    public GestureLibrary getGestureLib() {
        return mGestureLib;
    }
    public void changeBackGesture(Gesture gesture){
        mGestureLib.removeEntry("back");
        mGestureLib.addGesture("back", gesture);
        Toast.makeText(mContext, "成功了，快去试试吧", Toast.LENGTH_SHORT).show();
    }


}

package cn.lven.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * 代理的Activity
 */
public interface IPluginActivity {
    void attach(Activity proxyActivity);

    /**
     * 生命周期
     */
    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    boolean onTouchEvent(MotionEvent event);

    void onBackPressed();
}

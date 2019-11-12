package cn.lven.plugin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class PluginActivity extends AppCompatActivity implements IPluginActivity {

    private Activity mProxyActivity;

    public Activity getPluginActivity() {
        return mProxyActivity;
    }

    @Override
    public void attach(Activity proxyActivity) {
        this.mProxyActivity = proxyActivity;
    }

    @Override
    public void setContentView(View view) {
        if (!isActivityNull()) {
            mProxyActivity.setContentView(view);
            return;
        }
        super.setContentView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (!isActivityNull()) {
            mProxyActivity.setContentView(layoutResID);
            return;
        }
        super.setContentView(layoutResID);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (!isActivityNull()) {
            return mProxyActivity.findViewById(id);
        }
        return super.findViewById(id);
    }

    @Override
    public Intent getIntent() {
        if (!isActivityNull()) {
            return mProxyActivity.getIntent();
        }
        return super.getIntent();
    }

    @Override
    public ComponentName startService(Intent service) {
        if (!isActivityNull()) {
            Intent m = new Intent();
            m.putExtra("serviceName", service.getComponent().getClassName());
            return mProxyActivity.startService(m);
        }
        return super.startService(service);
    }

    @Override
    public ClassLoader getClassLoader() {
        return mProxyActivity.getClassLoader();
    }


    @Override
    public void startActivity(Intent intent) {
        //  ProxyActivity --->className
        Intent m = new Intent();
        m.putExtra("className", intent.getComponent().getClassName());
        mProxyActivity.startActivity(m);
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return mProxyActivity.getLayoutInflater();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return mProxyActivity.getApplicationInfo();
    }


    @Override
    public Window getWindow() {
        return mProxyActivity.getWindow();
    }


    @Override
    public WindowManager getWindowManager() {
        return mProxyActivity.getWindowManager();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    private boolean isActivityNull() {
        return mProxyActivity == null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

}

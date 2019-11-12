package cn.lven.plugin.prxy;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import java.lang.reflect.Constructor;

import cn.lven.plugin.PluginActivity;


/**
 * Created by Administrator on 2018/3/28.
 */

public class ProxyActivity extends AppCompatActivity {
    //    需要加载淘票票的  类名(全路径)
    private String className;
    PluginActivity mPluginActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra("className");
        try {
            Class activityClass = getClassLoader().loadClass(className);
            Constructor constructor = activityClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});
            mPluginActivity = (PluginActivity) instance;
            mPluginActivity.attach(this);
            Bundle bundle = new Bundle();
            mPluginActivity.onCreate(bundle);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        String className1 = intent.getStringExtra("className");
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("className", className1);
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        String serviceName = service.getStringExtra("serviceName");
        Intent intent1 = new Intent(this, ProxyService.class);
        intent1.putExtra("serviceName", serviceName);
        return super.startService(intent1);
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPluginActivity.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPluginActivity.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPluginActivity.onPause();
    }
}

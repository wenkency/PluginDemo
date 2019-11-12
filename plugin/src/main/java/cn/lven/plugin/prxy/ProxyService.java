package cn.lven.plugin.prxy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import java.lang.reflect.Constructor;

import cn.lven.plugin.IPluginService;

/**
 * 代理的Service
 */

public class ProxyService extends Service {
    private String serviceName;
    private IPluginService mPluginService;

    @Override
    public IBinder onBind(Intent intent) {
        init(intent);
        return null;
    }

    private void init(Intent intent) {
        serviceName = intent.getStringExtra("serviceName");
        try {
            Class loadClass = PluginManager.getInstance().getDexClassLoader().loadClass(serviceName);
            Constructor<?> localConstructor = loadClass.getConstructor(new Class[]{});
            Object instance = localConstructor.newInstance(new Object[]{});
            mPluginService = (IPluginService) instance;
            mPluginService.attach(this);
            Bundle bundle = new Bundle();
            bundle.putInt("form", 1);
            mPluginService.onCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mPluginService == null) {
            init(intent);
        }

        return mPluginService.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mPluginService.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        mPluginService.onLowMemory();
        super.onLowMemory();
    }


    @Override
    public boolean onUnbind(Intent intent) {
        mPluginService.onUnbind(intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        mPluginService.onRebind(intent);
        super.onRebind(intent);
    }

}

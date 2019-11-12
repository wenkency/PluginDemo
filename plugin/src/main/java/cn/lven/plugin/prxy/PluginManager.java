package cn.lven.plugin.prxy;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 组件的帮助类
 */
public class PluginManager {
    private static PluginManager mPluginManager = new PluginManager();
    private Context mContext;
    private Resources mResources;
    private PackageInfo mPackageInfo;
    private DexClassLoader mDexClassLoader;

    public static PluginManager getInstance() {
        return mPluginManager;
    }

    private PluginManager() {
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }


    public void loadPath(String path) {
        if (mContext == null || TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        // 这个拿到PackageInfo
        PackageManager packageManager = mContext.getPackageManager();
        mPackageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

        // activity 名字
        File dexOutFile = mContext.getDir("dex", Context.MODE_PRIVATE);
        mDexClassLoader = new DexClassLoader(file.getAbsolutePath(), dexOutFile.getAbsolutePath()
                , null, mContext.getClassLoader());
        // Resources
        try {
            Resources rs = mContext.getResources();
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            // 加载皮肤的路径
            addAssetPath.invoke(assetManager, file.getAbsolutePath());
            // 皮肤的资源
            mResources = new Resources(assetManager, rs.getDisplayMetrics(), rs.getConfiguration());
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public Resources getResources() {
        return mResources;
    }

    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    public DexClassLoader getDexClassLoader() {
        return mDexClassLoader;
    }
}

package cn.lven.plugindemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import cn.lven.plugin.prxy.PluginManager;
import cn.lven.plugin.prxy.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManager.getInstance().init(this);
    }

    public void load(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "tao.plugin");
        if (file.exists()) {
            PluginManager.getInstance().loadPath(file.getAbsolutePath());
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void jump(View view) {
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("className", PluginManager.getInstance().getPackageInfo().activities[0].name);
        startActivity(intent);
    }
}

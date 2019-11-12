package cn.lven.taopiaopiao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.lven.plugin.PluginActivity;

public class MainActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getPluginActivity(),SecondActivity.class));
            }
        });
    }
}

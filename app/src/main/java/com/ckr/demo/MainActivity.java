package com.ckr.demo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author ckr
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View flexBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnClick(R.id.bt_flexbox);
    }

    private void setOnClick(@IdRes int viewId) {
        findViewById(viewId).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_flexbox:
                FlexBoxActivity.start(this);
                break;
        }
    }
}

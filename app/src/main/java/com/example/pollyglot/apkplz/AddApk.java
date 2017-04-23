package com.example.pollyglot.apkplz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;

public class AddApk extends AppCompatActivity {

    private Toolbar mToolbarAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apk);

        mToolbarAddItem = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbarAddItem);
//        mToolbarAddItem.setTitle(null);


    }
}

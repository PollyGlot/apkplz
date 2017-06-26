package com.example.pollyglot.apkplz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pollyglot.apkplz.models.Apk;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppDetailActivity extends BaseActivity {

    private static final String TAG = "AppDetailActivity";
    public static final String EXTRA_APP_KEY = "app_key";

    private String mAppKey;
    private TextView mAppTitle;
    private DatabaseReference mAppReference;
    private ValueEventListener mAppListener;
    private Toolbar mToolbar;
    private ImageView mAppImage;
    private TextView mVersion;
    private TextView mDeveloper;
    private TextView mMinAndroid;
    private TextView mMaxAndroid;
    private TextView mWhatNew;
    private TextView mDpi;
    private Button mBtnDownload;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        mAppKey = getIntent().getStringExtra(EXTRA_APP_KEY);
        if (mAppKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_APP_KEY");
        }

        mAppReference = FirebaseDatabase.getInstance().getReference()
                .child("apps").child(mAppKey);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAppImage = (ImageView) findViewById(R.id.app_detail_title);
        mVersion = (TextView) findViewById(R.id.app_detail_version_text);
        mDeveloper = (TextView) findViewById(R.id.app_detail_dev_text);
        mMinAndroid = (TextView) findViewById(R.id.app_detail_minandroid_text);
        mMaxAndroid = (TextView) findViewById(R.id.app_detail_maxandroid_text);
        mDpi = (TextView) findViewById(R.id.app_detail_dpi_text);
        mWhatNew = (TextView) findViewById(R.id.app_detail_whatnew_text);
        mBtnDownload = (Button) findViewById(R.id.btn_download_file);

    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener appListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Apk apk = dataSnapshot.getValue(Apk.class);
                mToolbar.setTitle(apk.title);
                mVersion.setText(apk.version);
                mDeveloper.setText(apk.developer);
                mMinAndroid.setText(apk.minAndroidVersion);
                mMaxAndroid.setText(apk.maxAndroidVersion);
                mDpi.setText(apk.dpi);
                mWhatNew.setText(apk.description);



                mBtnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apk.fileUrl)));
                    }
                });

                Glide.with(mAppImage.getContext())
                        .load(apk.imageUrl)
                        .into(mAppImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadApp:onCancelled", databaseError.toException());
                Toast.makeText(AppDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mAppReference.addValueEventListener(appListener);

        mAppListener = appListener;
    }
}

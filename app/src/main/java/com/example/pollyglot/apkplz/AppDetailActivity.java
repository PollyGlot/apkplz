package com.example.pollyglot.apkplz;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

public class AppDetailActivity extends BaseActivity {

    private static final String TAG = "AppDetailActivity";
    public static final String EXTRA_APP_KEY = "app_key";

    private String mAppKey;
    private TextView mAppTitle;
    private DatabaseReference mAppReference;
    private ValueEventListener mAppListener;
    private Toolbar mToolbar;
    private ImageView mAppImage;

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

        mAppTitle = (TextView) findViewById(R.id.app_name);
        mAppImage = (ImageView) findViewById(R.id.app_detail_title);

    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener appListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Apk apk = dataSnapshot.getValue(Apk.class);
                mToolbar.setTitle(apk.title);

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

package com.example.pollyglot.apkplz;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pollyglot.apkplz.models.Apk;
import com.example.pollyglot.apkplz.models.Developer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.pollyglot.apkplz.AppDetailActivity.EXTRA_APP_KEY;

public class DevDetailActivity extends BaseActivity {

    private static final String TAG = "DevDetailActivity";
    public static final String EXTRA_DEV_KEY = "dev_key";
    public static final String EXTRA_DEV_NAME = "dev_name";

    private String mDevKey;
    private TextView mLatestAppTitle;
    private DatabaseReference mDevReference;
    private ValueEventListener mDevListener;
    private Toolbar mToolbar;
    private TextView mAppsNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_detail);

        mDevKey = getIntent().getStringExtra(EXTRA_DEV_KEY);
        if (mDevKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_APP_KEY");
        }

        final String mDevName = getIntent().getStringExtra(EXTRA_DEV_NAME);

        mDevReference = FirebaseDatabase.getInstance().getReference()
                .child("developers").child(mDevKey);

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

//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.toolbar_dev_layout);
//        collapsingToolbar.setTitle(mDevName);

        mLatestAppTitle = (TextView) findViewById(R.id.latest_app_title);
        mAppsNumber = (TextView) findViewById(R.id.dev_detail_apps_number);
    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener appListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Developer dev = dataSnapshot.getValue(Developer.class);
                mToolbar.setTitle(dev.developer);
                mLatestAppTitle.setText(dev.latestUpdate);
//                mAppsNumber.setText(dev.appsNumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadApp:onCancelled", databaseError.toException());
                Toast.makeText(DevDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mDevReference.addValueEventListener(appListener);

        mDevListener = appListener;
    }
}


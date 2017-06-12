package com.example.pollyglot.apkplz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pollyglot.apkplz.models.Apk;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DevDetailActivity extends BaseActivity {

    private static final String TAG = "DevDetailActivity";

    public static final String EXTRA_DEV_KEY = "dev_key";

    private String mDevKey;
    private TextView mDevName;
    private DatabaseReference mDevReference;
    private ValueEventListener mDevListener;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_detail);

        mDevKey = getIntent().getStringExtra(EXTRA_DEV_KEY);
        if (mDevKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setTitle("Developer");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDevReference = FirebaseDatabase.getInstance().getReference()
                .child("developers").child(mDevKey);

//        mDevName = (TextView) findViewById(R.id.developer);


        ValueEventListener devListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Apk apk = dataSnapshot.getValue(Apk.class);

                mToolbar.setTitle(apk.developer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(DevDetailActivity.this, "Failed to load dev.",
                        Toast.LENGTH_SHORT).show();
            }
        };

//        mDevReference.addValueEventListener(devListener);
//        // [END post_value_event_listener]
//
        // Keep copy of post listener so we can remove it when app stops
//        mDevListener = devListener;
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}

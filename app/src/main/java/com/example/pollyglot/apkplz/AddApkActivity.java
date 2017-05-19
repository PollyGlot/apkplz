package com.example.pollyglot.apkplz;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.pollyglot.apkplz.models.Apk;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddApkActivity extends BaseActivity {

    private static final String REQUIRED = "Required";
    private static final String TAG = "AddApkActivity";

    private Toolbar mToolbar;
    private Spinner mSpinnerDev;
    private Spinner mSpinnerTitle;
    private EditText mVersionField;
    private Spinner mSpinnerMin;
    private Spinner mSpinnerMax;
    private Spinner mSpinnerDpi;
    private SpinnerAdapter mSpinnerAdapter;
    private EditText mWhatNew;
    private EditText mAddField;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apk);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mSpinnerDev = (Spinner) findViewById(R.id.choose_developer);
        mSpinnerTitle = (Spinner) findViewById(R.id.choose_title);
        mSpinnerMin = (Spinner) findViewById(R.id.min_android);
        mSpinnerMax = (Spinner) findViewById(R.id.max_android);
        mSpinnerDpi = (Spinner) findViewById(R.id.choose_dpi_min);
        mWhatNew = (EditText) findViewById(R.id.what_new);
        mAddField = (EditText) findViewById(R.id.dialog_add_field);
        mVersionField = (EditText) findViewById(R.id.version_field);

        alertBuilder = new AlertDialog.Builder(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        mToolbar.setTitle(R.string.add_apk_title);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSpinnerDev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Add Developer")) {
                    showDevDialog();
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        mSpinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Add title")) {
                    showTitleDialog();
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

//        mSpinnerDpi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = parent.getItemAtPosition(position).toString();
//                if (selectedItem.equals("Add dpi")) {
//                    showDpiDialog();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        mSpinnerMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = parent.getItemAtPosition(position).toString();
//                if (selectedItem.equals("Add min")){
//                    showAndroidVersionDialog();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        mDatabase.child("developers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> developers = new ArrayList<>();

                for (DataSnapshot developersSnapshot: dataSnapshot.getChildren()) {
                    String devName = developersSnapshot.child("developer").getValue(String.class);
                    developers.add(devName);
                }
                Collections.sort(developers);
                developers.add(0, "Choose Developer");
                developers.add(1, "Add Developer");
                ArrayAdapter<String> developersAdapter = new ArrayAdapter<>(AddApkActivity.this,
                        android.R.layout.simple_spinner_item, developers);
                developersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerDev.setAdapter(developersAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("app_titles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> titles = new ArrayList<>();

                for (DataSnapshot titlesSnapshot: dataSnapshot.getChildren()) {
                    String titleName = titlesSnapshot.child("title").getValue(String.class);
                    titles.add(titleName);
                }
                Collections.sort(titles);
                titles.add(0, "Choose Title");
                titles.add(1, "Add title");

                ArrayAdapter<String> titlesAdapter = new ArrayAdapter<>(AddApkActivity.this,
                        android.R.layout.simple_spinner_item,  titles);
                titlesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerTitle.setAdapter(titlesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("android_version").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> androidVersions = new ArrayList<>();

                for (DataSnapshot titlesSnapshot: dataSnapshot.getChildren()) {
                    String androidVersion = titlesSnapshot.child("androidVersion").getValue(String.class);
                    androidVersions.add(androidVersion);
                }
                Collections.sort(androidVersions);
                androidVersions.add(0, "Android version");

                ArrayAdapter<String> androidVersionAdapter = new ArrayAdapter<>(AddApkActivity.this,
                        android.R.layout.simple_spinner_item,  androidVersions);
                androidVersionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerMin.setAdapter(androidVersionAdapter);
                mSpinnerMax.setAdapter(androidVersionAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("app_dpi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> dpi = new ArrayList<>();

                for (DataSnapshot dpiSnapshot: dataSnapshot.getChildren()) {
                    String androidVersion = dpiSnapshot.child("dpi").getValue(String.class);
                    dpi.add(androidVersion);
                }
//                Collections.sort(dpi);
                dpi.add(0, "Choose dpi");
//                androidVersions.add(1, "Add title");

                ArrayAdapter<String> dpiAdapter = new ArrayAdapter<>(AddApkActivity.this,
                        android.R.layout.simple_spinner_item,  dpi);
                dpiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerDpi.setAdapter(dpiAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void saveDev() {
        final String devName = mAddField.getText().toString();
        String key = mDatabase.child("developers").push().getKey();

        Apk apk = new Apk();
        apk.setDeveloper(devName);
        mDatabase.child("developers").child(key).setValue(apk);

        Toast.makeText(this, "Adding...", Toast.LENGTH_SHORT).show();
    }

    private void saveTitle(){
        final String titleName = mAddField.getText().toString();
        final String devName = mSpinnerDev.getSelectedItem().toString();

        mDatabase.child("developers")
                .orderByChild("developer")
                .equalTo(devName)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot devSnapshot : dataSnapshot.getChildren()) {
                            String devKey = devSnapshot.getKey();

                            String key = mDatabase.child("app_titles").push().getKey();
                            Apk apk = new Apk();

                            apk.setTitle(titleName);
                            apk.setDeveloper(devKey);
                            mDatabase.child("app_titles").child(key).setValue(apk);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Toast.makeText(this, "Adding...", Toast.LENGTH_SHORT).show();
    }

//    private void saveDpi(){
//        final String dpiName = mAddField.getText().toString();
//        String key = mDatabase.child("app_dpi").push().getKey();
//
//        Apk apk = new Apk();
//        apk.setDpi(dpiName);
//        mDatabase.child("app_dpi").child(key).setValue(apk);
//
//        Toast.makeText(this, "Adding...", Toast.LENGTH_SHORT).show();
//    }

//    private void saveMin() {
//        final String androidVersion = mAddField.getText().toString();
//        String key = mDatabase.child("android_version").push().getKey();
//
//        Apk apk = new Apk();
//        apk.setAndroidVersion(androidVersion);
//        mDatabase.child("android_version").child(key).setValue(apk);
//
//        Toast.makeText(this, "Adding...", Toast.LENGTH_SHORT).show();
//    }

    private void showTitleDialog(){
        View mView = getLayoutInflater().inflate(R.layout.dialog_add, null);

        mAddField = (EditText) mView.findViewById(R.id.dialog_add_field);
        alertBuilder.setTitle(mSpinnerTitle.getSelectedItem().toString());
        alertBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveTitle();
            }

        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertBuilder.setView(mView);
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    private void showDevDialog(){
        View mView = getLayoutInflater().inflate(R.layout.dialog_add, null);

        mAddField = (EditText) mView.findViewById(R.id.dialog_add_field);
        alertBuilder.setTitle(mSpinnerDev.getSelectedItem().toString());
        alertBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveDev();
            }

        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertBuilder.setView(mView);
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

//    private void showDpiDialog() {
//        View mView = getLayoutInflater().inflate(R.layout.dialog_add, null);
//
//        mAddField = (EditText) mView.findViewById(R.id.dialog_add_field);
//        alertBuilder.setTitle(mSpinnerDpi.getSelectedItem().toString());
//        alertBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                saveDpi();
//            }
//
//        });
//        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        alertBuilder.setView(mView);
//        AlertDialog dialog = alertBuilder.create();
//        dialog.show();
//    }

//    private void showAndroidVersionDialog() {
//        View mView = getLayoutInflater().inflate(R.layout.dialog_add, null);
//
//        mAddField = (EditText) mView.findViewById(R.id.dialog_add_field);
//        alertBuilder.setTitle(mSpinnerMin.getSelectedItem().toString());
//        alertBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                saveMin();
//            }
//
//        });
//        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        alertBuilder.setView(mView);
//        AlertDialog dialog = alertBuilder.create();
//        dialog.show();
//    }

    public void upload(){
        final String developer = mSpinnerDev.getSelectedItem().toString();
        final String title = mSpinnerTitle.getSelectedItem().toString();
        final String version = mVersionField.getText().toString();
        final String minAndroid = mSpinnerMin.getSelectedItem().toString();
        final String maxAndroid = mSpinnerMax.getSelectedItem().toString();
        final String dpi = mSpinnerDpi.getSelectedItem().toString();
        final String whatNew = mWhatNew.getText().toString();

        String appKey = mDatabase.child("apps").push().getKey();

        Apk apk = new Apk();
        apk.setDeveloper(developer);
        apk.setTitle(title);
        apk.setVersion(version);
        apk.setMinAndroidVersion(minAndroid);
        apk.setMaxAndroidVersion(maxAndroid);
        apk.setDpi(dpi);
        apk.setDescription(whatNew);

        mDatabase.child("apps").child(appKey).setValue(apk);
        Toast.makeText(this, "Adding...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload:
                upload();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



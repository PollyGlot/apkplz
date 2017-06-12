package com.example.pollyglot.apkplz;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pollyglot.apkplz.models.Apk;
import com.example.pollyglot.apkplz.models.Developer;
import com.example.pollyglot.apkplz.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.text.DateFormat.getDateTimeInstance;

public class AddApkActivity extends BaseActivity {

    private static final String REQUIRED = "Required";
    private static final String TAG = "AddApkActivity";
    private static final int SELECT_IMAGE = 0;
    private static final int SELECT_FILE =  1;


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
    private ImageButton mBtnChooseImage;
    private Button mBtnChooseFile;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    // [START declare_database_ref]
    private StorageReference mStorage;
    // [END declare_database_ref]

    private AlertDialog.Builder alertBuilder;

    //Uri object to store file path
    private Uri imgPath;

    private Bitmap selectedImage;
    private TextView mFileTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apk);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        // [START initialize_storage_ref]
        mStorage = FirebaseStorage.getInstance().getReference();
        // [END initialize_storage_ref]

        mBtnChooseImage = (ImageButton) findViewById(R.id.btn_choose_image);
        mSpinnerDev = (Spinner) findViewById(R.id.choose_developer);
        mSpinnerTitle = (Spinner) findViewById(R.id.choose_title);
        mSpinnerMin = (Spinner) findViewById(R.id.min_android);
        mSpinnerMax = (Spinner) findViewById(R.id.max_android);
        mSpinnerDpi = (Spinner) findViewById(R.id.choose_dpi_min);
        mWhatNew = (EditText) findViewById(R.id.what_new);
        mAddField = (EditText) findViewById(R.id.dialog_add_field);
        mVersionField = (EditText) findViewById(R.id.version_field);
        mBtnChooseFile = (Button) findViewById(R.id.btn_choose_file);
        mFileTxtView = (TextView) findViewById(R.id.file_title);

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

        mBtnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, 0);
            }
        });

        mBtnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.android.package-archive");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, 1);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        imgPath = data.getData();
                        try {
                            selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imgPath);
                            mBtnChooseImage.setBackground(null);
                            mBtnChooseImage.setImageBitmap(selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                break;

                case 1:
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        String uriString = uri.toString();
                        File mFile = new File(toString());
                        String filePath = mFile.getAbsolutePath();
                        String displayName = null;

                        if (uriString.startsWith("content://")) {
                            try (Cursor cursor = this.getContentResolver().query(uri, null, null, null, null)) {
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }
                            }
                        } else if (uriString.startsWith("file://")) {
                            displayName = mFile.getName();
                        }

                        mFileTxtView.setText(displayName);
                    }
                break;
            }

        }

    private String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void saveDev() {
        final String devName = mAddField.getText().toString();
        String key = mDatabase.child("developers").push().getKey();

        Developer developer = new Developer();
        developer.setDeveloper(devName);
        mDatabase.child("developers").child(key).setValue(developer);

        Toast.makeText(this, "Adding...", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("VisibleForTests")
    private void saveTitle(){
        final String titleName = mAddField.getText().toString();
        final String devName = mSpinnerDev.getSelectedItem().toString();

        if (imgPath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            String path = "app_icons/" + UUID.randomUUID() + ".png";

            StorageReference riversRef = mStorage.child(path);
            UploadTask uploadTask = riversRef.putFile(imgPath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    final String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    mDatabase.child("developers")
                            .orderByChild("developer")
                            .equalTo(devName)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot devSnapshot : dataSnapshot.getChildren()) {
                                        final String devKey = devSnapshot.getKey();
                                        final String appKey = mDatabase.child("app_titles").push().getKey();

                                        Apk apk = new Apk();

                                        apk.setTitle(titleName);
                                        apk.setDeveloper(devName);
                                        apk.setImageUrl(downloadUrl);

                                        mDatabase.child("app_titles").child(appKey).setValue(apk);
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w(TAG, "Title add error", databaseError.toException());
                                }
                            });

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot snapshot) {

                            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            progressDialog.setMessage("Upload is " + ((int) progress) + "%");
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
        }
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

//    @SuppressWarnings("VisibleForTests")
//    private void uploadImage() {
//        if (imgPath != null) {
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading");
//            progressDialog.show();
//
//            String path = "app_icons/" + UUID.randomUUID() + ".png";
//
//            StorageReference riversRef = mStorage.child(path);
//            UploadTask uploadTask = riversRef.putFile(imgPath);
//
//            uploadTask
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            final Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                            Toast.makeText(getApplicationContext(), "Text: " + downloadUrl, Toast.LENGTH_LONG).show();
//
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot snapshot) {
//
//                            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
//                            progressDialog.setMessage("Upload is " + ((int) progress) + "%");
//                        }
//                    });
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
//        }
//    }

    public void upload(){
        final String developer = mSpinnerDev.getSelectedItem().toString();
        final String title = mSpinnerTitle.getSelectedItem().toString();
        final String version = mVersionField.getText().toString();
        final String minAndroid = mSpinnerMin.getSelectedItem().toString();
        final String maxAndroid = mSpinnerMax.getSelectedItem().toString();
        final String dpi = mSpinnerDpi.getSelectedItem().toString();
        final String whatNew = mWhatNew.getText().toString();

        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        if (user == null) {
                            // User is null => error
                            Log.e(TAG, "User " + userId + " is null");
                            Toast.makeText(AddApkActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new apk

                            writeNewApk(developer, title, version, minAndroid,
                                    maxAndroid, dpi, whatNew, imageUrl, fileUrl);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    private void writeNewApk(String developer, String title, String version, String minAndroid,
                             String maxAndroid, String dpi, String whatNew, String imageUrl, String fileUrl) {

        String appKey = mDatabase.child("apps").push().getKey();
        Apk apk = new Apk(developer, title, version, minAndroid, maxAndroid, dpi, whatNew, imageUrl, fileUrl);
        Map<String, Object> apkValues = apk.apkMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/apps/" + appKey, apkValues);
        mDatabase.updateChildren(childUpdates);

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



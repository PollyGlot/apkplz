package com.example.pollyglot.apkplz.helper;

import com.example.pollyglot.apkplz.models.Apk;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseApkHelper {

//    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Boolean saved = null;

    public FirebaseApkHelper(DatabaseReference mDatabaseReference){
        this.mDatabaseReference = mDatabaseReference;
    }


    // SAVE
    public Boolean save(Apk developer){
        if (developer == null) {
            saved = false;
        } else {
            try {
                mDatabaseReference.getParent().child("app_info_general").child("developers").push().setValue(developer);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

//    // READ
//    public ArrayList<String> retrieve()
//    {
//        final ArrayList<String> developer = new ArrayList<>();
//        mDatabaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot, developer);
//            }
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot, developer);
//            }
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//            }
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//        return developer;
//    }

//    private void fetchData(DataSnapshot snapshot, ArrayList<String> developer) {
//        developer.clear();
//        for (DataSnapshot ds:snapshot.getChildren()) {
//            String name= ds.getValue(Apk.class).getDeveloper();
//            developer.add(name);
//        }
//    }
}

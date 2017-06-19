package com.example.pollyglot.apkplz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.adapters.DevTopListFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DevelopersTopFragment extends DevTopListFragment {

    public DevelopersTopFragment() {}

    public static DevelopersTopFragment newInstance() {
        return new DevelopersTopFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_developers_top, container, false);
    }

    @Override
    public Query getQuery(final DatabaseReference databaseReference) {

        Query devTopQuery = databaseReference.child("developers").limitToFirst(10);
        return devTopQuery;
    }
}

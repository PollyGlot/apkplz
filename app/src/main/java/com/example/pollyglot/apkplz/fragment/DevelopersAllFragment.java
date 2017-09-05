package com.example.pollyglot.apkplz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.adapters.DevAllListFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class DevelopersAllFragment extends DevAllListFragment {

    public DevelopersAllFragment() {}

    public static DevelopersAllFragment newInstance() {
        DevelopersAllFragment fragment = new DevelopersAllFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_developers_all, container, false);
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query devAllQuery = databaseReference.child("developers").orderByChild("developer");
        return devAllQuery;
    }
}

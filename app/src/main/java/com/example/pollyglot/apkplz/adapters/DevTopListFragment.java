package com.example.pollyglot.apkplz.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.example.pollyglot.apkplz.DevDetailActivity;
import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.models.Apk;
import com.example.pollyglot.apkplz.models.Developer;
import com.example.pollyglot.apkplz.viewholder.DevTopViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Random;

public abstract class DevTopListFragment extends Fragment {

    private static final String TAG = "DevTopListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Developer, DevTopViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FloatingActionButton mFab;

    public DevTopListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_developers_top, container, false);
        rootView.setTag(TAG);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mManager = new LinearLayoutManager(getActivity());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecycler = (RecyclerView) view.findViewById(R.id.dev_top_list);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mManager);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up FirebaseRecyclerAdapter with the Query
        Query topDevQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Developer, DevTopViewHolder>(Developer.class, R.layout.developers_top_card,
                DevTopViewHolder.class, topDevQuery) {
            @Override
            protected void populateViewHolder(final DevTopViewHolder viewHolder, final Developer model, final int position) {
                final DatabaseReference devRef = getRef(position);

                // Set click listener for the whole devCard view
                final String devKey = devRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch DevDetailActivity
                        Intent intent = new Intent(getActivity(), DevDetailActivity.class);
                        intent.putExtra(DevDetailActivity.EXTRA_DEV_KEY, devKey);
//                        intent.putExtra(DevDetailActivity.EXTRA_DEV_NAME);
                        startActivity(intent);
                    }
                });

                viewHolder.bindToDevTop(model);
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}

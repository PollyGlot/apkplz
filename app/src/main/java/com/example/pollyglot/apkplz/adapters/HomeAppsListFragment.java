package com.example.pollyglot.apkplz.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pollyglot.apkplz.AppDetailActivity;
import com.example.pollyglot.apkplz.MainActivity;
import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.fragment.HomeFragment;
import com.example.pollyglot.apkplz.models.Apk;
import com.example.pollyglot.apkplz.viewholder.HomeAppsHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public abstract class HomeAppsListFragment extends Fragment {

    private static final String TAG = "HomeAppsListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Apk, HomeAppsHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private DividerItemDecoration mDividerItemDecoration;
    private ImageView appImage;

    public HomeAppsListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
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
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);

        mRecycler = (RecyclerView) view.findViewById(R.id.home_apps_list);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mManager);

        mDividerItemDecoration = new DividerItemDecoration(mRecycler.getContext(),
                mManager.getOrientation());
        mRecycler.addItemDecoration(mDividerItemDecoration);

        appImage = (ImageView) view.findViewById(R.id.main_app_image);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        // Set up FirebaseRecyclerAdapter with the Query
        Query homeAppsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Apk, HomeAppsHolder>(Apk.class, R.layout.home_apps_list,
                HomeAppsHolder.class, homeAppsQuery) {

            @Override
            protected void populateViewHolder(final HomeAppsHolder viewHolder, final Apk model, final int position) {
                final DatabaseReference appRef = getRef(position);

                // Set click listener for the whole appCard view
                final String appKey = appRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AppDetailActivity.class);
                        intent.putExtra(AppDetailActivity.EXTRA_APP_KEY, appKey);

//                        ActivityOptionsCompat options = ActivityOptionsCompat.
//                                makeSceneTransitionAnimation(this, appImage, "appImage");

//                        ActivityOptionsCompat options = ActivityOptionsCompat.
//                                makeSceneTransitionAnimation(HomeAppsListFragment.this, appImage,
//                                        ViewCompat.getTransitionName(appImage));
                        startActivity(intent);
                    }
                });

                viewHolder.bindToHomeApps(model);
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

    protected abstract Query getQuery(DatabaseReference database);
}


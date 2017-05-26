package com.example.pollyglot.apkplz.adapters;

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

import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.models.Apk;
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

    private FirebaseRecyclerAdapter<Apk, DevTopViewHolder> mAdapter;
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
        mAdapter = new FirebaseRecyclerAdapter<Apk, DevTopViewHolder>(Apk.class, R.layout.developers_top_card,
                DevTopViewHolder.class, topDevQuery) {
            @Override
            protected void populateViewHolder(final DevTopViewHolder viewHolder, final Apk model, final int position) {
                final DatabaseReference devRef = getRef(position);

                // Set click listener for the whole post view
                final String devKey = devRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        // TODO create DevDetail Activity
//                        Intent intent = new Intent(getActivity(), DevDetailActivity.class);
//                        intent.putExtra(DevDetailActivity.EXTRA_POST_KEY, devKey);
//                        startActivity(intent);
                    }
                });

                viewHolder.bindToDevTop(model);
//                setAnimation(viewHolder.itemView, position);
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

//    private int lastPosition = -1;
//
//    private void setAnimation(View viewToAnimate, int position) {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition) {
//            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
//            viewToAnimate.startAnimation(anim);
//            lastPosition = position;
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}

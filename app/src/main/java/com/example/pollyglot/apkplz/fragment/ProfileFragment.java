package com.example.pollyglot.apkplz.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pollyglot.apkplz.R;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    private View mView;
    private FirebaseAuth mFirebaseAuth;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private Toolbar mToolbar;
    private ImageView mAvatar;
    private Bitmap selectedImage;
    private ProfilePictureView profileImage;
    Context mContext;
    private TextView mEmail;
    private TextView mId;
    private TextView mProvider;


    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_profile, container, false);

        mAvatar = (ImageView) mView.findViewById(R.id.avatar_image);
        mEmail = (TextView) mView.findViewById(R.id.profile_email_text);
        mId = (TextView) mView.findViewById(R.id.profile_id_text);
        mProvider = (TextView) mView.findViewById(R.id.profile_provider_text);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);

        mToolbar.setTitle(mFirebaseAuth.getCurrentUser().getDisplayName());
        mEmail.setText(mFirebaseAuth.getCurrentUser().getEmail());
        mId.setText(mFirebaseAuth.getCurrentUser().getUid());
        mProvider.setText(mFirebaseAuth.getCurrentUser().getProviderId());

        Glide.with(mAvatar.getContext().getApplicationContext())
                .load(mFirebaseAuth.getCurrentUser().getPhotoUrl())
                .into(mAvatar);
        return mView;
    }
}

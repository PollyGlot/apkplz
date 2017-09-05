package com.example.pollyglot.apkplz.viewholder;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.models.Apk;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static java.lang.System.load;
import static java.security.AccessController.getContext;

public class HomeAppsHolder extends RecyclerView.ViewHolder {

    public ImageView appImage;
    public TextView titleView;
    public TextView devNameView;
    public TextView versionView;

    public HomeAppsHolder(View itemView) {
        super(itemView);

        appImage = (ImageView) itemView.findViewById(R.id.main_app_image);
        titleView = (TextView) itemView.findViewById(R.id.title);
        devNameView = (TextView) itemView.findViewById(R.id.developer);
        versionView = (TextView) itemView.findViewById(R.id.version);
    }

    public void bindToHomeApps(Apk apk) {
        titleView.setText(apk.title);
        devNameView.setText(apk.developer);
        versionView.setText(apk.version);

        Glide.with(getApplicationContext())
                .load(apk.imageUrl)
                .into(appImage);
    }
}

package com.example.pollyglot.apkplz.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.models.Apk;
import com.example.pollyglot.apkplz.models.Developer;

import java.util.Random;


public class DevAllViewHolder extends RecyclerView.ViewHolder {

    public TextView devNameView;

    public DevAllViewHolder(View itemView) {
        super(itemView);

        devNameView = (TextView) itemView.findViewById(R.id.dev_all_title);
    }

    public void bindToDevAll(Developer dev) {
        devNameView.setText(dev.developer);
    }

}

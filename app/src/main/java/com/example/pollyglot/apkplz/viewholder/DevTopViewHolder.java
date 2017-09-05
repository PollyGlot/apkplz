package com.example.pollyglot.apkplz.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.models.Apk;
import com.example.pollyglot.apkplz.models.Developer;

public class DevTopViewHolder extends RecyclerView.ViewHolder {

    public TextView latestUpdate;
    public TextView devNameView;
    public ImageView appView;
    public TextView numAppView;

    public DevTopViewHolder(View itemView) {
        super(itemView);

        latestUpdate = (TextView) itemView.findViewById(R.id.dev_latest_update_include);
        devNameView = (TextView) itemView.findViewById(R.id.dev_top_title);
//        appView = (TextView) itemView.findViewById(R.id.dev_app_icon);
//        numAppView = (TextView) itemView.findViewById(R.id.dev_num_of_apps);
    }

    public void bindToDevTop(Developer dev) {

        latestUpdate.setText(dev.latestUpdate);
        devNameView.setText(dev.developer);
//        numAppView.setText(String.valueOf(dev.appsNumber));
//        numAppView.setText(dev.appsNumber);

    }
}

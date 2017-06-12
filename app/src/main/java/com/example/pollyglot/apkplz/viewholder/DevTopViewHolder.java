package com.example.pollyglot.apkplz.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.models.Apk;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DevTopViewHolder extends RecyclerView.ViewHolder {

    public TextView devTimeView;
    public TextView devNameView;
    public ImageView appView;
    public TextView numAppView;

    public DevTopViewHolder(View itemView) {
        super(itemView);

        devTimeView = (TextView) itemView.findViewById(R.id.dev_time);
        devNameView = (TextView) itemView.findViewById(R.id.dev_top_title);
        appView = (ImageView) itemView.findViewById(R.id.dev_app_icon);
        numAppView = (TextView) itemView.findViewById(R.id.dev_num_of_apps);
    }

    public void bindToDevTop(Apk apk) {
        devTimeView.setText("Latest Update " + apk.title);
        devNameView.setText(apk.developer);
//        numAppView.setText(appsNumber + " Apps");

    }
}

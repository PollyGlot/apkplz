package com.example.pollyglot.apkplz.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.models.AppsHome;

import java.util.List;

public class ListHomeAdapter extends RecyclerView.Adapter<ListHomeAdapter.ListHomeViewHolder> {

    private List<AppsHome> mList;


    class ListHomeViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDeveloper;
        TextView txtVersion;

        public ListHomeViewHolder(View itemView){
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.app_name);
            txtDeveloper = (TextView) itemView.findViewById(R.id.app_dev);
            txtVersion = (TextView) itemView.findViewById(R.id.app_version);
        }
    }

    @Override
    public ListHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListHomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_home, parent, false));
    }

    @Override
    public void onBindViewHolder(ListHomeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}


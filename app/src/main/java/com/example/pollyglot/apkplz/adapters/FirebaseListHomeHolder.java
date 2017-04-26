//package com.example.pollyglot.apkplz.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.TextView;
//
//import com.example.pollyglot.apkplz.R;
//import com.example.pollyglot.apkplz.models.AppsHome;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//import static android.R.attr.onClick;
//
//public class FirebaseListHomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//    private static final int MAX_WIDTH = 200;
//    private static final int MAX_HEIGHT = 200;
//    private static final String FIREBASE_APPS_HOME = "AppsHome";
//
//    View mView;
//    Context mContext;
//
//    public FirebaseListHomeHolder (View itemView){
//        super(itemView);
//
//        mView = itemView;
//        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
//    }
//
//    public void bindAppsHome(AppsHome appsHome) {
//        TextView nameTextView = (TextView) mView.findViewById(R.id.app_name);
//        TextView developerTextView = (TextView) mView.findViewById(R.id.app_dev);
//        TextView versionTextView = (TextView) mView.findViewById(R.id.app_version);
//
//        nameTextView.setText(appsHome.getName());
//        developerTextView.setText("by" + appsHome.getDeveloper() + "");
//        versionTextView.setText(appsHome.getVersion());
//    }
//
//    @Override
//    public void onClick(View v) {
//        final ArrayList<AppsHome> appsHome = new ArrayList<>();
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FIREBASE_APPS_HOME);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    appsHome.add(snapshot.getValue(AppsHome.class));
//                }
//
//                int itemPosition = getLayoutPosition();
//
////                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
////                intent.putExtra("position", itemPosition + "");
////                intent.putExtra("restaurants", Parcels.wrap(restaurants));
//
////                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//}

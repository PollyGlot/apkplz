package com.example.pollyglot.apkplz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.TabLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
//import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.pollyglot.apkplz.auth.LoginActivity;
import com.example.pollyglot.apkplz.fragment.DevelopersFragment;
import com.example.pollyglot.apkplz.fragment.HomeFragment;
import com.example.pollyglot.apkplz.fragment.PopularFragment;
import com.example.pollyglot.apkplz.fragment.ProfileFragment;
import com.example.pollyglot.apkplz.helper.BottomNavigationViewHelper;
import com.example.pollyglot.apkplz.models.User;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends BaseActivity {

    // Another method
    // https://goo.gl/5YykEB

    private Toolbar mToolbar;
    private User user;
    private BottomNavigationView mBottomBar;
    private FloatingActionButton mFab;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mIntent = new Intent(this, LoginActivity.class);

//        recyclerView = (RecyclerView) findViewById(R.id.home_apps_list);

        FirebaseMessaging.getInstance().subscribeToTopic("apps");

//        Snackbar snackbar = Snackbar
//                .make(findViewById(R.id.showSnackbar),
//                        "Logged in as hello world", Snackbar.LENGTH_LONG);
//        snackbar.show();


//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);



        mFab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), AddApkActivity.class);
                view.getContext().startActivity(Intent);
            }
        });

        mBottomBar = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(mBottomBar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame_layout, HomeFragment.newInstance());
        transaction.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = HomeFragment.newInstance();
                    break;
                case R.id.navigation_developers:
                    selectedFragment = DevelopersFragment.newInstance();
                    break;
                case R.id.navigation_popular:
                    selectedFragment = PopularFragment.newInstance();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = ProfileFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    //sign out method
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(mIntent);
    }

    // Toolbar Items on Main
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent startSettingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;

            case R.id.about:
                Intent startAboutActivity = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(startAboutActivity);
                return true;

            case R.id.logout:
                signOut();
                return true;

            default:
                break;
        }
        return false;
    }
}

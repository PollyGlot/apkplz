package com.example.pollyglot.apkplz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static enum Mode {
        NORMAL, ADD, SEARCH;
    }

    public static Mode mode;

    private Toolbar mToolbar;
    private BottomNavigationView mBottomBar;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mode = Mode.NORMAL;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), AddApk.class);
                view.getContext().startActivity(Intent);
            }
        });

        mBottomBar = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(mBottomBar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

//        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment = null;
//                switch (item.getItemId()) {
//                    case R.id.navigation_home:
//                        selectedFragment = HomeFragment.newInstance();
//                        break;
//                    case R.id.navigation_developers:
//                        selectedFragment = DevelopersTopFragment.newInstance();
//                        break;
//                    case R.id.navigation_popular:
//                        selectedFragment = PopularFragment.newInstance();
//                        break;
//                    case R.id.navigation_profile:
//                        selectedFragment = ProfileFragment.newInstance();
//                        break;
//                }
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, selectedFragment);
//                transaction.commit();
//                return true;
//            }
//
//        });
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
//        transaction.commit();

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
                    selectedFragment = DevelopersTopFragment.newInstance();
                    break;
                case R.id.navigation_popular:
                    selectedFragment = PopularFragment.newInstance();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = ProfileFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;


        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_more) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

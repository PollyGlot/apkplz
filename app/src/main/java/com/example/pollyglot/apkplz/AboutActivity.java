package com.example.pollyglot.apkplz;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    public static String SHARE_CONTENT = "A beautiful app designed with Material Design:";
    public static String EMAIL = "mailto: paul.trinko95@gmail.com";
    public static String FACEBOOK_URL = "https://www.facebook.com/paul.trinko";
    public static String INSTAGRAM_URL = "https://www.instagram.com/polly_glot";

    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(R.string.about_activity_title);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initView();
    }

    public void initView() {
        LinearLayout developer_card = (LinearLayout) findViewById(R.id.developer_card);
        LinearLayout email_card = (LinearLayout) findViewById(R.id.email_card);
        LinearLayout facebook_card = (LinearLayout) findViewById(R.id.facebook_card);
        LinearLayout instagram_card = (LinearLayout) findViewById(R.id.instagram_card);
        ScrollView scroll_about = (ScrollView) findViewById(R.id.scroll_about);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_about_card_show);
        scroll_about.startAnimation(animation);

        developer_card.setOnClickListener(this);
        email_card.setOnClickListener(this);
        facebook_card.setOnClickListener(this);
        instagram_card.setOnClickListener(this);

        mFab = (FloatingActionButton) findViewById(R.id.fab_about_share);
        mFab.setOnClickListener(this);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setStartOffset(600);

        TextView about_version = (TextView) findViewById(R.id.about_version);
        about_version.setText(getVersionName());
        about_version.startAnimation(alphaAnimation);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.email_card:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

                emailIntent.setType("plain/text");
                emailIntent.setData(Uri .parse(EMAIL));
//                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{EMAIL});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;

            case R.id.facebook_card:
                intent.setData(Uri.parse(FACEBOOK_URL));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                break;

            case R.id.instagram_card:
                intent.setData(Uri.parse(INSTAGRAM_URL));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                break;

            case R.id.fab_about_share:
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, SHARE_CONTENT);
                intent.setType("text/plain");
                startActivity(intent);
                break;
        }

    }

    private String getVersionName() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return getString(R.string.about_version) + " " + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}

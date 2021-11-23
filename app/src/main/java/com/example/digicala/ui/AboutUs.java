package com.example.digicala.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.broadcast.InternetBroadCast;
import com.example.digicala.sharedpreferences.SharePrefernce;

public class AboutUs extends InternetBroadCast {

    private TextView namesample;
    private TextView name;
    private TextView phonesample;
    private TextView phone;
    private TextView classinfromation;
    private ImageView logo;

    private SharePrefernce sharePrefernce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharePrefernce = new SharePrefernce(AboutUs.this);
        if (sharePrefernce.gettheme()) {

            setTheme(R.style.ThemeOverlay_AppCompat_Dark);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        init();
        setting();

    }

    private void init() {

        namesample = findViewById(R.id.tv_name_sample_about_us);
        name = findViewById(R.id.tv_name_about_us);
        phone = findViewById(R.id.tv_phone_about_us);
        phonesample = findViewById(R.id.tv_phone_sample_about_us);
        classinfromation = findViewById(R.id.tv_class_information_about_us);
        logo = findViewById(R.id.im_about_us);

        boolean loginstate = sharePrefernce.getloginstate();
        if (loginstate == false) {
            Intent intent = new Intent(AboutUs.this, Login.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.m_back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);


    }

    private void setting() {
        namesample.setTextSize((namesample.getTextSize() / 3) + sharePrefernce.getfontsize());
        name.setTextSize((name.getTextSize() / 3) + sharePrefernce.getfontsize());
        phonesample.setTextSize((phonesample.getTextSize() / 3) + sharePrefernce.getfontsize());
        phone.setTextSize((phone.getTextSize() / 3) + sharePrefernce.getfontsize());
        classinfromation.setTextSize((classinfromation.getTextSize() / 3) + sharePrefernce.getfontsize());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

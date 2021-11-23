package com.example.digicala.ui;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.broadcast.InternetBroadCast;
import com.example.digicala.sharedpreferences.SharePrefernce;

public class Setting extends InternetBroadCast {


    private SeekBar seekBar;
    private TextView sampletext, sizeshow, theme;
    private ImageView light, dark;

    private TextView tv_text_size;
    private TextView tv_theme;
    private TextView tv_text_logo_setting;


    int primsize;

    SharePrefernce sharePrefernce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharePrefernce = new SharePrefernce(Setting.this);


        if (sharePrefernce.gettheme()) {

            setTheme(R.style.ThemeOverlay_AppCompat_Dark);

        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
        setting();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                sampletext.setTextSize(primsize + progress);
                sizeshow.setText(String.valueOf(primsize + progress));
                sharePrefernce.setfontsize(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void init() {

        sampletext = findViewById(R.id.tv_sample_text_size);
        seekBar = findViewById(R.id.setting_seekbar);
        sizeshow = findViewById(R.id.tv_size_show);
        theme = findViewById(R.id.tv_theme);
        light = findViewById(R.id.im_light);
        dark = findViewById(R.id.im_dark);
        tv_text_size = findViewById(R.id.tv_text_size);
        tv_theme = findViewById(R.id.tv_theme);
        tv_text_logo_setting = findViewById(R.id.tv_text_logo_setting);
        seekBar.setMax(3);
        seekBar.setMin(-3);
        seekBar.setProgress(0);
        primsize = (int) sampletext.getTextSize() / 3;
        sizeshow.setText(String.valueOf(primsize));
        seekBar.setProgress(primsize);

        boolean loginstate = sharePrefernce.getloginstate();
        if (loginstate == false) {
            Intent intent = new Intent(Setting.this, Login.class);
            startActivity(intent);
            finish();
        }
        if (sharePrefernce.gettheme() == false) {
//            light.animate().alpha(1f).setDuration(1000);
//            dark.animate().alpha(0.2f).setDuration(1000);
            light.setAlpha(1f);
            dark.setAlpha(0.2f);
        } else {

            dark.setAlpha(1f);
            light.setAlpha(0.2f);

        }

    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Setting.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }


    public void lightonclick(View view) {

        light.animate().alpha(1f).setDuration(1000);
        dark.animate().alpha(0.2f).setDuration(1000);
        sharePrefernce.changetheme(false);


    }

    public void darkonclick(View view) {

        dark.animate().alpha(1f).setDuration(1000);
        light.animate().alpha(0.2f).setDuration(1000);
        sharePrefernce.changetheme(true);


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

    public void setting() {


        tv_text_size.setTextSize((tv_text_size.getTextSize() / 3) + sharePrefernce.getfontsize());
        tv_theme.setTextSize((tv_theme.getTextSize() / 3) + sharePrefernce.getfontsize());
        tv_text_logo_setting.setTextSize((tv_text_logo_setting.getTextSize() / 3) + sharePrefernce.getfontsize());

    }

}

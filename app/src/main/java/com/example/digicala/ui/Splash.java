package com.example.digicala.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.broadcast.InternetBroadCast;
import com.example.digicala.database.ProductsDbHelper;
import com.example.digicala.entity.Product;
import com.example.digicala.sharedpreferences.SharePrefernce;
import com.example.digicala.view.SplashView;
import com.example.digicala.webserviceapi.DigiCalaApi;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Splash extends InternetBroadCast {


    Timer timer;
    SplashView splashView;
    SharePrefernce sharePrefernce;
    ImageView imlogo;

    int splashspeed = 20;

    Retrofit retrofit;

    ProductsDbHelper productsDbHelper;

    ArrayList<Product> data;


    private static final String TAG = "Splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        init();
        splashmove();
        showlogo();


    }


    private void init() {

        productsDbHelper = new ProductsDbHelper(Splash.this);
        data = new ArrayList();
        splashView = findViewById(R.id.logo_view);
        imlogo = findViewById(R.id.im_Logo_text);
        sharePrefernce = new SharePrefernce(Splash.this);


    }

    private void splashmove() {


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (splashView.height > splashView.bottom) {
                    splashView.top += splashspeed;
                    splashView.bottom += splashspeed;

                    splashView.invalidate();
                }

            }
        }, 0, 50);


    }

    private void showlogo() {

        imlogo.animate().alpha(1f).setDuration(1500);


    }

    public Splash() {


    }


}

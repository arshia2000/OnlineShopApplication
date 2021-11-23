package com.example.digicala.broadcast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.database.ProductsDbHelper;
import com.example.digicala.entity.Product;
import com.example.digicala.sharedpreferences.SharePrefernce;
import com.example.digicala.ui.Login;
import com.example.digicala.ui.Splash;
import com.example.digicala.webserviceapi.DigiCalaApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InternetBroadCast extends AppCompatActivity {

    private Check broadcast;
    public IntentFilter intentFilter;
    public AlertDialog alertDialog;
    public AlertDialog.Builder builder;
    public static boolean alert_status = false;
    private Button retry;

    Context context_main;

    ArrayList<Product> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AlertDialog.Builder builder = new AlertDialog.Builder(InternetBroadCast.this);
        View customview = LayoutInflater.from(InternetBroadCast.this).inflate(R.layout.custom_alert_dialog, null);
        builder.setView(customview);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setView(customview);
        retry = customview.findViewById(R.id.btn_custom_alert_wifi);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                broadcast = new Check(InternetBroadCast.this);
                intentFilter = new IntentFilter(Check.ACTION_NAME);
                registerReceiver(broadcast, intentFilter);

            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onStart() {

        broadcast = new Check(InternetBroadCast.this);
        intentFilter = new IntentFilter(Check.ACTION_NAME);
        registerReceiver(broadcast, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {

        unregisterReceiver(broadcast);


        super.onStop();
    }

    public class Check extends BroadcastReceiver {


        private static final String TAG = "broadcast";
        //Context context_main;
        public static final String ACTION_NAME = "android.net.conn.CONNECTIVITY_CHANGE";

        public Check(Context context) {
            context_main = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            try {


                if (isOnline(context)) {

                    alertDialog.dismiss();
                    alert_status = false;




                    if (context_main instanceof Splash){


                        intent_main_activity();

                    }

                    Log.e(TAG, "onReceive:network avaliabele ");

                } else {

                    alertDialog.show();
                    alert_status = true;
                    Log.e(TAG, "onReceive:network unavable");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
    }


    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void intent_main_activity() {

        ProductsDbHelper productsDbHelper = new ProductsDbHelper(InternetBroadCast.this);
        SharePrefernce sharePrefernce = new SharePrefernce(InternetBroadCast.this);
        Retrofit retrofit;


        int itemnumber = productsDbHelper.record_number();

        if (itemnumber < 1) {
            retrofit = new Retrofit.Builder().baseUrl(DigiCalaApi.base_url).addConverterFactory(GsonConverterFactory.create()).build();

            DigiCalaApi api = retrofit.create(DigiCalaApi.class);
            Call<ArrayList<Product>> request = api.getallproduct();
            request.enqueue(new Callback<ArrayList<Product>>() {
                @Override
                public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {

                    data = response.body();

                    for (Product item : data) {

                        productsDbHelper.insert(item);

                    }

                    boolean loginstate = sharePrefernce.getloginstate();

                    if (loginstate == false) {
                        Intent intent = new Intent(context_main, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(context_main, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }


                @Override
                public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

                    // Log.e(TAG, "onFailure");
                }


            });


        } else {


            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    boolean loginstate = sharePrefernce.getloginstate();

                    if (loginstate == false) {
                        Intent intent = new Intent(context_main, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(context_main, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }.start();


        }
    }



}

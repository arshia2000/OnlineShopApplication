package com.example.digicala.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.adaptor.Adaptor;
import com.example.digicala.broadcast.InternetBroadCast;
import com.example.digicala.database.ProductsDbHelper;
import com.example.digicala.entity.Product;
import com.example.digicala.sharedpreferences.SharePrefernce;

import java.util.ArrayList;

public class Cart extends InternetBroadCast {

    ImageView im_cart;
    TextView tv_cart;
    TextView tv_list_cart;
    ListView lv_cart;
    SharePrefernce sharePrefernce;

    ProductsDbHelper productsDbHelper;
    Adaptor adapter;

    ArrayList<Product> cartdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharePrefernce = new SharePrefernce(Cart.this);
        if (sharePrefernce.gettheme()) {

            setTheme(R.style.ThemeOverlay_AppCompat_Dark);

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        init();
        setting();

        if (cartdata.size() > 0) {

            im_cart.setVisibility(View.GONE);
            tv_cart.setVisibility(View.GONE);

            tv_list_cart.setVisibility(View.VISIBLE);
            lv_cart.setVisibility(View.VISIBLE);

            lv_cart.setAdapter(adapter);


        }

    }


    private void init() {
        im_cart = findViewById(R.id.im_cart);
        tv_cart = findViewById(R.id.tv_cart);
        tv_list_cart = findViewById(R.id.tv_list_cart);
        lv_cart = findViewById(R.id.lv_cart);


        boolean loginstate = sharePrefernce.getloginstate();
        if (loginstate == false) {
            Intent intent = new Intent(Cart.this, Login.class);
            startActivity(intent);
            finish();
        }

        productsDbHelper = new ProductsDbHelper(Cart.this);


        cartdata = new ArrayList<>();
        cartdata = productsDbHelper.orderd_data();
        adapter = new Adaptor(Cart.this, cartdata, true);


    }

    private void setting() {
        tv_cart.setTextSize((tv_cart.getTextSize() / 3) + sharePrefernce.getfontsize());
        tv_list_cart.setTextSize((tv_list_cart.getTextSize() / 3) + sharePrefernce.getfontsize());

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cartdata = productsDbHelper.orderd_data();
        if (cartdata.size() > 0) {

            adapter = new Adaptor(Cart.this, cartdata, true);
            lv_cart.setAdapter(adapter);

        } else {

            im_cart.setVisibility(View.VISIBLE);
            tv_cart.setVisibility(View.VISIBLE);

            tv_list_cart.setVisibility(View.GONE);
            lv_cart.setVisibility(View.GONE);

            lv_cart.setAdapter(adapter);

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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}

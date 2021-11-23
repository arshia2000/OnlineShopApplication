package com.example.digicala.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.adaptor.Adaptor;
import com.example.digicala.broadcast.InternetBroadCast;
import com.example.digicala.database.ProductsDbHelper;
import com.example.digicala.entity.Product;
import com.example.digicala.sharedpreferences.SharePrefernce;

import java.util.ArrayList;
import java.util.Spliterators;

public class Category extends InternetBroadCast {


    private ListView listView;
    private GridView gridView;
    private ImageView gridsymbol;
    private ImageView listsymbol;

    private SharePrefernce sharePrefernce;


    ArrayList<Product> data;
    ProductsDbHelper productsDbHelper;
    Adaptor adaptor;

    private String category = "";
    private boolean listorgrid = false;
    private int rotate_grid = -1;
    private int rotate_list = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        sharePrefernce = new SharePrefernce(Category.this);


        if (sharePrefernce.gettheme()) {

            setTheme(R.style.ThemeOverlay_AppCompat_Dark);

        }

        init();

    }

    private void init() {
        listView = findViewById(R.id.lv_category);
        gridView = findViewById(R.id.gv_category);
        gridsymbol = findViewById(R.id.im_grid_category_symbol);
        listsymbol = findViewById(R.id.im_list_simbol_category);
        sharePrefernce = new SharePrefernce(Category.this);

        boolean loginstate = sharePrefernce.getloginstate();
        if (loginstate == false) {
            Intent intent = new Intent(Category.this, Login.class);
            startActivity(intent);
            finish();
        }

        category = getIntent().getStringExtra("category");

        productsDbHelper = new ProductsDbHelper(Category.this);
        data = new ArrayList<>();

        data = productsDbHelper.getcategory(category);
        adaptor = new Adaptor(Category.this, data, listorgrid);
        listView.setAdapter(adaptor);

        adaptor = new Adaptor(Category.this, data, false);
        gridView.setAdapter(adaptor);

        gridsymbol.setAlpha(1f);
        listsymbol.setAlpha(0.2f);
    }

    public void gridclick(View view) {

        if (listorgrid == true) {

            rotate_grid *= -1;
            gridsymbol.animate().alpha(1f).setDuration(1000);
            listsymbol.animate().alpha(0.2f).setDuration(1000);
            gridsymbol.animate().rotationX(rotate_grid * 360).setDuration(1000);
            listView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            listorgrid = false;
            adaptor = new Adaptor(Category.this, data, listorgrid);
            gridView.setAdapter(adaptor);

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

    public void listclick(View view) {

        if (listorgrid == false) {

            rotate_list *= -1;
            listsymbol.animate().alpha(1f).setDuration(1000);
            gridsymbol.animate().alpha(0.2f).setDuration(1000);
            listsymbol.animate().rotation(rotate_list * 360f).setDuration(1000);
            gridView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listorgrid = true;
            adaptor = new Adaptor(Category.this, data, listorgrid);
            listView.setAdapter(adaptor);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

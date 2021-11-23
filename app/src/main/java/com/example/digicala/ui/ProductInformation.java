package com.example.digicala.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.broadcast.InternetBroadCast;
import com.example.digicala.database.ProductsDbHelper;
import com.example.digicala.entity.Product;
import com.example.digicala.sharedpreferences.SharePrefernce;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ProductInformation extends InternetBroadCast {


    private ImageView productimage;
    private TextView tv_title, tv_information, tv_state, tv_price, tv_ratingbar;
    private RatingBar ratingBar;
    private Button btn_order;
    private int id;
    private SharePrefernce sharePrefernce;
    Product product;

    boolean isordered = false;

    ProductsDbHelper productsDbHelper;

    int num_rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharePrefernce = new SharePrefernce(ProductInformation.this);
        if (sharePrefernce.gettheme()) {

            setTheme(R.style.ThemeOverlay_AppCompat_Dark);

        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        init();
        setting();

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productsDbHelper.get_order_by_id(id).equals("false")) {

                    productsDbHelper.insertorder("true", id);

                    btn_order.setBackgroundColor(getResources().getColor(R.color.red_color));
                    btn_order.setText("حذف از سبد");

                } else if (productsDbHelper.get_order_by_id(id).equals("true")) {

                    // isordered = false;
                    productsDbHelper.insertorder("false", id);
                    btn_order.setBackgroundColor(getResources().getColor(R.color.green_color));
                    btn_order.setText("اضافه کردن به سبد");
                }
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                num_rate = (int) rating;

                productsDbHelper.insertrate(num_rate, id);


            }
        });


    }

    private void setting() {
        tv_title.setTextSize((tv_title.getTextSize() / 3) + sharePrefernce.getfontsize());
        tv_price.setTextSize((tv_price.getTextSize() / 3) + sharePrefernce.getfontsize());
        tv_information.setTextSize((tv_information.getTextSize() / 3) + sharePrefernce.getfontsize());
        tv_state.setTextSize((tv_state.getTextSize() / 3) + sharePrefernce.getfontsize());
        tv_ratingbar.setTextSize((tv_ratingbar.getTextSize() / 3) + sharePrefernce.getfontsize());
        if (sharePrefernce.gettheme()) {
            tv_price.setTextColor(getResources().getColor(R.color.white_color));
        }
    }


    private void init() {

        id = getIntent().getIntExtra("id", 0);
        productsDbHelper = new ProductsDbHelper(ProductInformation.this);
        product = productsDbHelper.selectByid(id);


        productimage = findViewById(R.id.img_information);
        tv_title = findViewById(R.id.tv_title_information);
        tv_information = findViewById(R.id.tv_information_information);
        tv_state = findViewById(R.id.tv_state_information);
        tv_price = findViewById(R.id.tv_price_information);
        btn_order = findViewById(R.id.btn_order_information);
        tv_ratingbar = findViewById(R.id.tv_rating_bar);

        boolean loginstate = sharePrefernce.getloginstate();
        if (loginstate == false) {
            Intent intent = new Intent(ProductInformation.this, Login.class);
            startActivity(intent);
            finish();
        }


        ratingBar = findViewById(R.id.rating_bar);


        Picasso.get().load(product.getImg()).into(productimage);
        tv_title.setText(product.getTitle());

        tv_information.setText("مشخصات محصول :" + product.getInformation());
        tv_price.setText(product.getPrice() + " تومان ");
        if (product.isExists().equals("true")) {
            tv_state.setText("موجود");
            tv_state.setBackground(getResources().getDrawable(R.drawable.custom_textview_exists));
            isordered = true;
        } else {
            btn_order.setBackgroundColor(Color.GRAY);
            btn_order.setClickable(false);
            tv_state.setText("ناموجود");
            tv_state.setBackground(getResources().getDrawable(R.drawable.custom_textview_do_not_exist));
            isordered = false;
        }

        ratingBar.setRating(product.getRate());

        ratingBar.setRating(product.getRate());
        if (product.getOrder().equals("true")) {
            btn_order.setBackgroundColor(getResources().getColor(R.color.red_color));
            btn_order.setText("حذف از سبد");
        } else if (product.getOrder().equals("false")) {
            btn_order.setBackgroundColor(getResources().getColor(R.color.green_color));
            btn_order.setText("اضافه کردن به سبد");

        }

    }

    private void checkpermision() {

        if (ActivityCompat.checkSelfPermission(ProductInformation.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProductInformation.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
        }
    }


    public void callclick(View view) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + "09384227751"));

        if (ActivityCompat.checkSelfPermission(ProductInformation.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProductInformation.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
        } else {
            startActivity(callIntent);
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

    public void smsclick(View view) {

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", "default content");
        sendIntent.setType("vnd.android-dir/mms-sms");
        if (ActivityCompat.checkSelfPermission(ProductInformation.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProductInformation.this, new String[]{Manifest.permission.SEND_SMS}, 0);
        } else {
            startActivity(sendIntent);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

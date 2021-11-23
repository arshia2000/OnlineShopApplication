package com.example.digicala;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.digicala.adaptor.Adaptor;
import com.example.digicala.adaptor.ImageSliderAdaptor;
import com.example.digicala.adaptor.RecycleAdaptor;
import com.example.digicala.broadcast.InternetBroadCast;
import com.example.digicala.database.ProductsDbHelper;
import com.example.digicala.entity.ImageCategory;
import com.example.digicala.entity.Product;
import com.example.digicala.gps.MapsActivity;
import com.example.digicala.sharedpreferences.SharePrefernce;
import com.example.digicala.ui.AboutUs;
import com.example.digicala.ui.Cart;
import com.example.digicala.ui.Category;
import com.example.digicala.ui.DownloadActivity;
import com.example.digicala.ui.Login;
import com.example.digicala.ui.Setting;
import com.example.digicala.ui.Splash;
import com.example.digicala.webserviceapi.DigiCalaApi;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends InternetBroadCast implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private SharePrefernce sharePrefernce;

    private Adaptor normaladaptor;
    private RecycleAdaptor recycleAdaptor;
    private ImageSliderAdaptor imageSliderAdaptor;
    private Retrofit retrofit;


    private GridView gridView;
    private ListView listView;
    private RecyclerView recyclerView;
    private SliderView sliderView;
    private RelativeLayout containerlayout;
    private LinearLayout drawerlayout;

    private EditText et_search;
    private TextView tv_list_name;

    private ImageView back_search;
    private ImageView gridsymbol;
    private ImageView listsymbol;
    private ImageView digitalproduct;
    private ImageView homeproduct;
    private ImageView clothesproduct;

    private CardView card_home;
    private CardView card_digital;
    private CardView card_cloth;


    ArrayList<Product> data;
    ArrayList<Product> list;
    ArrayList<Product> recycledata;

    private int rotate_list = -1;
    private int rotate_grid = -1;
    private static int count = 2;
    private int main_exite = 0;
    private int main_state = 0;



    ProductsDbHelper productsDbHelper;

    public static drawermenu category_state_menu;
    public static category category_state_icon;

    private boolean listorgrid = false;


    ArrayList<ImageCategory> sliderdata;

    TextView tv_username;

    String username;


    private static final String TAG = "MainActivity";


    private void setting() {

        tv_list_name.setTextSize((tv_list_name.getTextSize() / 3) + sharePrefernce.getfontsize());
        if (sharePrefernce.gettheme()) {

            containerlayout.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            drawerlayout.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            recyclerView.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            navigationView.setBackgroundColor(getResources().getColor(R.color.dark_gray));

        }


    }


    public void backsearch(View view) {

        back_search.setVisibility(View.GONE);
        et_search.setVisibility(View.INVISIBLE);
        sliderView.setVisibility(View.VISIBLE);
        containerlayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        card_cloth.setVisibility(View.VISIBLE);
        card_digital.setVisibility(View.VISIBLE);
        card_home.setVisibility(View.VISIBLE);
        tv_list_name.setVisibility(View.VISIBLE);

        main_state = 0;

        list = productsDbHelper.select();
        normaladaptor = new Adaptor(MainActivity.this, list, listorgrid);
        gridView.setAdapter(normaladaptor);
        setListViewHeightBasedOnChildrengrid(gridView);
        listView.setAdapter(normaladaptor);
        setListViewHeightBasedOnChildren(listView);


    }


    public enum category {

        DIGITAL_PRODUCT,
        HOME_PRODUCT,
        CLOTHES_PRODUCT;


    }


    public enum drawermenu {

        DIGITAL_PRODUCT,
        HOME_PRODUCT,
        HOMESTATE;


    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharePrefernce = new SharePrefernce(MainActivity.this);
        if (sharePrefernce.gettheme()) {
            setTheme(R.style.ThemeOverlay_AppCompat_Dark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_drawer);
        init();
        setting();


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = et_search.getText().toString();
                if (text.isEmpty()) {
                    normaladaptor = new Adaptor(MainActivity.this, list, true);
                    listView.setAdapter(normaladaptor);
                    normaladaptor = new Adaptor(MainActivity.this, list, false);
                    gridView.setAdapter(normaladaptor);
                }
                normaladaptor.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void init() {


        productsDbHelper = new ProductsDbHelper(MainActivity.this);
        Intent download_intent = getIntent();
        String dispath = download_intent.getStringExtra("download_message");

        if (dispath != null) {

            Toast.makeText(MainActivity.this, dispath, Toast.LENGTH_LONG).show();
        }

        drawerinit();
        recycleviewinit();
        sliderviewinit();

        data = new ArrayList<>();
        tv_username = findViewById(R.id.tv_textdrawer);
        username = sharePrefernce.getusername();
        tv_username.setText(username);

        gridView = findViewById(R.id.gv_list);
        gridsymbol = findViewById(R.id.im_grid_symbol);
        digitalproduct = findViewById(R.id.im_digital_product);
        homeproduct = findViewById(R.id.im_home_product);
        clothesproduct = findViewById(R.id.im_clothes_product);
        et_search = findViewById(R.id.et_search);

        listView = findViewById(R.id.lv_list);
        listsymbol = findViewById(R.id.im_list_simbol);
        containerlayout = findViewById(R.id.layout_container);
        drawerlayout = findViewById(R.id.picture);
        back_search = findViewById(R.id.im_back_search);
        tv_list_name = findViewById(R.id.tv_list_name);


        card_home = findViewById(R.id.cd1);
        card_digital = findViewById(R.id.cd2);
        card_cloth = findViewById(R.id.cd3);


        recycledata = new ArrayList<>();
        list = new ArrayList<>();


        list = productsDbHelper.select();
        recycledata = productsDbHelper.selectdiscount();

        normaladaptor = new Adaptor(MainActivity.this, list, listorgrid);
        listView.setVisibility(View.GONE);
        listsymbol.animate().alpha(0.2f);
        recycleAdaptor = new RecycleAdaptor(MainActivity.this, recycledata);


        gridView.setAdapter(normaladaptor);
        setListViewHeightBasedOnChildrengrid(gridView);
        recyclerView.setAdapter(recycleAdaptor);


    }


    private void drawerinit() {

        drawer = findViewById(R.id.mainactivity_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.m_search:
                et_search.setVisibility(View.VISIBLE);
                back_search.setVisibility(View.VISIBLE);
                sliderView.setVisibility(View.GONE);
                containerlayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                card_cloth.setVisibility(View.GONE);
                card_digital.setVisibility(View.GONE);
                card_home.setVisibility(View.GONE);
                tv_list_name.setVisibility(View.GONE);
                main_state = 1;


                break;

            case R.id.m_cart:
                Intent intent = new Intent(MainActivity.this, Cart.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.m_home:
                if (category_state_menu != drawermenu.HOMESTATE) {
                    sliderView.setVisibility(View.VISIBLE);
                    containerlayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    category_state_menu = drawermenu.HOMESTATE;
                    digitalproduct.animate().alpha(1f).setDuration(500);
                    homeproduct.animate().alpha(1f).setDuration(500);
                    clothesproduct.animate().alpha(1f).setDuration(500);

                    list = productsDbHelper.select();
                    normaladaptor = new Adaptor(MainActivity.this, list, listorgrid);
                    listView.setAdapter(normaladaptor);

                    normaladaptor = new Adaptor(MainActivity.this, list, false);
                    gridView.setAdapter(normaladaptor);
                }

                break;

            case R.id.m_download:
                Intent intent_download = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent_download);
                break;

            case R.id.m_digital:

                if (category_state_menu != drawermenu.DIGITAL_PRODUCT) {
                    sliderView.setVisibility(View.GONE);
                    containerlayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    category_state_menu = drawermenu.DIGITAL_PRODUCT;

                    digitalproduct.animate().alpha(1f).setDuration(500);
                    homeproduct.animate().alpha(0.5f).setDuration(500);
                    clothesproduct.animate().alpha(0.5f).setDuration(500);


                    data = productsDbHelper.getcategory("digital");
                    normaladaptor = new Adaptor(MainActivity.this, data, listorgrid);
                    listView.setAdapter(normaladaptor);

                    normaladaptor = new Adaptor(MainActivity.this, data, false);
                    gridView.setAdapter(normaladaptor);

                }
                break;
            case R.id.home_product:
                if (category_state_menu != drawermenu.HOME_PRODUCT) {
                    sliderView.setVisibility(View.GONE);
                    containerlayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    category_state_menu = drawermenu.HOME_PRODUCT;

                    digitalproduct.animate().alpha(0.5f).setDuration(500);
                    homeproduct.animate().alpha(1f).setDuration(500);
                    clothesproduct.animate().alpha(0.5f).setDuration(500);

                    data = productsDbHelper.getcategory("home");
                    normaladaptor = new Adaptor(MainActivity.this, data, listorgrid);
                    listView.setAdapter(normaladaptor);

                    normaladaptor = new Adaptor(MainActivity.this, data, false);
                    gridView.setAdapter(normaladaptor);

                }

                break;
            case R.id.m_setting:

                Intent intent_setting = new Intent(MainActivity.this, Setting.class);
                startActivity(intent_setting);
                finish();

                break;
            case R.id.m_log_out:

                sharePrefernce.changeloginstate(false);
                sharePrefernce.setusername("");
                Intent intent_logout = new Intent(MainActivity.this, Login.class);
                startActivity(intent_logout);
                finish();

                break;


            case R.id.m_about_us:
                Intent intent_about_us = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent_about_us);
                break;

            case R.id.m_contact_us:
                Intent intent_contact_us = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent_contact_us);
                break;

            case R.id.m_shopping:
                Intent intent_shopping = new Intent(MainActivity.this, Cart.class);
                startActivity(intent_shopping);
                break;
        }
        return false;
    }

    private void recycleviewinit() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void sliderviewinit() {

        sliderView = findViewById(R.id.slider);
        slidersetdata();
        imageSliderAdaptor = new ImageSliderAdaptor(MainActivity.this, sliderdata);
        sliderView.setSliderAdapter(imageSliderAdaptor);
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

    }

    private void slidersetdata() {
        sliderdata = new ArrayList<>();
        sliderdata.add(new ImageCategory(R.drawable.book, "دانلود انواع کتاب ها", "book"));
        sliderdata.add(new ImageCategory(R.drawable.digitalproduct, "انواع کالا های دیجیتال", "digital"));
        sliderdata.add(new ImageCategory(R.drawable.homeproducts, "وسایل خانگی", "home"));


    }


    public void listsymbolclick(View view) {

        if (listorgrid == false) {

            rotate_list *= -1;
            listsymbol.animate().alpha(1f).setDuration(1000);
            gridsymbol.animate().alpha(0.2f).setDuration(1000);
            listsymbol.animate().rotation(rotate_list * 360f).setDuration(1000);
            gridView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listorgrid = true;
            normaladaptor = new Adaptor(MainActivity.this, list, listorgrid);
            listView.setAdapter(normaladaptor);
            setListViewHeightBasedOnChildren(listView);
        }
    }

    public void gridsymbolclick(View view) {

        if (listorgrid == true) {

            rotate_grid *= -1;
            gridsymbol.animate().alpha(1f).setDuration(1000);
            listsymbol.animate().alpha(0.2f).setDuration(1000);
            gridsymbol.animate().rotationX(rotate_grid * 360).setDuration(1000);
            listView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            listorgrid = false;
            normaladaptor = new Adaptor(MainActivity.this, list, listorgrid);
            gridView.setAdapter(normaladaptor);
            setListViewHeightBasedOnChildrengrid(gridView);
        }

    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public static void setListViewHeightBasedOnChildrengrid(GridView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();


        int count = listAdapter.getCount();
        if (count == 0) {
            count = 1;
        }
        int height = listView.getHeight() / count;

        params.height = (totalHeight + height);

        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public void clothesonclick(View view) {

        Intent intent = new Intent(MainActivity.this, Category.class);
        intent.putExtra("category", "cloth");
        startActivity(intent);

    }

    public void digitalproductonclick(View view) {


        Intent intent = new Intent(MainActivity.this, Category.class);
        intent.putExtra("category", "digital");
        startActivity(intent);

    }

    public void homeproductonclick(View view) {


        Intent intent = new Intent(MainActivity.this, Category.class);
        intent.putExtra("category", "home");
        startActivity(intent);


    }


    @Override
    public void onBackPressed() {

        if (main_state == 0) {


            main_exite += 1;
            if (main_exite == 1) {
                Toast.makeText(MainActivity.this, "برای خروج دوباره کلیک کنید", Toast.LENGTH_LONG).show();

                ////////////////////
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        main_exite = 0;


                    }
                }.start();
                //////////////////
            } else if (main_exite >= 2) {

                finish();
            }

        } else if (main_state == 1) {

            back_search.setVisibility(View.GONE);
            et_search.setVisibility(View.INVISIBLE);
            sliderView.setVisibility(View.VISIBLE);
            containerlayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            card_cloth.setVisibility(View.VISIBLE);
            card_digital.setVisibility(View.VISIBLE);
            card_home.setVisibility(View.VISIBLE);
            tv_list_name.setVisibility(View.VISIBLE);

            main_state = 0;

            list = productsDbHelper.select();
            normaladaptor = new Adaptor(MainActivity.this, list, listorgrid);
            gridView.setAdapter(normaladaptor);
            setListViewHeightBasedOnChildrengrid(gridView);
            listView.setAdapter(normaladaptor);
            setListViewHeightBasedOnChildren(listView);

        }
    }
}

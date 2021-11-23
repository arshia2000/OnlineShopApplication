package com.example.digicala.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.adaptor.DownloadListAdapter;
import com.example.digicala.broadcast.InternetBroadCast;
import com.example.digicala.entity.BookProduct;
import com.example.digicala.sharedpreferences.SharePrefernce;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Connection;

public class DownloadActivity extends InternetBroadCast {


    ListView downloadlist;
    DownloadListAdapter downloadListAdapter;
    ArrayList<BookProduct> downloaddata;
    SharePrefernce sharePrefernce;

    ProgressDialog pd;
    private String dispath;
    private int download_repete;

    private URL url;
    private HttpURLConnection connection;

    private InputStream input;
    private OutputStream output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        init();


        downloadlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BookProduct product = (BookProduct) parent.getItemAtPosition(position);
                File file = new File("/sdcard/DigiCala/");
                if (!file.exists()) {
                    file.mkdir();
                }
                new download().execute(product);


            }
        });


    }

    private void init() {

        downloadlist = findViewById(R.id.lv_download);
        downloaddata = new ArrayList<BookProduct>();
        sharePrefernce = new SharePrefernce(DownloadActivity.this);
        boolean loginstate = sharePrefernce.getloginstate();
        if (loginstate == false) {
            Intent intent = new Intent(DownloadActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
        checkpermission();
        setdata();

        downloadlist.setAdapter(downloadListAdapter);

    }

    private void setdata() {
        downloaddata.add(new BookProduct("زن وسواسی", "عزیز نسین", R.drawable.vasvasi, "http://dl.parsbook.com/server3/uploads/zane-vasvasi.pdf"));
        downloaddata.add(new BookProduct("قانون دوست داشتن", "وحید فخرایی", R.drawable.ghanoon, "http://dl.parsbook.com/server3/uploads/ghanoone-doost-dahstan.pdf"));
        downloaddata.add(new BookProduct("20 تغییر بزرگ تکنولوژی", "ارش پور ابراهیمی", R.drawable.taghyre, "http://dl.parsbook.com/server3/uploads/20-taghyir.pdf"));
        downloaddata.add(new BookProduct("انفجار افکار طراحی داخلی", "سجاد شهبازی", R.drawable.enfejar, "http://dl.parsbook.com/server3/uploads/tarahi-dakheli.pdf"));
        downloaddata.add(new BookProduct("مدیریت زمان و راهنمای برنامه ریزی", "محمد روستا", R.drawable.zaman, "http://dl.parsbook.com/server3/uploads/modiriyate-zaman-barnamerizi.pdf"));


        downloadListAdapter = new DownloadListAdapter(DownloadActivity.this, downloaddata);
    }

    private void checkpermission() {

        if (ActivityCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }


    public class download extends AsyncTask<BookProduct, Integer, String> {


        public download() {

            pd = new ProgressDialog(DownloadActivity.this);
            pd.setMax(100);
            pd.setProgress(0);
            pd.setCancelable(false);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pd.setProgress(0);
            pd.show();


        }


        @Override
        protected String doInBackground(BookProduct... params) {


            try {


                dispath = "/sdcard/DigiCala/" + params[0].getTitle() + String.valueOf(download_repete) + ".pdf";
                download_repete += 1;
                url = new URL(params[0].getUrl());
                connection = (HttpURLConnection) url.openConnection();
                int lenght_file = 0;
                lenght_file = connection.getContentLength();
                input = new BufferedInputStream(connection.getInputStream());
                output = new FileOutputStream(dispath);

                byte[] buffer = new byte[1024];
                int count = -1;
                int total = 0;

                while ((count = input.read(buffer)) != -1) {
                    total = total + count;
                    publishProgress((int) ((total * 100) / lenght_file));
                    output.write(buffer, 0, count);
                }

                output.flush();
                output.close();
                input.close();


            } catch (Exception e) {


            } finally {
                try {
                    output.flush();
                    output.close();
                    input.close();
                    Log.e(DownloadActivity.this.getClass().getSimpleName(), "crash on downloading");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(DownloadActivity.this.getClass().getSimpleName(), "crash on closing files");
                }
            }


            return "saved " + dispath;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            pd.setProgress(values[0]);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            make_notification();
            Toast.makeText(DownloadActivity.this, s, Toast.LENGTH_SHORT).show();


        }
    }


    private void make_notification() {

        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        Notification notification;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("DigiCala");
        builder.setContentText(dispath);
        builder.setSmallIcon(R.drawable.logo);

        Intent intent = new Intent(DownloadActivity.this, MainActivity.class);
        intent.putExtra("download_message", dispath);
        PendingIntent pendingIntent = PendingIntent.getActivity(DownloadActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        String channelid = "download_notification";


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            NotificationChannel notificationChannel = new NotificationChannel(channelid, "notify", NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(channelid);
            manager.createNotificationChannel(notificationChannel);

        }

        notification = builder.build();
        manager.notify(download_repete, notification);

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
        super.onBackPressed();
        finish();
    }
}

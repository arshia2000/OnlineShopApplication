package com.example.digicala.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.broadcast.InternetBroadCast;
import com.example.digicala.sharedpreferences.SharePrefernce;

public class Login extends InternetBroadCast {

    EditText et_username, et_password, et_confrim_password;
    String username, password, confrimpassword;
    TextView tv_title, tv_error;
    ImageView im_see_password;
    Button btn_register;
    boolean pass;
    String error;
    SharePrefernce sharePrefernce;

    Boolean seepassword = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setting();


    }

    private void init() {

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_confrim_password = findViewById(R.id.et_password_confrim);
        tv_title = findViewById(R.id.tv_title);
        tv_error = findViewById(R.id.tv_error);
        im_see_password = findViewById(R.id.im_see_password);
        btn_register = findViewById(R.id.btn_register);
        sharePrefernce = new SharePrefernce(Login.this);

        et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        et_confrim_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        // et_password.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);

    }


    private void setting() {

        sharePrefernce.setfontsize(0);

        boolean farsi = sharePrefernce.getlanguage();
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/B-NAZANIN.TTF");

        et_username.setTypeface(type);
        et_password.setTypeface(type);
        et_confrim_password.setTypeface(type);

        if (farsi == true) {
            et_username.setHint("نام کاربری خود را وارد کنید");
            et_password.setHint("رمز خود را وارد کنید");
            et_confrim_password.setHint("مجددا رمز را وارد کنید");
            tv_title.setText("ایجاد حساب");
            btn_register.setText("ثبت نام");


        } else {

            et_username.setHint("Enter you user name");
            et_password.setHint("Enter password");
            et_confrim_password.setHint("Confrim password");
            tv_title.setText("Register");
            btn_register.setText("Register");

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

    public void imageclick(View view) {

        if (seepassword == true) {
            im_see_password.setImageResource(R.drawable.ic_visibility_off_black_24dp);
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
            et_confrim_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            seepassword = false;
        } else if (seepassword == false) {
            im_see_password.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_TEXT);
            et_confrim_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_TEXT);
            seepassword = true;
        }
    }

    public void registerclick(View view) {

        try {

            username = et_username.getText().toString();
            password = et_password.getText().toString();
            confrimpassword = et_confrim_password.getText().toString();
            pass = true;
            error = "";
            int passcount = password.length();

            if (TextUtils.isEmpty(username)) {

                error += "نام کاربری خود را وارد کنید. " + "\n";
                et_password.setText("");
                et_confrim_password.setText("");
                pass = false;

            }
            if (TextUtils.isEmpty(password)) {

                error += "رمز خود را وارد کنید. " + "\n";
                et_password.setText("");
                et_confrim_password.setText("");
                pass = false;

            }
            if (TextUtils.isEmpty(confrimpassword)) {

                error += "رمز خود مجددا وارد کنید. " + "\n";
                et_password.setText("");
                et_confrim_password.setText("");
                pass = false;

            }
            if (!(TextUtils.isEmpty(password)) && passcount < 5) {
                error += "رمز شما حداقل باید 5 کارکتر باشد. " + "\n";
                et_password.setText("");
                et_confrim_password.setText("");
                pass = false;

            }
            if (!(TextUtils.isEmpty(confrimpassword)) && !(TextUtils.isEmpty(password)) && !(password.equals(confrimpassword))) {
                error += "رمز شما تطابق ندارد. " + "\n";
                et_password.setText("");
                et_confrim_password.setText("");
                pass = false;
            }


        } catch (Exception e) {

            error += "تمام اطلاعات را وارد کنید." + "\n";
            et_password.setText("");
            et_confrim_password.setText("");
            pass = false;

        }

        if (pass == true) {
            tv_error.setText(error);
            sharePrefernce.changeloginstate(pass);
            sharePrefernce.setusername(username);
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            tv_error.setText(error);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

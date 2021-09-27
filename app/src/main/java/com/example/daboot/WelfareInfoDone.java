package com.example.daboot;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class WelfareInfoDone extends AppCompatActivity {

    EditText Welfare_Name, Welfare_Area, Welfare_Phone, Welfare_Qualification;
    Button btn_done;
    CheckBox chk_disorder, chk_old, chk_child, chk_family, chk_medical;
    String field;
    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅

    @Override // @Nullable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_info_done);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide(); // 상단바 제거

        WebSettings mWebSettings; //웹뷰세팅
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setGeolocationEnabled(true);

        /*
            Welfare_Name = (EditText)findViewById(R.id.welfare_name);
            Welfare_Area = (EditText)findViewById(R.id.welfare_area);
            Welfare_Phone = (EditText)findViewById(R.id.welfare_phone);
            Welfare_Qualification = (EditText)findViewById(R.id.welfare_Qualification);
            btn_done = (Button)findViewById(R.id.info_done);
            chk_disorder = (CheckBox)findViewById(R.id.chk_disorder);
            chk_old = (CheckBox)findViewById(R.id.chk_old);
            chk_child = (CheckBox)findViewById(R.id.chk_child);
            chk_family = (CheckBox)findViewById(R.id.chk_family);
            chk_medical = (CheckBox)findViewById(R.id.chk_medical);
         */


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Welfare_Name.getText().toString();
                String area = Welfare_Area.getText().toString();
                String phone = Welfare_Phone.getText().toString();
                String qualification = Welfare_Qualification.getText().toString();

                // 체크된 버튼의 값을 담아 DB로 값넘김
                if(chk_disorder.isChecked()) field = "장애";
                if(chk_old.isChecked()) field = "노인";
                if(chk_child.isChecked()) field = "아동";
                if(chk_family.isChecked()) field = "가족";
                if(chk_medical.isChecked()) field = "의료";

            }
        });


    }
}

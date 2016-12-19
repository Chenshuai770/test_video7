package com.cs.test_video7;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtUrl;
    private TextView mTvUrl;
    private String url;
    private Button mBtn1;
    private Button mBtn2;
    private String NETURL="http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
    private String NETURL2="http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
    private String NETURL3="http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        mEtUrl = (EditText) findViewById(R.id.et_url);
        url = mEtUrl.getText().toString().trim();
        Log.d("TGG", url);
        mEtUrl.setOnClickListener(this);
        mTvUrl = (TextView) findViewById(R.id.tv_url);
        mTvUrl.setOnClickListener(this);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn1:
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("url",NETURL);
                startActivity(intent);
                break;
            case R.id.btn2:
              /*  Intent intent2 = new Intent(MainActivity.this, PlayActivity.class);
                intent2.putExtra("url",NETURL3);
                startActivity(intent2);*/
                Intent intent2 = new Intent(MainActivity.this, PlayActivity.class);
                intent2.putExtra("url",NETURL2);
                startActivity(intent2);
                break;
        }
    }


}

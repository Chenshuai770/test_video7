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
    private Button mBtn;
    private EditText mEtUrl;
    private TextView mTvUrl;
    private String url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        mEtUrl = (EditText) findViewById(R.id.et_url);
        url = mEtUrl.getText().toString().trim();
        Log.d("TGG", url);
        mEtUrl.setOnClickListener(this);
        mTvUrl = (TextView) findViewById(R.id.tv_url);
      /*  mTvUrl.setText(
                "http://mp4.ludashi1.info/O63DNZb.mp4"+"\n"+
                "http://mp4.ludashi1.info/O63DNZb.mp4"+"\n"+
                "http://mp4.ludashi1.info/O63D5Kr.mp4"+"\n"+
                "http://mp4.ludashi1.info/O657DHa.mp4"+"\n"+
                "http://mp4.ludashi1.info/O657HDr.mp4"+"\n"+
                "http://mp4.ludashi1.info/O63EK2p.mp4"+"\n"+
                "http://mp4.ludashi1.info/O68WCOn.mp4"+"\n"+
                "http://mp4.ludashi1.info/O68WRRf.mp4"+"\n"+
                "http://mp4.ludashi1.info/O5ZN06e.mp4"+"\n"+
                "http://mp4.ludashi1.info/O6COE8g.mp4"+"\n"+
                "http://mp4.ludashi1.info/O6COK5k.mp4"+"\n"+
                "http://mp4.ludashi1.info/O5ZMMYy.mp4"+"\n"+
                "http://mp4.ludashi1.info/O5XVG6b.mp4"+"\n"+
                "http://wpwp-1.bbbplayer.com/da-16-10-11-skyla-novea-when-a.mp4"+"\n"+
                "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv");*/
        mTvUrl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Intent intent = new Intent(MainActivity.this,PlayActivity.class);

                startActivity(intent);
                break;
        }
    }


}

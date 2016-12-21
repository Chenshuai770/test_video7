package com.cs.test_video7;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvUrl;
    private String url;
    private Button mBtn1;
    private Button mBtn2;
    private String NETURL1 = "http://183.131.119.51/sohu.vodnew.lxdns.com/sohu/s26h23eab6/171/237/sDpLxtbhX3DyOLPoqOjjH1.mp4?wshc_tag=1&wsiphost=ipdbm";
    private String NETURL2 = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
    private String NETURL3 = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
    private String NETURL4 = "http://183.131.119.51/sohu.vodnew.lxdns.com/sohu/s26h23eab6/171/237/sDpLxtbhX3DyOLPoqOjjH1.mp4?wshc_tag=1&wsiphost=ipdbm";
    private String NETURL5 = "http://183.61.37.175/video.dispatch.tc.qq.com/e017351hvug.m701.mp4?vkey=36D088C2F60694EC7F45DB1915179E8713BC443AF8C0E8D6FBBFC071F0D629D836C0651F8D01E4C85CCE5BAC39CCCD4B98CA8342252A59AD10465ADAB338BA8C787E19D3992AC956461BE5E5CD03FE45EFC49D8E152239868A765A256A9F117358AB7931C3637B61276E016DBDA6383C&ocid=262807468&ocid=347280812&ocid=1367537162";
    private String NETURL6 = "http://183.134.20.46/sohu.vodnew.lxdns.com/sohu/s26h23eab6/182/148/H6H1f6N3RPet70Tc7MMCJD.mp4?wshc_tag=0&wsiphost=ipdbm";
    private String NETURL7 = "http://videohy.tc.qq.com/video.dispatch.tc.qq.com/j0017ajk9cg.m1301.mp4?vkey=955E75FEF6000BDA8B02824EF73B193756C3DB8163203C067C20DBB79046563A8F5403AC4598C6E6ADE8D9163A5CC693DC29A51339768A7F0E615AF271A217DBE4144A77DCA2006DAEA91D15B3E469AB9F243ED6C8FE1A8D98C80F8C1645BDF8F6BDC86600281E236D8D5AB205D3DBD7&ocid=296361900";
   // private String NETURL8 = "http://www.iqiyi.com/v_19rr96agog.html?vfm=f_191_360y&fv=p_09_01";
    private String NETURL9 = "http://202.109.167.170/mp4/2015/dianying/msntgd_7333/E5F48E3CCC14D12927DE64ACCB477062_20150415_1_1_1096.mp4?t=585adf64&pno=1042000&sign=ecfac4a5a57e41c6365d4605bb292c1d&win=300&srgid=253&urgid=1015&srgids=253&nid=1982&payload=usertoken%3dhit%3d1%5eruip%3d2344884984&rdur=21600&arange=0&limitrate=0&fid=E5F48E3CCC14D12927DE64ACCB477062&ver=0x03&uuid=c3597b53d8c14334b7ad38c73f101fab#ct1482321816s";
//HLS- Apple HTTP live streaming - m3u8
/*private String NETURL8 = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
private String NETURL9 = "http://www.modrails.com/videos/passenger_nginx.mov";
private String NETURL10 = "rtsp://xgrammyawardsx.is.livestream-api.com/livestreamiphone/grammyawards";
private String NETURL11 = "mms://112.230.192.196/zb12";*/


    private ListView mListview;
    private MyAdapter myAdapter;
    private List<String> mlist = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTvUrl = (TextView) findViewById(R.id.tv_url);
        mTvUrl.setOnClickListener(this);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mListview = (ListView) findViewById(R.id.listview);

        mlist.add(NETURL1);
        mlist.add(NETURL2);
        mlist.add(NETURL3);
        mlist.add(NETURL4);
        mlist.add(NETURL5);
        mlist.add(NETURL6);
        mlist.add(NETURL7);

        mlist.add(NETURL9);




        myAdapter = new MyAdapter(MainActivity.this, mlist);
        mListview.setAdapter(myAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = mlist.get(i);
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn1:
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("url", NETURL1);
                startActivityForResult(intent, 2);
                break;
            case R.id.btn2:
              /*  Intent intent2 = new Intent(MainActivity.this, PlayActivity.class);
                intent2.putExtra("url",NETURL3);
                startActivity(intent2);*/
                Intent intent2 = new Intent(MainActivity.this, PlayActivity.class);
                intent2.putExtra("url", NETURL2);
                startActivity(intent2);
                break;
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                   Bitmap bitmap= (Bitmap) msg.obj;
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case RESULT_OK:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        byte[] bitmaps = intent.getByteArrayExtra("bitmap");
                        Log.d("TGG",bitmaps.length+"");

                        Bitmap bitmap= BitmapFactory.decodeByteArray(bitmaps,0,bitmaps.length);
                        Message message = new Message();
                        message.what=1;
                        message.obj=bitmap;
                        handler.sendMessage(message);

                    }
                }).start();



                break;
        }
    }
}

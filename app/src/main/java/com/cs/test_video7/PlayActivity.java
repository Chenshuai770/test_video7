package com.cs.test_video7;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by chenshuai on 2016/12/14.
 */

public class PlayActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "PlayActivity";

    private VideoView mVideoView;
    private MediaController mMediaController;
    private MyMediaController myMediaController;

    //  String path1 = Environment.getExternalStorageDirectory() + "/Download/eva.mkv";
    private String path1 = Environment.getExternalStorageDirectory() + "/ABC/123.mp4";


   // private  String NETURL="http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
    private String NETURL;
    private Uri path2;
    private static final int TIME = 0;
    private static final int BATTERY = 1;
    private static final int LOADING=2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    myMediaController.setTime(msg.obj.toString());
                    break;
                case BATTERY:
                    myMediaController.setBattery(msg.obj.toString());
                    break;
                case LOADING:
                    Toast.makeText(PlayActivity.this, "文件已经下在BBB里面", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private TextView mBufferPercent;
    private TextView mNetSpeed;
    private Button mBtnDownload;
    private long duration;
    private int progress;
    private static long currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = PlayActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        toggleHideyBar();
        setContentView(R.layout.activity_paly);
        Intent intent = getIntent();
        NETURL= intent.getStringExtra("url");

        path2= Uri.parse(NETURL);
        initView();
        //缓冲和显示速度
        mBufferPercent = (TextView) findViewById(R.id.buffer_percent);
        mNetSpeed = (TextView) findViewById(R.id.net_speed);

        mVideoView = (VideoView) findViewById(R.id.surface_view);

        //mVideoView.setVideoPath(path1);
        mVideoView.setVideoURI(path2);
        mMediaController = new MediaController(this);
        myMediaController = new MyMediaController(this, mVideoView, this);

        mMediaController.show(5000);
/**
 * VIDEO_LAYOUT_SCALE一条直线
 * VIDEO_LAYOUT_ZOOM压缩播放
 */
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_ZOOM,0.001f);
        mVideoView.setMediaController(myMediaController);
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//高画质
        mVideoView.requestFocus();

        //myMediaController.setSeekBarChange(50);
       // mVideoView.seekTo(50);




        //设置缓冲比
        mVideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                mBufferPercent.setText("已缓冲:" + percent + "%");
            }
        });
        //设置显示速度
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    //开始缓冲
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mBufferPercent.setVisibility(View.VISIBLE);
                        mNetSpeed.setVisibility(View.VISIBLE);
                        mp.pause();
                        break;
                    //缓冲结束
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mBufferPercent.setVisibility(View.GONE);
                        mNetSpeed.setVisibility(View.GONE);
                        mp.start();
                        break;
                    //正在缓冲
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        mNetSpeed.setText("当前网速:" + extra + "kb/s");
                        break;
                }
                return true;
            }
        });
        //画面是否拉伸
      //mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 16/9 );
        registerBoradcastReceiver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //时间读取线程
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String str = sdf.format(new Date());
                    Message msg = new Message();
                    msg.obj = str;
                    msg.what = TIME;
                    mHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = mVideoView.getCurrentPosition();
        Log.d("TGG", "onPause: "+ currentPosition);
         mVideoView.pause();
    }



    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                 long duration = (long) (mVideoView.getDuration()/3);
                Log.d("TGG", "onPrepared: "+currentPosition);
                mp.seekTo(currentPosition);
            }
        });
        mVideoView.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(batteryBroadcastReceiver);
        } catch (IllegalArgumentException ex) {

        }
    }

    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                int scale = intent.getIntExtra("scale", 100);
                //把它转成百分比
                //tv.setText("电池电量为"+((level*100)/scale)+"%");
                Message msg = new Message();
                msg.obj = (level * 100) / scale + "";
                msg.what = BATTERY;
                mHandler.sendMessage(msg);
            }
        }
    };

    public void registerBoradcastReceiver() {
        //注册电量广播监听
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, intentFilter);

    }

    public void toggleHideyBar() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (get_current_ui_flags)
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }


    private void initView() {
        mBtnDownload = (Button) findViewById(R.id.btn_download);

        mBtnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "BBB");
                            if (!file.exists()){
                                file.mkdirs();
                            }
                            long time = System.nanoTime();
                            File netfile=new File(file.getAbsoluteFile()+ File.separator+"test"+time+".flv");
                            URL url = new URL(NETURL);
                            HttpURLConnection coon = (HttpURLConnection) url.openConnection();
                            coon.setRequestMethod("GET");
                            coon.setConnectTimeout(5*1000);
                            coon.connect();
                            InputStream inputStream = coon.getInputStream();
                            byte[] buffer = new byte[1024];
                            FileOutputStream fileOutputStream = new FileOutputStream(netfile);
                            int len;
                            while ((len=inputStream.read())!=-1){
                                fileOutputStream.write(buffer,0,len);
                            }
                            fileOutputStream.close();
                            inputStream.close();
                            Message message = new Message();
                            message.what=LOADING;
                            mHandler.sendMessage(message);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                mBtnDownload.setVisibility(View.GONE);

                break;
        }
    }
}

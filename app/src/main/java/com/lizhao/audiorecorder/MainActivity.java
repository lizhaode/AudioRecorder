package com.lizhao.audiorecorder;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = "lizhao";
    private MediaRecorder mRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button stopbutton = (Button) findViewById(R.id.stopRecord);
        stopbutton.setEnabled(false);
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Log.v(LOG_TAG, "/" + year + month + day + hour + minute + ".aac");
        return "/" + year + month + day + hour + minute + ".aac";
    }

    public String AudioPath(){
        String existspath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
            if (file.exists()){
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
                existspath = path + getTime();
            }else {
                file.mkdirs();
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
                existspath = path + getTime();
            }
        }
        Log.v(LOG_TAG,existspath);
        return existspath;
    }

    public void startRecord(View view){
        Button startbutton = (Button) findViewById(R.id.startRecord);
        Button stopbutton = (Button) findViewById(R.id.stopRecord);

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mRecorder.setOutputFile(AudioPath());

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "录音准备失败!" );
        }
        mRecorder.start();
        startbutton.setText("正在录音......");
        startbutton.setEnabled(false);
        stopbutton.setEnabled(true);


    }

    public void stopRecord(View view){
        Button startbutton = (Button) findViewById(R.id.startRecord);
        Button stopbutton = (Button) findViewById(R.id.stopRecord);

        mRecorder.stop();
        mRecorder.release();

        startbutton.setEnabled(true);
        startbutton.setText("开始录音");
        assert stopbutton != null;
        stopbutton.setEnabled(false);
    }
}

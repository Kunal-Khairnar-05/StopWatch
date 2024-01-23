package com.sinixcode.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    MaterialButton Start, Stop, Reset;
    int miliSecond, seconds, minutes;
    long miliseconds,startTime, timeBuff, updateTime=0L;
    Handler handler;

    private final Runnable runnable = new Runnable(){
        @Override
        public void run() {
            miliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeBuff + miliseconds;
            seconds = (int)updateTime/1000;
            minutes = seconds/60;
            seconds = seconds%60;
            miliSecond = (int) updateTime %1000;

            textView.setText(MessageFormat.format("{0}:{1}:{2}",minutes,String.format(Locale.getDefault(),"%02d",seconds),String.format(Locale.getDefault(),"%02d",miliSecond)));
            handler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.curr_time);
        Start = findViewById(R.id.start);
        Stop = findViewById(R.id.pause);
        Reset = findViewById(R.id.Reset);

        handler = new Handler(Looper.getMainLooper());

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                Reset.setEnabled(false);
                Stop.setEnabled(true);
                Start.setEnabled(false);
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeBuff += miliSecond;
                handler.removeCallbacks(runnable);
                Reset.setEnabled(true);
                Start.setEnabled(true);
                Stop.setEnabled(false);
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miliSecond =0;
                startTime=0;
                updateTime=0;
                timeBuff=0;
                seconds=0;
                minutes=0;
                miliseconds=0;
                textView.setText("00:00:00");
            }
        });

        textView.setText("00:00:00");
    }


}
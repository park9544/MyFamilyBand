package com.usepjh92.user.myfamilyband.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.usepjh92.user.myfamilyband.R;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        timer.schedule(task , 1000);


    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            Intent intent = new Intent(IntroActivity.this , MainActivity.class);
            startActivity(intent);

            finish();
        }
    };

}

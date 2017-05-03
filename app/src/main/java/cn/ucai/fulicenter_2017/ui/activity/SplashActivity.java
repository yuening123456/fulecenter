package cn.ucai.fulicenter_2017.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.ucai.fulicenter_2017.R;

public class SplashActivity extends AppCompatActivity {
    final static int time=5000;
    TextView  tvSkip;
    MyCountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvSkip= (TextView) findViewById(R.id.skip);
        countDownTimer=new MyCountDownTimer(time,1000);
        countDownTimer.start();
        setListener();

    }

    private void setListener() {
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                countDownTimer.onFinish();
            }
        });
    }

    class MyCountDownTimer extends CountDownTimer {


        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvSkip.setText(getString(R.string.skip)+""+millisUntilFinished/1000+"s");
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();

        }
    }
}

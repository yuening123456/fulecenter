package cn.ucai.fulicenter_2017.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter_2017.R;

public class SplashActivity extends AppCompatActivity {
    final static int time = 5000;
    MyCountDownTimer countDownTimer;
    @BindView(R.id.skip)
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        countDownTimer = new MyCountDownTimer(time, 1000);
        countDownTimer.start();

    }
    @OnClick(R.id.skip) void skip(){
        countDownTimer.cancel();
        countDownTimer.onFinish();
    }

    class MyCountDownTimer extends CountDownTimer {


        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            skip.setText(getString(R.string.skip) + "" + millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();

        }
    }
}

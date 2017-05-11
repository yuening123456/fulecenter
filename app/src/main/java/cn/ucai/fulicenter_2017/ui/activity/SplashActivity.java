package cn.ucai.fulicenter_2017.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.local.UserDao;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.SharePrefrenceUtils;

public class SplashActivity extends AppCompatActivity {
    final static int time = 5000;
    MyCountDownTimer countDownTimer;
    @BindView(R.id.skip)
    TextView skip;
    Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder=ButterKnife.bind(this);
        countDownTimer = new MyCountDownTimer(time, 1000);
        countDownTimer.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(FuLiCenterApplication.getInstance().getCurrentUser()==null){
                    String userName= SharePrefrenceUtils.getInstance().getUserName();
                    L.e("main","userName="+userName);
                    if(userName!=null){
                        UserDao dao=new UserDao(SplashActivity.this);
                        User user=dao.getUser(userName);
                        if(user!=null){
                            FuLiCenterApplication.getInstance().setCurrentUser(user);
                        }
                    }

                }
            }
        }).start();

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
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

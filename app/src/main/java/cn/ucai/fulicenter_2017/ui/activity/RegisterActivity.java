package cn.ucai.fulicenter_2017.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter_2017.R;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.layout_back_ground, R.id.Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_back_ground:
                finish();
                break;
            case R.id.Register:
                break;
        }
    }
}

package cn.ucai.fulicenter_2017.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.utils.ImageLoader;
import cn.ucai.fulicenter_2017.data.utils.SharePrefrenceUtils;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.imUserAvatar)
    ImageView imUserAvatar;
    @BindView(R.id.tvNick)
    TextView tvNick;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.out)
    Button out;
    @BindView(R.id.layout_tvNick)
    LinearLayout layoutTvNick;
    @BindView(R.id.layout_back_ground)
    ImageView layoutBackGround;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        User user = FuLiCenterApplication.getInstance().getCurrentUser();
        if (user != null) {
            tvNick.setText(user.getMuserNick());
            tvUserName.setText(user.getMuserName());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), SettingActivity.this, imUserAvatar);
        }
    }

    @OnClick({R.id.layout_back_ground, R.id.out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back_ground:
                finish();
                break;
            case R.id.out:
                logout();
                break;
        }
    }

    private void logout() {
        FuLiCenterApplication.getInstance().setCurrentUser(null);
        SharePrefrenceUtils.getInstance().removeUser();
        finish();
        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
    }


    @OnClick({R.id.layout_back_ground, R.id.layout_tvNick})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_back_ground:
                finish();
                break;
            case R.id.layout_tvNick:
                startActivityForResult(new Intent(SettingActivity.this,UpdataNickActivity.class),I.REQUEST_CODE_NICK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==I.REQUEST_CODE_NICK&&resultCode==RESULT_OK){
            initData();
        }
    }
}

package cn.ucai.fulicenter_2017.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.Login)
    Button Login;
    @BindView(R.id.Register)
    Button Register;
    @BindView(R.id.layout_back_ground)
    ImageView layoutBackGround;
    @BindView(R.id.edUserName)
    EditText edUserName;
    @BindView(R.id.edPassword)
    EditText edPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.Login, R.id.Register,R.id.layout_back_ground})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_back_ground:
                finish();
            case R.id.Login:
                break;
            case R.id.Register:
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class),0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            String username = data.getStringExtra(I.User.USER_NAME);
            Log.i("main", "username:" + username);
            edUserName.setText(username);

        }
    }
}

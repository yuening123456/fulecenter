package cn.ucai.fulicenter_2017.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.Result;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.net.IUserModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.net.UserModel;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.MD5;
import cn.ucai.fulicenter_2017.data.utils.ResultUtils;
import cn.ucai.fulicenter_2017.data.utils.SharePrefrenceUtils;

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
    ProgressDialog pd;
    IUserModel model;
    String userName,password;

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
                login();
                break;
            case R.id.Register:
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class),0);
                break;
        }
    }

    private void login() {
        initDialog();
        if (checkInput()) {
            model = new UserModel();
            model.login(LoginActivity.this, userName, MD5.getMessageDigest(password), new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    if (s != null) {
                        Result<User> result = ResultUtils.getResultFromJson(s, User.class);
                        if (result != null) {
                            if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                               setUserNameMass(R.string.login_fail_unknow_user);
                            } else if (result.getRetCode() ==I.MSG_LOGIN_ERROR_PASSWORD) {
                              setUserNameMass(R.string.login_fail_error_password);
                            } else {
                                User user = result.getRetData();
                                loginSuccess(user);
                            }
                        }
                    }
                    disMissDialog();
                }

                @Override
                public void onError(String error) {
                    disMissDialog();
                }
            });
        } else {
            disMissDialog();
        }

    }

    private void setUserNameMass(int id) {
        edUserName.requestFocus();
        edUserName.setError(getString(id));
    }

    private void disMissDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
    private void loginSuccess(User user) {
        L.i("main","loginSuccess"+user.toString());
        FuLiCenterApplication.getInstance().setCurrentUser(user.toString());
        SharePrefrenceUtils.getInstance().setUserName(user.getMuserName());
        finish();
    }
    private boolean checkInput() {
        userName = edUserName.getText().toString().trim();
        password = edPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            setUserNameMass(R.string.user_name_connot_be_empty);
            return false;
        }
        if (!userName.matches("[a-zA-Z]\\w{5,15}")) {
            setUserNameMass(R.string.illegal_user_name);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            edPassword.requestFocus();
            edPassword.setError(getString(R.string.password_connot_be_empty));
            return false;
        }
        return true;

    }

    private void initDialog() {
        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage(getString(R.string.logining));
        pd.show();
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

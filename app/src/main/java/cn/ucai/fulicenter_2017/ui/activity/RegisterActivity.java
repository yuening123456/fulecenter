package cn.ucai.fulicenter_2017.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.Result;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.net.IUserModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.net.UserModel;
import cn.ucai.fulicenter_2017.data.utils.ResultUtils;


public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.edUserName)
    EditText edUserName;
    @BindView(R.id.edNick)
    EditText edNick;
    @BindView(R.id.edPassword)
    EditText edPassword;
    @BindView(R.id.Register)
    Button Register;
    IUserModel model;
    String userName, userNick, password;
    ProgressDialog pd;
    @BindView(R.id.cyPassword)
    EditText cyPassword;


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
                register();
                break;
        }
    }

    private void register() {
        initDialog();
        if (checkInput()) {
            model = new UserModel();
            model.register(RegisterActivity.this, userName, userNick, password, new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    if (s != null) {
                        Result result = ResultUtils.getResultFromJson(s, User.class);
                        if (result != null) {
                            if (result.getRetCode() == I.MSG_GROUP_HXID_EXISTS) {
                                edUserName.requestFocus();
                                edUserName.setError(getString(R.string.register_fail_exists));
                            } else if (result.getRetCode() == I.MSG_CONNECTION_FAIL) {
                                edUserName.requestFocus();
                                edUserName.setError(getString(R.string.register_fail));
                            } else {
                                registerSuccess();
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

    private void initDialog() {
        pd = new ProgressDialog(RegisterActivity.this);
        pd.setMessage(getString(R.string.registering));
        pd.show();
    }

    private void disMissDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    private void registerSuccess() {
        setResult(RESULT_OK, new Intent().putExtra(I.User.USER_NAME, userName));
        finish();
    }

    private boolean checkInput() {
        userName = edUserName.getText().toString().trim();
        userNick = edNick.getText().toString().trim();
        password = edPassword.getText().toString().trim();
        String cpwd = edPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            edUserName.requestFocus();
            edUserName.setError(getString(R.string.user_name_connot_be_empty));
            return false;
        }
        if (!userName.matches("[a-zA-Z]\\w{5,15}")) {
            edUserName.requestFocus();
            edUserName.setError(getString(R.string.illegal_user_name));
            return false;
        }
        if (TextUtils.isEmpty(userNick)) {
            edNick.requestFocus();
            edNick.setError(getString(R.string.nick_name_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            edPassword.requestFocus();
            edPassword.setError(getString(R.string.password_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(cpwd)) {
            cyPassword.requestFocus();
            cyPassword.setError(getString(R.string.confirm_password_connot_be_empty));
            return false;
        }
        if (!password.equals(cpwd)) {
            cyPassword.requestFocus();
            cyPassword.setError(getString(R.string.two_input_password));
            return false;
        }
        return true;

    }


}

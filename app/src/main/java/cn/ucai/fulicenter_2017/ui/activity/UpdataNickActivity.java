package cn.ucai.fulicenter_2017.ui.activity;

import android.app.ProgressDialog;
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
import cn.ucai.fulicenter_2017.data.local.UserDao;
import cn.ucai.fulicenter_2017.data.net.IUserModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.net.UserModel;
import cn.ucai.fulicenter_2017.data.utils.CommonUtils;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.ResultUtils;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class UpdataNickActivity extends AppCompatActivity {
    @BindView(R.id.layout_back_ground)
    ImageView layoutBackGround;
    @BindView(R.id.tv_NickName)
    EditText tvNickName;
    @BindView(R.id.btn_success)
    Button btnSuccess;
    IUserModel modle;
    User user;
    ProgressDialog pd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_nick_activity);
        ButterKnife.bind(this);
        modle=new UserModel();
        initData();
    }

    private void initData() {
       user= FuLiCenterApplication.getInstance().getCurrentUser();
        if(user!=null){
            tvNickName.setText(user.getMuserNick());
            tvNickName.selectAll();
        }else{
            finish();
        }
    }
    private void initDialog(){
        pd=new ProgressDialog(UpdataNickActivity.this);
        pd.setMessage(getString(R.string.update_user_nick));
        pd.show();
    }
    private void dismissDialog(){
        if(pd!=null&&pd.isShowing()){
            pd.dismiss();
        }
    }

    @OnClick({R.id.layout_back_ground, R.id.btn_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_back_ground:
                finish();
                break;
            case R.id.btn_success:
                String newNick=tvNickName.getText().toString().trim();
                Log.i("main", "UpdataNickActivity.user:" + user + ",newNick:" + newNick);
                if (checkInput()) {
                    modle.updateNick(UpdataNickActivity.this, user.getMuserName(), newNick, new OnCompleteListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            initDialog();
                            if (s != null) {
                                Result<User> result = ResultUtils.getResultFromJson(s, User.class);
                                if (result != null) {
                                    if (result.getRetCode() == I.MSG_USER_SAME_NICK) {
                                        CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                                    } else if (result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                        CommonUtils.showLongToast(R.string.update_fail);
                                    } else {
                                        updateSuccess(result.getRetData());
                                    }
                                }
                            }
                            dismissDialog();
                        }
                        @Override
                        public void onError(String error) {
                            dismissDialog();
                        }
                    });
                }
                break;
        }
    }

    private boolean checkInput() {
        String newNick=tvNickName.getText().toString().trim();
        L.e("main",newNick+"newNick");
        if(TextUtils.isEmpty(newNick)){
            CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
            return false;
        }if(newNick.equals(user.getMuserNick())){
            CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
            return false;
        }
        return true;
    }

    private void updateSuccess(User user) {
        CommonUtils.showLongToast(R.string.update_user_nick_success);
        UserDao dao=new UserDao(UpdataNickActivity.this);
        L.e("main","user+sdfss="+user.toString());
        dao.saveUser(user);
        FuLiCenterApplication.getInstance().setCurrentUser(user);
        setResult(RESULT_OK);
        finish();
    }

}

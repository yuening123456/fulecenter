package cn.ucai.fulicenter_2017.application;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;
import cn.ucai.fulicenter_2017.data.bean.User;


/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class FuLiCenterApplication extends Application{
    private static FuLiCenterApplication instace;
    User CurrentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        instace=this;
        ShareSDK.initSDK(this);
    }

    public static FuLiCenterApplication getInstance(){
        return  instace;
    }

    public User getCurrentUser() {
        return CurrentUser;
    }

    public void setCurrentUser(User currentUser) {
        CurrentUser = currentUser;
    }
}

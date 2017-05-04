package cn.ucai.fulicenter_2017.application;

import android.app.Application;


/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class FuLiCenterApplication extends Application{
    private static FuLiCenterApplication instace;

    @Override
    public void onCreate() {
        super.onCreate();
        instace=this;
    }

    public static FuLiCenterApplication getInstance(){
        return  instace;
    }
}

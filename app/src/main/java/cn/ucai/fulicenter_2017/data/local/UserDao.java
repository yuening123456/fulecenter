package cn.ucai.fulicenter_2017.data.local;

import android.content.Context;

import cn.ucai.fulicenter_2017.data.bean.User;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class UserDao {
    public  UserDao(Context context){
        DBManager.getInstance().initDB(context);
    }
    public User getUser(String username){
        return  DBManager.getInstance().getUser(username);
    }
    public boolean saveUser(User user){
        return  DBManager.getInstance().saveUser(user);
    }
}

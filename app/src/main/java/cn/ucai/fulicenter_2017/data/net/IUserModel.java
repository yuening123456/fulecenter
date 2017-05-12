package cn.ucai.fulicenter_2017.data.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter_2017.data.bean.MessageBean;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public interface IUserModel {
    void login(Context context,String username,String password ,OnCompleteListener<String>listener );
    void register(Context context,String username,String nick,String password ,OnCompleteListener<String>listener );
    void updateNick(Context context, String username, String nickname, OkHttpUtils.OnCompleteListener<String> listener);
    void uploadAvatar(Context context,String username,String avatarType,File file, OnCompleteListener<String>listener);
    void loadCollectsCount(Context context, String username, OnCompleteListener<MessageBean>listener);
    void addCollect(Context context, String goodsId, String username, OnCompleteListener<MessageBean>listener);
    void removeCollect(Context context, String goodsId, String username,OnCompleteListener<MessageBean>listener);
    void isCollect(Context context, String goodsId, String username,OnCompleteListener<MessageBean>listener);
}

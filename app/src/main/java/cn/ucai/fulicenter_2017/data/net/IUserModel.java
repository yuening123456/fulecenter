package cn.ucai.fulicenter_2017.data.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.CartBean;
import cn.ucai.fulicenter_2017.data.bean.CollectBean;
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
    void loadCollects(Context context, String username, int pageId, int pageSize, OnCompleteListener<CollectBean[]>listener);
    void addCart(Context context,int goodsId,String username,int count,boolean isChecked,OnCompleteListener<MessageBean>listener);
    void removeCart(Context context,int cartId,OnCompleteListener<MessageBean>listener);
    void updateCart(Context context,int cartId,int count,boolean isChecked,OnCompleteListener<MessageBean>listener);
    void loadCart(Context context, String username, OnCompleteListener<CartBean[]> listener);
}

package cn.ucai.fulicenter_2017.data.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.CollectBean;
import cn.ucai.fulicenter_2017.data.bean.MessageBean;
import cn.ucai.fulicenter_2017.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class UserModel implements IUserModel{
    @Override
    public void login(Context context, String username, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.PASSWORD,password)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void register(Context context, String username, String nick, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .addParam(I.User.PASSWORD,password)
                .post()
                .targetClass(String.class)
                .execute(listener);

    }

    @Override
    public void updateNick(Context context, String username, String nick, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .targetClass(String.class)
                .execute(listener);

    }

    @Override
    public void uploadAvatar(Context context, String username, String avatarType, File file, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,username)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);

    }

    @Override
    public void loadCollectsCount(Context context, String username,OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean>utils =new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void addCollect(Context context, String goodsId, String username, OnCompleteListener<MessageBean> listener) {
        collectAction(I.ACTION_ADD_COLLECT,context,goodsId,username,listener);

    }

    private void collectAction(int action, Context context, String goodsId, String username, OnCompleteListener<MessageBean> listener) {
        String url=I.REQUEST_IS_COLLECT;
        if(action==I.ACTION_ADD_COLLECT){
            url=I.REQUEST_ADD_COLLECT;
        }else if(action==I.ACTION_DELETE_COLLECT){
            url=I.REQUEST_DELETE_COLLECT;
        }
        OkHttpUtils<MessageBean> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(url)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,goodsId)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void removeCollect(Context context, String goodsId, String username, OnCompleteListener<MessageBean> listener) {
        collectAction(I.ACTION_DELETE_COLLECT,context,goodsId,username,listener);
    }

    @Override
    public void isCollect(Context context, String goodsId, String username, OnCompleteListener<MessageBean> listener) {
        collectAction(I.ACTION_IS_COLLECT,context,goodsId,username,listener);
    }

    @Override
    public void loadCollects(Context context, String username, int pageId, int pageSize, OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils =new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(pageSize))
                .targetClass(CollectBean[].class)
                .execute(listener);

    }
}

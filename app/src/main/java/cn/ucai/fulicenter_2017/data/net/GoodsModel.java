package cn.ucai.fulicenter_2017.data.net;

import android.content.Context;

import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.NewGoodsBean;
import cn.ucai.fulicenter_2017.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class GoodsModel implements IGoodsModel {
    @Override
    public void loadNewGoodsData(Context context, int catId, int pageId, int pageSize, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(I.CAT_ID))
               .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(pageSize))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}

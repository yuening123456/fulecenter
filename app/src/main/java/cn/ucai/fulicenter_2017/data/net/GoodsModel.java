package cn.ucai.fulicenter_2017.data.net;

import android.content.Context;

import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.BoutiqueBean;
import cn.ucai.fulicenter_2017.data.bean.CategoryChildBean;
import cn.ucai.fulicenter_2017.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter_2017.data.bean.ColorBean;
import cn.ucai.fulicenter_2017.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter_2017.data.bean.NewGoodsBean;
import cn.ucai.fulicenter_2017.data.bean.PropertiesBean;
import cn.ucai.fulicenter_2017.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class GoodsModel implements IGoodsModel {
    @Override
    public void loadNewGoodsData(Context context, int catId, int pageId, int pageSize,
                                 OnCompleteListener<NewGoodsBean[]> listener) {
        String requestUrl=I.REQUEST_FIND_NEW_BOUTIQUE_GOODS;
        if(catId>0){
            requestUrl=I.REQUEST_FIND_GOODS_DETAILS;
        }
        OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(requestUrl)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
               .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(pageSize))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    @Override
    public void loadBoutiqueData(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);

    }

    @Override
    public void loadGoodDetails(Context context, int good_id, OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,String.valueOf(good_id))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    @Override
    public void loadCategoryGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils =new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    @Override
    public void loadCategoryChild(Context context, int parentId, OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(parentId))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);




    }


}

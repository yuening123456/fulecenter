package cn.ucai.fulicenter_2017.data.net;

import android.content.Context;

import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.BoutiqueBean;
import cn.ucai.fulicenter_2017.data.bean.CategoryChildBean;
import cn.ucai.fulicenter_2017.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter_2017.data.bean.ColorBean;
import cn.ucai.fulicenter_2017.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter_2017.data.bean.NewGoodsBean;
import cn.ucai.fulicenter_2017.data.utils.OkHttpUtils;


/**
 * Created by Administrator on 2017/5/4 0004.
 */

public interface IGoodsModel {
    void loadNewGoodsData(Context context, int catId, int pageId, int pageSize,
                       OnCompleteListener<NewGoodsBean[]> listener);
    void loadBoutiqueData(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener);
    void loadGoodDetails(Context context, int good_id, OnCompleteListener<GoodsDetailsBean> listener);
    void loadCategoryGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>listener);
    void loadCategoryChild(Context context, int parentId, OkHttpUtils.OnCompleteListener<CategoryChildBean[]>listener);


}

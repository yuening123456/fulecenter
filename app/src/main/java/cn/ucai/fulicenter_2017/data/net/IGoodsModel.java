package cn.ucai.fulicenter_2017.data.net;

import android.content.Context;

import cn.ucai.fulicenter_2017.data.bean.NewGoodsBean;
import cn.ucai.fulicenter_2017.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public interface IGoodsModel {
    void loadNewGoodsData(Context context, int catId, int pageId, int pageSize,
                          OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener);
}

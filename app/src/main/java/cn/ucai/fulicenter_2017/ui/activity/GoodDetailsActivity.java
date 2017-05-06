package cn.ucai.fulicenter_2017.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.AlbumsBean;
import cn.ucai.fulicenter_2017.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter_2017.data.bean.PropertiesBean;
import cn.ucai.fulicenter_2017.data.net.GoodsModel;
import cn.ucai.fulicenter_2017.data.net.IGoodsModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.ui.view.AutoSlideLoopView;
import cn.ucai.fulicenter_2017.ui.view.FlowIndicator;

public class GoodDetailsActivity extends AppCompatActivity {

    @BindView(R.id.backClickArea)
    ImageView backClickArea;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.tv_goodsName)
    TextView tvGoodsName;
    @BindView(R.id.tv_goodsEnglishName)
    TextView tvGoodsEnglishName;
    @BindView(R.id.tv_shopPrice)
    TextView tvShopPrice;
    @BindView(R.id.aslv)
    AutoSlideLoopView aslv;
    @BindView(R.id.flowIndicator)
    FlowIndicator flowIndicator;
    @BindView(R.id.goodsBrief)
    TextView goodsBrief;
    Unbinder bind;
    IGoodsModel model;
    ArrayList<String> mGoodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);
        bind = ButterKnife.bind(this);
        int good_id=getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID,I.CAT_ID);
        model=new GoodsModel();

        model.loadGoodDetails(this, good_id, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
              /* setView(result);*/
            }
            @Override
            public void onError(String error) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bind!=null){
            bind.unbind();
        }
    }
}

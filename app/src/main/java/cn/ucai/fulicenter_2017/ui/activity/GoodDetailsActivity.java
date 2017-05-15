package cn.ucai.fulicenter_2017.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.AlbumsBean;
import cn.ucai.fulicenter_2017.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter_2017.data.bean.MessageBean;
import cn.ucai.fulicenter_2017.data.bean.PropertiesBean;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.net.GoodsModel;
import cn.ucai.fulicenter_2017.data.net.IGoodsModel;
import cn.ucai.fulicenter_2017.data.net.IUserModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.net.UserModel;
import cn.ucai.fulicenter_2017.data.utils.CommonUtils;
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
    @BindView(R.id.tv_CurrencyPrice)
    TextView tvCurrencyPrice;
    @BindView(R.id.aslv)
    AutoSlideLoopView aslv;
    @BindView(R.id.flowIndicator)
    FlowIndicator flowIndicator;
    @BindView(R.id.goodsBrief)
    WebView goodsBrief;
    Unbinder bind;
    IGoodsModel model;
    IUserModel userModel;
    ArrayList<String> mGoodList;
    int good_id;
    @BindView(R.id.layout_back_ground)
    RelativeLayout layoutBackGround;
    User user;
    boolean isCollect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);
        bind = ButterKnife.bind(this);
        good_id = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, I.CAT_ID);
        initData();
        setListener();
    }

    private void setListener() {
        //设置图片的触摸事件
        aslv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }


    private void initData() {
        if (good_id == 0) {
            finish();
        } else {
            model = new GoodsModel();
            userModel = new UserModel();
            loadData();
        }
    }

    private void loadData() {
        user = FuLiCenterApplication.getInstance().getCurrentUser();
        model.loadGoodDetails(this, good_id, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                showView(result);
            }

            @Override
            public void onError(String error) {
                L.e("main", "error" + error);
            }
        });
        loadCollectStatus();
    }

    private void loadCollectStatus() {
        user = FuLiCenterApplication.getInstance().getCurrentUser();
        if (user != null) {
            userModel.isCollect(GoodDetailsActivity.this, String.valueOf(good_id), user.getMuserName(), new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    isCollect = result != null && result.isSuccess() ? true : false;
                    updateUI();
                }

                @Override
                public void onError(String error) {
                    isCollect = false;
                    updateUI();
                }
            });
        }
    }

    private void updateUI() {
        ivCollect.setImageResource(isCollect ? R.mipmap.bg_collect_out : R.mipmap.bg_collect_in);
    }

    private void showView(GoodsDetailsBean result) {
        tvCurrencyPrice.setText(result.getCurrencyPrice());
        tvGoodsName.setText(result.getGoodsName());
        tvGoodsEnglishName.setText(result.getGoodsEnglishName());
        goodsBrief.loadDataWithBaseURL(null, result.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
        PropertiesBean[] properties = result.getProperties();
        aslv.startPlay(this, getGoodsList(properties), flowIndicator);

    }

    private ArrayList<String> getGoodsList(PropertiesBean[] properties) {

        mGoodList = new ArrayList<>();
        for (PropertiesBean property : properties) {
            for (AlbumsBean albumsBean : property.getAlbums()) {
                mGoodList.add(albumsBean.getImgUrl());
            }
        }
        return mGoodList;
    }

    @OnClick(R.id.backClickArea)
    public void back(View view) {
        setResult(RESULT_OK,
                new Intent().putExtra(I.Goods.KEY_GOODS_ID, good_id).putExtra(I.Goods.KEY_IS_COLLECT, isCollect));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK, new Intent().putExtra(I.Goods.KEY_GOODS_ID, good_id)
                .putExtra(I.Goods.KEY_IS_COLLECT, true));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }

    @OnClick(R.id.iv_collect)
    public void onViewClicked() {
        user = FuLiCenterApplication.getInstance().getCurrentUser();
        if (user == null) {
            startActivityForResult(new Intent(GoodDetailsActivity.this, LoginActivity.class), 0);
        } else {
            if (isCollect) {
                userModel.removeCollect(GoodDetailsActivity.this, String.valueOf(good_id), user.getMuserName(), mListener);
                CommonUtils.showLongToast("取消收藏成功");
            } else {
                userModel.addCollect(GoodDetailsActivity.this, String.valueOf(good_id), user.getMuserName(), mListener);
                CommonUtils.showLongToast("添加收藏成功");
            }
        }
    }

    OnCompleteListener<MessageBean> mListener = new OnCompleteListener<MessageBean>() {
        @Override
        public void onSuccess(MessageBean result) {
            isCollect = !isCollect;
            updateUI();
        }

        @Override
        public void onError(String error) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadCollectStatus();
        }
    }

    @OnClick(R.id.iv_cart)
    public void onCart() {
        if(user!=null){
          userModel.addCart(this, good_id, user.getMuserName(),1, true, new OnCompleteListener<MessageBean>() {
              @Override
              public void onSuccess(MessageBean result) {
                  if(result!=null&&result.isSuccess()){
                  CommonUtils.showLongToast("添加购物车成功");
                  }
              }
              @Override
              public void onError(String error) {

              }
          });
        }else{
            startActivityForResult(new Intent(this,LoginActivity.class),I.ACTION_CART_ADD);
        }
    }

}

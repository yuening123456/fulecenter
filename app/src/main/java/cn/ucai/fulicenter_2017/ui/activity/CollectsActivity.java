package cn.ucai.fulicenter_2017.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.CollectBean;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.net.IUserModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.net.UserModel;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.ResultUtils;
import cn.ucai.fulicenter_2017.ui.adapter.CollectAdapter;
import cn.ucai.fulicenter_2017.ui.view.SpaceItemDecoration;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class CollectsActivity extends AppCompatActivity {
    @BindView(R.id.layout_back_ground)
    ImageView layoutBackGround;
    @BindView(R.id.tvDown)
    TextView tvDown;
    @BindView(R.id.rvGoods)
    RecyclerView rvGoods;
    @BindView(R.id.srfl)
    SwipeRefreshLayout srfl;
    @BindView(R.id.tv_nore)
    TextView tvNore;
    IUserModel model;
    ProgressDialog pd;
    GridLayoutManager gm;
    CollectAdapter adapter;
    int mPageId = I.PAGE_ID_DEFAULT;
    int mPageSize = I.PAGE_SIZE_DEFAULT;
    Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        bind = ButterKnife.bind(this);
        initDialog();
        model = new UserModel();
        initViw();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initDialog() {
        pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.load_more));
        pd.show();
    }

    private void initViw() {
        gm = new GridLayoutManager(this, I.COLUM_NUM);
        rvGoods.setLayoutManager(gm);
        rvGoods.addItemDecoration(new SpaceItemDecoration(15));
        //设置item的间距
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //   L.i("main","size"+position);
                if (adapter == null || position == adapter.getItemCount() - 1) {
                    return I.COLUM_NUM;
                }
                return 1;
            }
        });
        srfl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow));

    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullUpListener() {
        rvGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = gm.findLastVisibleItemPosition();
                if (adapter != null && lastVisibleItemPosition == adapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE && adapter.isMore()) {
                    mPageId++;
                    loadData();
                }

            }
        });
    }

    private void setPullDownListener() {
        srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setLayoutVisibility(true);
                mPageId = 1;
                loadData();
            }
        });
    }

    void setLayoutVisibility(boolean visibility) {
        srfl.setRefreshing(visibility);
        tvDown.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    void setListVisibility(boolean visibility) {
        tvNore.setVisibility(visibility ? View.GONE : View.VISIBLE);
        srfl.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.tv_nore)
    public void reloadData() {
        pd.show();
        loadData();

    }

    public void loadData() {
        User user = FuLiCenterApplication.getInstance().getCurrentUser();
        if(user==null){
            finish();
            return;
        }
        model.loadCollects(this, user.getMuserName(), mPageId, mPageSize, new OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                pd.dismiss();
                setLayoutVisibility(false);
                setListVisibility(true);
                if (result != null) {
                    ArrayList<CollectBean> list = ResultUtils.array2List(result);
                    L.e("main","list="+list);
                    if (mPageId == 1) {
                        adapter = null;
                    }
                    updateUI(list);
                } else {
                    if (adapter == null || adapter.getItemCount() == 1) {
                        setListVisibility(false);
                    }
                }
                if (adapter != null) {
                    adapter.setMore(result != null && result.length > 0);
                }
            }

            @Override
            public void onError(String error) {
                L.e("main", "error" + error);
                srfl.setRefreshing(false);
                tvDown.setVisibility(View.GONE);
                if (adapter == null || adapter.getItemCount() == 1) {
                    setListVisibility(false);
                }
            }

        });

    }

    private void updateUI(ArrayList<CollectBean> list) {
        if (adapter == null) {
            adapter = new CollectAdapter(this, list);
            rvGoods.setAdapter(adapter);
        } else {
            adapter.addList(list);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @OnClick(R.id.layout_back_ground)
    public void onViewClicked() {
        finish();
    }
}

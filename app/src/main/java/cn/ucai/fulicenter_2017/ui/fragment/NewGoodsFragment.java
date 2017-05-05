package cn.ucai.fulicenter_2017.ui.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.NewGoodsBean;
import cn.ucai.fulicenter_2017.data.net.GoodsModel;
import cn.ucai.fulicenter_2017.data.net.IGoodsModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.ResultUtils;
import cn.ucai.fulicenter_2017.ui.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter_2017.ui.view.SpaceItemDecoration;

import static android.R.attr.onClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    NewGoodsAdapter adapter;
    IGoodsModel model;
    GridLayoutManager gm;
    @BindView(R.id.tvDown)
    TextView tvDown;
    @BindView(R.id.rvGoods)
    RecyclerView rvGoods;
    @BindView(R.id.srfl)
    SwipeRefreshLayout srfl;
    Unbinder unbinder;
    @BindView(R.id.tv_nore)
    TextView tv_nore;

    int mPageId=I.PAGE_ID_DEFAULT;
    int catId=I.CAT_ID;
    int mPageSize=I.PAGE_SIZE_DEFAULT;
    ProgressDialog pd;
    public NewGoodsFragment() {
        // Required empty public constructor
    }

    public NewGoodsFragment(int catId) {
        this.catId = catId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDialog();
        model = new GoodsModel();
        initViw();
        loadData();
        setListener();
    }

    private void initDialog() {
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString (R.string .load_more));
        pd.show();
    }

    private void initViw() {
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        rvGoods.setLayoutManager(gm);
        rvGoods.addItemDecoration(new SpaceItemDecoration(15));
        //设置item的间距
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
             //   L.i("main","size"+position);
                if(adapter==null||position==adapter.getItemCount()-1){
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
                if(adapter!=null&&lastVisibleItemPosition==adapter.getItemCount()-1&&newState==RecyclerView.SCROLL_STATE_IDLE&&adapter.isMore()){
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
                mPageId=1;
                loadData();
            }
        });
    }
    void setLayoutVisibility(boolean visibility){
        srfl.setRefreshing(visibility);
        tvDown.setVisibility(visibility?View.VISIBLE:View.GONE);
    }
    void setListVisibility(boolean visibility){
        tv_nore.setVisibility(visibility?View.GONE:View.VISIBLE);
        srfl.setVisibility(visibility?View.VISIBLE:View.GONE);
    }

    @OnClick(R.id.tv_nore)
    public void reloadData(){
        pd.show();
        loadData();

    }

    public void loadData() {
        model.loadNewGoodsData(getContext(), catId, mPageId, mPageSize,
                new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        pd.dismiss();
                        setLayoutVisibility(false);
                        setListVisibility(true);
                        if(result!=null){
                            ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                            if(mPageId==1){
                               adapter=null;
                            }
                            updateUI(list);
                        }else{
                            if(adapter==null||adapter.getItemCount()==1){
                                setListVisibility(false);
                            }
                        }
                        if(adapter!=null){
                            adapter.setMore(result!=null&&result.length>0);
                        }
                    }
                    @Override
                    public void onError(String error) {
                        L.e("main", "error" + error);
                        srfl.setRefreshing(false);
                        tvDown.setVisibility(View.GONE);
                        if(adapter==null||adapter.getItemCount()==1){
                           setListVisibility(false);

                        }

                    }
                });

    }

    private void updateUI(ArrayList<NewGoodsBean> list) {
        if (adapter == null) {
            adapter = new NewGoodsAdapter(getContext(), list);
            rvGoods.setAdapter(adapter);
        }else{
            adapter.addList(list);

        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

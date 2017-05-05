package cn.ucai.fulicenter_2017.ui.fragment;


import android.os.Bundle;
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

    int mPageId=I.PAGE_ID_DEFAULT;
    int catId=I.CAT_ID;
    int mPageSize=I.PAGE_SIZE_DEFAULT;

    public NewGoodsFragment() {
        // Required empty public constructor
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
        model = new GoodsModel();
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        rvGoods.setLayoutManager(gm);
        initViw();
        loadData(mPageId);
        setListener();
    }

    private void initViw() {
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
                if(lastVisibleItemPosition==adapter.getItemCount()-1&&newState==RecyclerView.SCROLL_STATE_IDLE&&adapter.isMore()){
                    mPageId++;
                    loadData(mPageId);
                }

            }
        });
    }

    private void setPullDownListener() {
        srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srfl.setRefreshing(true);
                tvDown.setVisibility(View.VISIBLE);
                mPageId=1;
                loadData(mPageId);
            }
        });
    }

    public void loadData(int pageId) {
        model.loadNewGoodsData(getContext(), catId, pageId, mPageSize,
                new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        tvDown.setVisibility(View.GONE);
                        srfl.setRefreshing(false);
                        if(result!=null){
                            ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                            updateUI(list);
                        }
                        if(adapter!=null){
                            adapter.setMore(result!=null&&result.length>0);
                        }
                    }
                    @Override
                    public void onError(String error) {
                        L.e("main", "error" + error);
                        tvDown.setVisibility(View.GONE);
                        srfl.setRefreshing(false);
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

package cn.ucai.fulicenter_2017.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import cn.ucai.fulicenter_2017.data.bean.BoutiqueBean;
import cn.ucai.fulicenter_2017.data.bean.NewGoodsBean;
import cn.ucai.fulicenter_2017.data.net.GoodsModel;
import cn.ucai.fulicenter_2017.data.net.IGoodsModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.ResultUtils;
import cn.ucai.fulicenter_2017.ui.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter_2017.ui.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter_2017.ui.view.SpaceItemDecoration;


/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class BoutiqueFragment extends Fragment {
    @BindView(R.id.tvDown)
    TextView tvDown;
    @BindView(R.id.rvGoods)
    RecyclerView rvGoods;
    @BindView(R.id.srfl)
    SwipeRefreshLayout srfl;
    @BindView(R.id.tv_nore)
    TextView tv_nore;
    Unbinder unbinder;
    IGoodsModel model;
    LinearLayoutManager llm;
    ProgressDialog pd;
    BoutiqueAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDialog();
        model=new GoodsModel();
        initViw();
        loadData();
        setListener();
    }
    private void initDialog() {
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString (R.string .load_more));
        pd.show();
    } private void initViw() {
        llm = new LinearLayoutManager(getContext());
        rvGoods.setLayoutManager(llm);
        srfl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow));

    }

    private void setListener() {
        setPullDownListener();
    }

    private void setPullDownListener() {
        srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setLayoutVisibility(true);
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
    private void loadData() {
        model.loadBoutiqueData(getContext(),
                new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                pd.dismiss();
                setLayoutVisibility(false);
                setListVisibility(true);
                if(result!=null){
                    ArrayList<BoutiqueBean> list = ResultUtils.array2List(result);
                    updateUI(list);
                }

            }
            @Override
            public void onError(String error) {
                L.e("main", "error" + error);
                srfl.setRefreshing(false);
                tvDown.setVisibility(View.GONE);
            }
        });
    }
    private void updateUI(ArrayList<BoutiqueBean> list) {
        if (adapter == null) {
            adapter = new BoutiqueAdapter(getContext(), list);
            rvGoods.setAdapter(adapter);
        }else{
            adapter.initList(list);
        }



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

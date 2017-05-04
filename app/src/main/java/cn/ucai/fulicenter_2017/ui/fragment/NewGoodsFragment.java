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
        loadData();
    }

    public void loadData() {
        model.loadNewGoodsData(getContext(), 0, 1, 10,
                new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        if (result != null) {
                            L.e("main", "result" + result);
                            ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                            updateUI(list);
                        }
                    }
                    @Override
                    public void onError(String error) {
                        L.e("main", "error" + error);

                    }
                });

    }

    private void updateUI(ArrayList<NewGoodsBean> list) {
        if (adapter == null) {
            adapter = new NewGoodsAdapter(getContext(), list);
            rvGoods.setAdapter(adapter);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

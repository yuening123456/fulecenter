package cn.ucai.fulicenter_2017.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.BoutiqueBean;
import cn.ucai.fulicenter_2017.data.bean.CategoryChildBean;
import cn.ucai.fulicenter_2017.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter_2017.data.net.GoodsModel;
import cn.ucai.fulicenter_2017.data.net.IGoodsModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.ResultUtils;
import cn.ucai.fulicenter_2017.ui.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter_2017.ui.adapter.CategoryAdapter;

import static cn.ucai.fulicenter_2017.R.id.list_item;
import static cn.ucai.fulicenter_2017.R.id.tvDown;


/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class CategoryFragment extends Fragment {


    @BindView(R.id.tv_nore)
    TextView tv_nore;
    Unbinder unbinder;
    IGoodsModel model;
    ProgressDialog pd;
    CategoryAdapter adapter;
    @BindView(R.id.rvGoods)
    ExpandableListView rvGoods;
    List<CategoryGroupBean> groupList=new ArrayList<>();
    List<List<CategoryChildBean>> childList=new ArrayList<>();
    int groupCount=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDialog();
        model = new GoodsModel();
        loadData();
    }

    private void initDialog() {
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.load_more));
        pd.show();
    }
    void setListVisibility(boolean visibility) {
        tv_nore.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }
    @OnClick(R.id.tv_nore)
    public void reloadData() {
        pd.show();
        loadData();

    }

    private void loadData() {
        model.loadCategoryGroup(getContext(),
                new OnCompleteListener<CategoryGroupBean[]>() {
                    @Override
                    public void onSuccess(CategoryGroupBean[] result) {
                        L.e("main","Category_loadData"+result);
                        setListVisibility(true);
                        if (result != null) {
                            groupList = ResultUtils.array2List(result);
                            for (CategoryGroupBean bean : groupList) {
                                loadChildData(bean.getId());
                            }
                        }

                    }
                    @Override
                    public void onError(String error) {
                        L.e("main", "error" + error);
                        pd.dismiss();
                        setListVisibility(false);
                    }
                });
    }
    private void loadChildData(int parentId) {
        L.e("main","loadChildData,parentId="+parentId);
        model.loadCategoryChild(getContext(), parentId, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                L.e("main","loadChildData"+result);
                if(result!=null){
                    ArrayList<CategoryChildBean> list=ResultUtils.array2List(result);
                    childList.add(list);
                }else{
                }
                if(groupCount==groupList.size()){
                    pd.dismiss();
                    setListVisibility(true);
                    updateUI();
                }
            }
            @Override
            public void onError(String error) {
                if(groupCount==groupList.size()){
                    pd.dismiss();
                    setListVisibility(false);
                }

            }
        });

    }

    private void updateUI() {
        if (adapter == null) {
            adapter = new CategoryAdapter(getContext(), groupList,childList);
            rvGoods.setAdapter(adapter);
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

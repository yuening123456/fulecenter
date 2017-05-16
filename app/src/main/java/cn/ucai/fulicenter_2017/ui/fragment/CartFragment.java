package cn.ucai.fulicenter_2017.ui.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.data.bean.BoutiqueBean;
import cn.ucai.fulicenter_2017.data.bean.CartBean;
import cn.ucai.fulicenter_2017.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter_2017.data.bean.MessageBean;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.net.GoodsModel;
import cn.ucai.fulicenter_2017.data.net.IGoodsModel;
import cn.ucai.fulicenter_2017.data.net.IUserModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.net.UserModel;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.ResultUtils;
import cn.ucai.fulicenter_2017.ui.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter_2017.ui.adapter.CartAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {


    @BindView(R.id.btn_Settlement)
    Button btnSettlement;
    @BindView(R.id.total)
    TextView tvSumPrice;
    @BindView(R.id.tv_save_price)
    TextView tvSavePrice;
    @BindView(R.id.layout_cart)
    RelativeLayout layoutCart;
    Unbinder unbinder;
    IUserModel model;
    LinearLayoutManager llm;
    ProgressDialog pd;
    @BindView(R.id.tvDown)
    TextView tvDown;
    @BindView(R.id.rvGoods)
    RecyclerView rvGoods;
    @BindView(R.id.srfl)
    SwipeRefreshLayout srfl;
    @BindView(R.id.tv_nore)
    TextView tvNore;
    CartAdapter adapter;
    ArrayList<CartBean> list=new ArrayList<>();

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDialog();
        model = new UserModel();
        initViw();
        setListener();
    }

    private void initDialog() {
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.load_more));
        pd.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void initViw() {
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

    void setLayoutVisibility(boolean visibility) {
        srfl.setRefreshing(visibility);
        tvDown.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    void setListVisibility(boolean visibility,boolean isError) {
        tvNore.setText(isError?"网络请求失败":"购物车空空的。。");
        tvNore.setVisibility(visibility ? View.GONE : View.VISIBLE);
        srfl.setVisibility(visibility ? View.VISIBLE : View.GONE);
        layoutCart.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.tv_nore)
    public void reloadData() {
        pd.show();
        loadData();

    }

    private void loadData() {

        User user = FuLiCenterApplication.getInstance().getCurrentUser();
        if(user!=null){
            model.loadCart(getContext(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    pd.dismiss();
                    setLayoutVisibility(false);
                    setListVisibility(true,false);
                    list.clear();
                    if (result != null) {
                        list.addAll(ResultUtils.array2List(result));
                        updateUI();
                        L.e("main", "list.size=" + list.size());
                        if (list.size() == 0) {
                            setListVisibility(false, false);
                        } else {
                            setListVisibility(true, false);
                        }
                    } else {
                        setListVisibility(false,false);
                    }
                }

                @Override
                public void onError(String error) {
                    pd.dismiss();
                    L.e("main", "error" + error);
                    setLayoutVisibility(false);
                    list.clear();
                   setListVisibility(false,true);
                }
            });
        }

    }

    private void updateUI() {
        if (adapter == null) {
            adapter = new CartAdapter(getContext(),list);
            adapter.setCbkListener(cbkListener);
            adapter.setClickListener(clickListener);
            rvGoods.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void sumPrice(){
        int sumPrice=0;
        int savePrice=0;
        if(list.size()>0){
            for (CartBean bean : list) {
                if(bean.isChecked()){
                    GoodsDetailsBean goods = bean.getGoods();
                    if(goods!=null){
                        sumPrice+=getPrice(goods.getCurrencyPrice())*bean.getCount();
                        savePrice+=(getPrice(goods.getCurrencyPrice())-getPrice(goods.getRankPrice()))
                                *bean.getCount();
                    }
                }
            }
        }else{
            sumPrice=0;
            savePrice=0;
        }
        tvSumPrice.setText("￥"+sumPrice);
        tvSavePrice.setText("￥"+savePrice);

    }
    private int getPrice(String currencyPrice){
        String price=currencyPrice.substring(currencyPrice.indexOf("￥")+1);
        return Integer.parseInt(price);
    }
    CompoundButton.OnCheckedChangeListener cbkListener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position= (int) buttonView.getTag();
            list.get(position).setChecked(isChecked);
            sumPrice();
        }
    };
    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.iv_cart_add:
                  final int   position= (int) v.getTag();
                    updateCart(position,1);
                    break;
                case R.id.iv_cart_del:
                    final int positions = (int) v.getTag();
                    if(list!=null) {
                        final CartBean bean = list.get(positions);
                        if (bean.getCount() > 1) {
                            model.updateCart(getContext(), bean.getId(), bean.getCount() - 1, false, new OnCompleteListener<MessageBean>() {
                                @Override
                                public void onSuccess(MessageBean result) {
                                    list.get(positions).setCount(bean.getCount() - 1);
                                    sumPrice();
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onError(String error) {

                                }
                            });
                        }
                        if (bean.getCount() == 1) {
                            model.removeCart(getContext(), bean.getId(), new OnCompleteListener<MessageBean>() {
                                @Override
                                public void onSuccess(MessageBean result) {
                                    list.remove(positions);
                                    sumPrice();
                                    adapter.notifyDataSetChanged();
                                    L.e("main","updateCart,list.size()="+list.size());
                                    if(list.size()==0){
                                        setListVisibility(false,false);
                                    }
                                }
                                @Override
                                public void onError(String error) {

                                }
                            });
                        }
                    }else{
                        setListVisibility(false,false);
                    }
                    break;

            }
        }
    };

    private void updateCart(final int position, final int count) {
        final CartBean bean = list.get(position);
        model.updateCart(getContext(), bean.getId(), bean.getCount() + count, false, new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if(result!=null&&result.isSuccess()){
                    list.get(position).setCount(bean.getCount()+count);
                    sumPrice();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }


}

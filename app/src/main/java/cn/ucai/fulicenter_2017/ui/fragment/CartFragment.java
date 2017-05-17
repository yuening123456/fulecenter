package cn.ucai.fulicenter_2017.ui.fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.CartBean;
import cn.ucai.fulicenter_2017.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter_2017.data.bean.MessageBean;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.net.IUserModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.net.UserModel;
import cn.ucai.fulicenter_2017.data.utils.CommonUtils;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.ResultUtils;
import cn.ucai.fulicenter_2017.ui.activity.OrderActivity;
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
    ArrayList<CartBean> list = new ArrayList<>();
    @BindView(R.id.newGoodsFragment)
    RelativeLayout newGoodsFragment;
    int sumPrice ,savePrice;

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
        loadData();
        setListener();
    }

    private void initDialog() {
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.load_more));
        pd.show();
    }

 /*   @Override
    public void onResume() {
        super.onResume();
        loadData();
    }*/

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
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATA_CART);
        getContext().registerReceiver(mReceiver, filter);
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

    void setListVisibility(boolean visibility, boolean isError) {
        tvNore.setText(isError ? "网络请求失败" : "购物车空空的。。");
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
        if (user != null) {
            model.loadCart(getContext(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    pd.dismiss();
                    setLayoutVisibility(false);
                    setListVisibility(true, false);
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
                        setListVisibility(false, false);
                    }
                    sumPrice();
                }

                @Override
                public void onError(String error) {
                    pd.dismiss();
                    L.e("main", "error" + error);
                    setLayoutVisibility(false);
                    list.clear();
                    setListVisibility(false, true);
                }
            });
        }

    }

    private void updateUI() {
        if (adapter == null) {
            adapter = new CartAdapter(getContext(), list);
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

    private void sumPrice() {
         sumPrice = 0;
        savePrice = 0;
        if (list.size() > 0) {
            for (CartBean bean : list) {
                if (bean.isChecked()) {
                    GoodsDetailsBean goods = bean.getGoods();
                    if (goods != null) {
                        sumPrice += getPrice(goods.getCurrencyPrice()) * bean.getCount();
                        savePrice += (getPrice(goods.getCurrencyPrice()) - getPrice(goods.getRankPrice()))
                                * bean.getCount();
                    }
                }
            }
        } else {
            sumPrice = 0;
            savePrice = 0;
        }
        tvSumPrice.setText("￥" + sumPrice);
        tvSavePrice.setText("￥" + savePrice);

    }

    private int getPrice(String currencyPrice) {
        String price = currencyPrice.substring(currencyPrice.indexOf("￥") + 1);
        return Integer.parseInt(price);
    }

    CompoundButton.OnCheckedChangeListener cbkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();
            if (isChecked) {
                list.get(position).setChecked(isChecked);
                sumPrice();
                Log.i("main", "onCheckedChanged(),isChecked" + isChecked);
            }
        }
    };
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.iv_cart_add:
                    final int position = (int) v.getTag();
                    updateCart(position, 1);
                    break;
                case R.id.iv_cart_del:
                    final int positions = (int) v.getTag();
                    if (list != null) {
                        final CartBean bean = list.get(positions);
                        Log.i("main", "bean.getId():" + bean.getId());
                        if (bean.getCount() > 1) {
                            updateCart(positions, -1);
                        } else {
                            model.removeCart(getContext(), bean.getId(), new OnCompleteListener<MessageBean>() {
                                @Override
                                public void onSuccess(MessageBean result) {
                                    list.remove(positions);
                                    sumPrice();
                                    adapter.notifyDataSetChanged();
                                    if (list.size() == 0) {
                                        setListVisibility(false, false);
                                    }
                                }

                                @Override
                                public void onError(String error) {

                                }
                            });
                        }
                    } else {
                        setListVisibility(false, false);
                    }
                    break;

            }
        }
    };

    private void updateCart(final int position, final int count) {
        final CartBean bean = list.get(position);
        Log.i("main", "bean.getId:" + bean.getId());
        model.updateCart(getContext(), bean.getId(), bean.getCount() + count, false, new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    Log.i("main", "bea.getCount:" + bean.getCount());
                    list.get(position).setCount(bean.getCount() + count);
                    sumPrice();
                    adapter.notifyDataSetChanged();
                } else {
                    Log.i("main", "CartFragment.fail");
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    UpdateCartBroadCastReceiver mReceiver = new UpdateCartBroadCastReceiver();

    @OnClick(R.id.btn_Settlement)
    public void onViewClicked() {
        if(sumPrice!=0){
            startActivity(new Intent(getContext(),OrderActivity.class).putExtra(I.Cart.PAY_PRICE,sumPrice-savePrice));
        }else{
            CommonUtils.showLongToast("您还没有选择宝贝哦。。");
        }
    }

    class UpdateCartBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            GoodsDetailsBean bean = (GoodsDetailsBean) intent.getSerializableExtra(I.Cart.class.toString());
            updateCarts(bean);
        }

        private void updateCarts(GoodsDetailsBean bean) {
            boolean isHas = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getGoodsId() == bean.getGoodsId()) {
                    list.get(i).setCount(list.get(i).getCount() + 1);
                    isHas = true;
                    adapter.notifyDataSetChanged();
                    sumPrice();
                    return;
                }
            }
            if (!isHas) {
                CartBean cart = new CartBean();
                cart.setCount(1);
                cart.setGoodsId(bean.getGoodsId());
                cart.setChecked(true);
                cart.setUserName(FuLiCenterApplication.getInstance().getCurrentUser().getMuserName());
                cart.setGoods(bean);
                list.add(cart);
                adapter.notifyDataSetChanged();
                sumPrice();
            }
        }
    }


}




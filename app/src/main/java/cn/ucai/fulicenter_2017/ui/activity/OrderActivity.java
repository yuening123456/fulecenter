package cn.ucai.fulicenter_2017.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

=======
>>>>>>> origin/master
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class OrderActivity extends AppCompatActivity {


    @BindView(R.id.layout_back_ground)
    ImageView layoutBackGround;
    @BindView(R.id.layout_order_title)
    RelativeLayout layoutOrderTitle;
    @BindView(R.id.tv_order_name)
    TextView tvOrderName;
    @BindView(R.id.ed_order_name)
    EditText edOrderName;
    @BindView(R.id.layout_order_name)
    RelativeLayout layoutOrderName;
    @BindView(R.id.tv_order_phone)
    TextView tvOrderPhone;
    @BindView(R.id.ed_order_phone)
    EditText edOrderPhone;
    @BindView(R.id.layout_order_phone)
    RelativeLayout layoutOrderPhone;
    @BindView(R.id.tv_order_province)
    TextView tvOrderProvince;
    @BindView(R.id.spin_order_province)
    Spinner spinOrderProvince;
    @BindView(R.id.layout_order_province)
    RelativeLayout layoutOrderProvince;
    @BindView(R.id.tv_order_street)
    TextView tvOrderStreet;
    @BindView(R.id.ed_order_street)
    EditText edOrderStreet;
    @BindView(R.id.layout_order_street)
    RelativeLayout layoutOrderStreet;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_order_buy)
    TextView tvOrderBuy;
    @BindView(R.id.layout_order)
    RelativeLayout layoutOrder;
<<<<<<< HEAD
    int price;
    private static String URL = "http://218.244.151.190/demo/charge";
=======

>>>>>>> origin/master
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
<<<<<<< HEAD
         price = getIntent().getIntExtra(I.Cart.PAY_PRICE,0);
        tvOrderPrice.setText("合计：￥ "+price);

        //设置需要使用的支付方式
        PingppOne.enableChannels(new String[] { "wx", "alipay", "upacp", "bfb", "jdpay_wap" });

        // 提交数据的格式，默认格式为json
        // PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";

        PingppLog.DEBUG = true;
=======
        int price = getIntent().getIntExtra(I.Cart.PAY_PRICE,0);
        tvOrderPrice.setText("合计：￥ "+price);
>>>>>>> origin/master
    }

    @OnClick(R.id.tv_order_buy)
    public void onBuy() {
        if (checkInput()) {
<<<<<<< HEAD
            onPlay();
=======
            startActivity(new Intent());
>>>>>>> origin/master

        }
    }

<<<<<<< HEAD
    private void onPlay() {
        {
            // 产生个订单号
            String orderNo = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

            // 计算总金额（以分为单位）
            int amount =price*100 ;

            // 构建账单json对象
            JSONObject bill = new JSONObject();

            // 自定义的额外信息 选填
            JSONObject extras = new JSONObject();
            try {
                extras.put("extra1", "extra1");
                extras.put("extra2", "extra2");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                bill.put("order_no", orderNo);
                bill.put("amount", amount);
                bill.put("extras", extras);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //壹收款: 创建支付通道的对话框
            PingppOne.showPaymentChannels(this, bill.toString(), URL, new PaymentHandler() {
                @Override public void handlePaymentResult(Intent data) {
                    if (data != null) {
                        /**
                         * code：支付结果码  -2:服务端错误、 -1：失败、 0：取消、1：成功
                         * error_msg：支付结果信息
                         */
                        int code = data.getExtras().getInt("code");
                        String result = data.getExtras().getString("result");
                    }
                }
            });
        }
    }

    private boolean checkInput(){
        String receiveName=edOrderName.getText().toString();
        if(TextUtils.isEmpty(receiveName)){
            edOrderName.setError("收货人姓名不能为空");
            edOrderName.requestFocus();
            return false;
        }
        String mobile=edOrderPhone.getText().toString();
        if(TextUtils.isEmpty(mobile)){
            edOrderPhone.setError("手机号码不能为空");
            edOrderPhone.requestFocus();
            return false;
        }
        if(!mobile.matches("[\\d]{11}")){
            edOrderPhone.setError("手机号码格式错误");
            edOrderPhone.requestFocus();
            return false;
        }
        String area=spinOrderProvince.getSelectedItem().toString();
        if(TextUtils.isEmpty(area)){
            Toast.makeText(OrderActivity.this,"收货地区不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        String address=edOrderStreet.getText().toString();
        if(TextUtils.isEmpty(address)){
            edOrderStreet.setError("街道地址不能为空");
            edOrderStreet.requestFocus();
            return false;
        }
        return true;
    }
=======
        private boolean checkInput(){
            String receiveName=edOrderName.getText().toString();
            if(TextUtils.isEmpty(receiveName)){
                edOrderName.setError("收货人姓名不能为空");
                edOrderName.requestFocus();
                return false;
            }
            String mobile=edOrderPhone.getText().toString();
            if(TextUtils.isEmpty(mobile)){
                edOrderPhone.setError("手机号码不能为空");
                edOrderPhone.requestFocus();
                return false;
            }
            if(!mobile.matches("[\\d]{11}")){
                edOrderPhone.setError("手机号码格式错误");
                edOrderPhone.requestFocus();
                return false;
            }
            String area=spinOrderProvince.getSelectedItem().toString();
            if(TextUtils.isEmpty(area)){
                Toast.makeText(OrderActivity.this,"收货地区不能为空",Toast.LENGTH_SHORT).show();
                return false;
            }
            String address=edOrderStreet.getText().toString();
            if(TextUtils.isEmpty(address)){
                edOrderStreet.setError("街道地址不能为空");
                edOrderStreet.requestFocus();
                return false;
            }
            return true;
        }
>>>>>>> origin/master

    @OnClick(R.id.layout_back_ground)
    public void onBack() {
        finish();
    }
}

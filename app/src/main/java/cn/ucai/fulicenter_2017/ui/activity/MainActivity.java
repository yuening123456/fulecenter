package cn.ucai.fulicenter_2017.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.data.bean.NewGoodsBean;
import cn.ucai.fulicenter_2017.data.net.GoodsModel;
import cn.ucai.fulicenter_2017.data.net.IGoodsModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.data.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public  void onCheckedChange(View view){
        testDownLoadData();


    }
    public void testDownLoadData(){
        IGoodsModel model=new GoodsModel();
        model.loadNewGoodsData(MainActivity.this, 0, 1, 10,
                new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        if(result!=null){
                            L.e("main","result"+result);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        L.e("main","error"+error);

                    }
                });

    }
}

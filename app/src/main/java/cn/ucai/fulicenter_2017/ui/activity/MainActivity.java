package cn.ucai.fulicenter_2017.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.ucai.fulicenter_2017.R;

import cn.ucai.fulicenter_2017.ui.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {
    NewGoodsFragment goodsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goodsFragment=new NewGoodsFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.layoutFragment,goodsFragment).commit();
    }
    public  void onCheckedChange(View view){


    }

}

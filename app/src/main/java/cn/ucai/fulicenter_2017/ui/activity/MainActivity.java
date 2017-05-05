package cn.ucai.fulicenter_2017.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.ucai.fulicenter_2017.R;

import cn.ucai.fulicenter_2017.ui.fragment.BoutiqueFragment;
import cn.ucai.fulicenter_2017.ui.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {
    NewGoodsFragment goodsFragment;
    BoutiqueFragment boutiqueFragment;
    Fragment[] mFragment;
    int currentIndex, index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        showFragment();
    }



    private void initFragment() {
        goodsFragment=new NewGoodsFragment();
        boutiqueFragment=new BoutiqueFragment();
        mFragment=new Fragment[5];
        mFragment[0]=goodsFragment;
        mFragment[1]=boutiqueFragment;
    }
    private void showFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.layoutFragment,mFragment[0])
                .add(R.id.layoutFragment,mFragment[1])
                .show(goodsFragment)
                .hide(boutiqueFragment)
                .commit();
    }

    public  void onCheckedChange(View view){
            switch (view.getId()){
                case R.id.tvNewGoods:
                    index=0;
                    break;
                case R.id.tvBoutique:
                    index=1;
                    break;
            }
            setFragment();

    }

    private void setFragment() {
        if(index!=currentIndex){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
          ft.hide(mFragment[currentIndex]);
           if(!mFragment[index].isAdded()){
               ft.add(R.id.layoutFragment,mFragment[index]);
           }
           ft.show(mFragment[index]);
            ft.commit();
            currentIndex=index;

        }
    }

}

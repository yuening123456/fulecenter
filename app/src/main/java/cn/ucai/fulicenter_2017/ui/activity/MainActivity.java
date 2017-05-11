package cn.ucai.fulicenter_2017.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.ucai.fulicenter_2017.R;

import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.ui.fragment.BoutiqueFragment;
import cn.ucai.fulicenter_2017.ui.fragment.CategoryFragment;
import cn.ucai.fulicenter_2017.ui.fragment.NewGoodsFragment;
import cn.ucai.fulicenter_2017.ui.fragment.PersonalFragment;

public class MainActivity extends AppCompatActivity {
    NewGoodsFragment goodsFragment;
    BoutiqueFragment boutiqueFragment;
    CategoryFragment categoryFragment;
    PersonalFragment personalFragment;
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
        categoryFragment=new CategoryFragment();
        personalFragment=new PersonalFragment();
        mFragment=new Fragment[5];
        mFragment[0]=goodsFragment;
        mFragment[1]=boutiqueFragment;
        mFragment[2]=categoryFragment;
        mFragment[4]=personalFragment;
    }
    private void showFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.layoutFragment,mFragment[0])
                .add(R.id.layoutFragment,mFragment[1])
                .add(R.id.layoutFragment,mFragment[2])
                .show(goodsFragment)
                .hide(boutiqueFragment)
                .hide(categoryFragment)
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
                case R.id.tvCategory:
                    index=2;
                    break;
                case R.id.tvCenter:

                    if(FuLiCenterApplication.getInstance().getCurrentUser()==null){
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }else{
                        index=4;
                    }
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

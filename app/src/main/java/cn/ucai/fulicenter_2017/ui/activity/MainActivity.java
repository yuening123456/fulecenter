package cn.ucai.fulicenter_2017.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.ui.fragment.BoutiqueFragment;
import cn.ucai.fulicenter_2017.ui.fragment.CartFragment;
import cn.ucai.fulicenter_2017.ui.fragment.CategoryFragment;
import cn.ucai.fulicenter_2017.ui.fragment.NewGoodsFragment;
import cn.ucai.fulicenter_2017.ui.fragment.PersonalFragment;

public class MainActivity extends AppCompatActivity {
    NewGoodsFragment goodsFragment;
    BoutiqueFragment boutiqueFragment;
    CategoryFragment categoryFragment;
    PersonalFragment personalFragment;
    CartFragment cartFragment;
    Fragment[] mFragment;
    int currentIndex, index;
    RadioButton[] mRadioButton;
    @BindView(R.id.tvNewGoods)
    RadioButton tvNewGoods;
    @BindView(R.id.tvBoutique)
    RadioButton tvBoutique;
    @BindView(R.id.tvCategory)
    RadioButton tvCategory;
    @BindView(R.id.tvCart)
    RadioButton tvCart;
    @BindView(R.id.tvCenter)
    RadioButton tvCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        initRadioButton();
        showFragment();
    }

    private void initRadioButton() {
        mRadioButton = new RadioButton[5];
        mRadioButton[0]=tvNewGoods;
        mRadioButton[1]=tvBoutique;
        mRadioButton[2]=tvCategory;
        mRadioButton[3]=tvCart;
        mRadioButton[4]=tvCenter;


    }


    private void initFragment() {
        goodsFragment = new NewGoodsFragment();
        boutiqueFragment = new BoutiqueFragment();
        categoryFragment = new CategoryFragment();
        cartFragment=new CartFragment();
        personalFragment = new PersonalFragment();
        mFragment = new Fragment[5];
        mFragment[0] = goodsFragment;
        mFragment[1] = boutiqueFragment;
        mFragment[2] = categoryFragment;
        mFragment[3]=cartFragment;
        mFragment[4] = personalFragment;
    }

    private void showFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.layoutFragment, mFragment[0])
                .add(R.id.layoutFragment, mFragment[1])
                .add(R.id.layoutFragment, mFragment[2])
                .show(goodsFragment)
                .hide(boutiqueFragment)
                .hide(categoryFragment)
                .commit();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.tvNewGoods:
                index = 0;
                break;
            case R.id.tvBoutique:
                index = 1;
                break;
            case R.id.tvCategory:
                index = 2;
                break;
            case R.id.tvCart:
                if (FuLiCenterApplication.getInstance().getCurrentUser() == null) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), I.REQUEST_CODE_LOGIN_FROM_CART);
                } else {
                    index = 3;
                }
                break;
            case R.id.tvCenter:
                if (FuLiCenterApplication.getInstance().getCurrentUser() == null) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), I.REQUEST_CODE_LOGIN);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();

    }

    private void setFragment() {
        if (index != currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragment[currentIndex]);
            if (!mFragment[index].isAdded()) {
                ft.add(R.id.layoutFragment, mFragment[index]);
            }
            ft.show(mFragment[index]);
            ft.commit();
            currentIndex = index;
        }
        setRadioButton();
    }

    private void setRadioButton() {
        for (int i = 0; i < mRadioButton.length; i++) {
            mRadioButton[i].setChecked(i==index?true:false);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.e("main","MainActivity.onActivityResult requsetCode="+requestCode+"resultCode="+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==I.REQUEST_CODE_LOGIN){
                index=4;
            }
            if(requestCode==I.REQUEST_CODE_LOGIN_FROM_CART){
                index=3;
            }
            setFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(index==4&&FuLiCenterApplication.getInstance().getCurrentUser()==null){
            index=0;
            setFragment();

        }
    }
}

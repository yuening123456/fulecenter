package cn.ucai.fulicenter_2017.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.ui.fragment.NewGoodsFragment;

public class CategoryChildActivity extends AppCompatActivity {

    @BindView(R.id.backClickArea)
    ImageView backClickArea;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.layoutFragment)
    FrameLayout layoutFragment;
    Unbinder bind;
   boolean sortByPrice,sortByAddtime;
    NewGoodsFragment fragment;
    int sortBy=I.SORT_BY_ADDTIME_ASC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        bind = ButterKnife.bind(this);
        int catId = getIntent().getIntExtra(I.CategoryChild.CAT_ID, I.CAT_ID);
        fragment=new NewGoodsFragment(catId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layoutFragment, fragment)
                .commit();
    }

    @OnClick(R.id.backClickArea)
    public void onClick(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }

    @OnClick({R.id.btn_price, R.id.addTime})
    public void onViewClicked(View view) {
        L.e("main","onViewClicked"+view.getId());
        switch (view.getId()) {
            case R.id.btn_price:
                sortByPrice=!sortByPrice;
                sortBy=sortByPrice?I.SORT_BY_PRICE_ASC:I.SORT_BY_PRICE_DESC;
                break;
            case R.id.addTime:
                sortByAddtime=!sortByAddtime;
                sortBy=sortByPrice?I.SORT_BY_ADDTIME_ASC:I.SORT_BY_ADDTIME_DESC;
                break;
        }
        fragment.sortBy(sortBy);

    }
}

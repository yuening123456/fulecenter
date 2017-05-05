package cn.ucai.fulicenter_2017.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.ui.fragment.NewGoodsFragment;

public class BoutiqueChild_Activity extends AppCompatActivity {

    @BindView(R.id.backClickArea)
    ImageView backClickArea;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.layoutFragment)
    FrameLayout layoutFragment;
    Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_child);
        bind=ButterKnife.bind(this);
        int catId=getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID,I.CAT_ID);
        String title=getIntent().getStringExtra(I.Boutique.TITLE);
        tvCommonTitle.setText(title);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layoutFragment,new NewGoodsFragment(catId))
                .commit();
    }
    @OnClick(R.id.backClickArea)
    public void onClick(View view){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bind!=null){
            bind.unbind();
        }
    }
}

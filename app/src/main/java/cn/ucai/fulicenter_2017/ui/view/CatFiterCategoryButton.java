package cn.ucai.fulicenter_2017.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;


import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.data.bean.CategoryChildBean;
import cn.ucai.fulicenter_2017.data.utils.CommonUtils;
import cn.ucai.fulicenter_2017.ui.adapter.CatFiterAdapter;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class CatFiterCategoryButton extends android.support.v7.widget.AppCompatButton {
    Context context;
    PopupWindow mpopuWin;
    boolean isExpand=false;
    CatFiterAdapter adapter;
    GridView gv;

    public CatFiterCategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        setPopupWindowListener();
    }

    private void setPopupWindowListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpand) {
                    if (mpopuWin != null && mpopuWin.isShowing()) {
                        mpopuWin.dismiss();
                    }
                } else{
                        initPowin();
                    }
                    setArrow();
                }
        });
    }

    private void setArrow() {
        Drawable end=context.getDrawable(isExpand? R.drawable.arrow2_up:R.drawable.arrow2_down);
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,end,null);
        isExpand=!isExpand;
    }

    public  void initPowin(){
        if(mpopuWin==null){
            mpopuWin=new PopupWindow(context);
            mpopuWin.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            mpopuWin.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            mpopuWin.setContentView(gv);
            gv.setNumColumns(2);

        }
        mpopuWin.showAsDropDown(this);
    }
    public void initView(String groupName, ArrayList<CategoryChildBean> list){
        if(groupName==null||list==null||list.size()==0){
            CommonUtils.showLongToast("数据获取异常，请重试！");
            return;
        }
        this.setText(groupName);
        adapter=new CatFiterAdapter(context,list);
        gv=new GridView(context);
        gv.setHorizontalSpacing(10);
        gv.setVerticalSpacing(10);
        gv.setAdapter(adapter);

    }


    public void release() {
        if(mpopuWin!=null){
            mpopuWin.dismiss();
        }
    }
}

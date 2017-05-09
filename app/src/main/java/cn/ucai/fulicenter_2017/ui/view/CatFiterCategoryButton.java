package cn.ucai.fulicenter_2017.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;


import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.ucai.fulicenter_2017.R;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class CatFiterCategoryButton extends Button {
    Context context;
    PopupWindow mpopuWin;
    boolean isExpand=false;

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
            TextView tv=new TextView(context);
            tv.setTextColor(getResources().getColor(R.color.red));
            tv.setTextSize(30);
            tv.setText("cagsdf");
            mpopuWin.setContentView(tv);
        }
        mpopuWin.showAsDropDown(this);
    }
}

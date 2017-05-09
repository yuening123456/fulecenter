package cn.ucai.fulicenter_2017.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.CategoryChildBean;
import cn.ucai.fulicenter_2017.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter_2017.data.utils.ImageLoader;
import cn.ucai.fulicenter_2017.data.utils.L;
import cn.ucai.fulicenter_2017.ui.activity.CategoryChildActivity;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class CatFiterAdapter extends BaseAdapter {
    Context context;
    ArrayList<CategoryChildBean> list;


    public CatFiterAdapter(Context context, ArrayList<CategoryChildBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public CategoryChildBean getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CatFilterViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_cat_filter, null);
            holder=new CatFilterViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (CatFilterViewHolder) convertView.getTag();
        }
        holder.bind(position);
        return convertView;
    }

         class CatFilterViewHolder {
        @BindView(R.id.iv_categoryChildThumb)
        ImageView ivCategoryChildThumb;
        @BindView(R.id.tv_categoryChildName)
        TextView tvCategoryChildName;
        @BindView(R.id.layout_categoryChild)
        LinearLayout layoutCategoryChild;


        CatFilterViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bind(final int position) {
            final CategoryChildBean bean = list.get(position);

            if(bean!=null){
                ImageLoader.downloadImg(context,ivCategoryChildThumb,bean.getImageUrl());
                tvCategoryChildName.setText(bean.getName());
                layoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        L.e("main","setOnClickListener");
                        context.startActivity(new Intent(context,CategoryChildActivity.class)
                                .putExtra(I.CategoryGroup.NAME,bean.getName())
                                .putExtra(I.CategoryChild.ID,list)
                                .putExtra(I.CategoryChild.CAT_ID,bean.getId()));
                    }


                });

            }

        }

         }
}

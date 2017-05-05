package cn.ucai.fulicenter_2017.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.data.bean.BoutiqueBean;
import cn.ucai.fulicenter_2017.data.utils.ImageLoader;

/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<BoutiqueAdapter.BoutiqueViewHolder> {
    Context context;
    ArrayList<BoutiqueBean> list;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BoutiqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new BoutiqueViewHolder(View.inflate(context, R.layout.item_boutiqye, null));
    }

    @Override
    public void onBindViewHolder(BoutiqueViewHolder holder, int position) {
        BoutiqueBean bean = list.get(position);
        holder.tvTitle.setText(bean.getTitle());
        holder.tvBoutiqueName.setText(bean.getName());
        holder.tvBoutiqueDescription.setText(bean.getDescription());
        ImageLoader.downloadImg(context,holder.ivBoutique,bean.getImageurl());

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

     class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_Boutique)
        ImageView ivBoutique;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView tvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView tvBoutiqueDescription;
        @BindView(R.id.layout_item_boutique)
        RelativeLayout layoutItemBoutique;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

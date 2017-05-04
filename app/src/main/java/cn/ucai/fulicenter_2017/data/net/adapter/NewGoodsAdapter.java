package cn.ucai.fulicenter_2017.data.net.adapter;

import android.content.Context;
import android.media.ImageReader;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.data.bean.NewGoodsBean;
import cn.ucai.fulicenter_2017.data.utils.ImageLoader;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter<NewGoodsAdapter.GoodsViewHolder> {
    Context context;
    ArrayList<NewGoodsBean> list;

    public NewGoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return new GoodsViewHolder(View.inflate(context,R.layout.item_goods,null));
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        NewGoodsBean bean = list.get(position);
        holder.tvUserName.setText(bean.getGoodsName());
        holder.tvPrice.setText(bean.getCurrencyPrice());
        ImageLoader.downloadImg(context,holder.ivAvatar,bean.getGoodsThumb());

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

 class GoodsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;

     GoodsViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
        }
    }
}

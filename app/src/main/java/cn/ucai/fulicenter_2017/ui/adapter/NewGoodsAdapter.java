package cn.ucai.fulicenter_2017.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.I;
import cn.ucai.fulicenter_2017.data.bean.NewGoodsBean;
import cn.ucai.fulicenter_2017.data.utils.ImageLoader;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<NewGoodsBean> list;

    boolean isMore=true;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public NewGoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case I.TYPE_ITEM:
                return new GoodsViewHolder(View.inflate(context, R.layout.item_goods, null));
            case I.TYPE_FOOTER:
                return new FooterHolder(View.inflate(context, R.layout.item_footer, null));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parent, int position) {
        if(position == getItemCount() -1){
            FooterHolder footerHolder= (FooterHolder) parent;
            footerHolder.tvFooter.setText(getFooter());
            footerHolder.tvFooter.setVisibility(View.VISIBLE);
            return;
        }else{

            NewGoodsBean bean = list.get(position);
            GoodsViewHolder goodsViewHolder = (GoodsViewHolder) parent;
            goodsViewHolder.tvUserName.setText(bean.getGoodsName());
            goodsViewHolder.tvPrice.setText(bean.getCurrencyPrice());
            ImageLoader.downloadImg(context, goodsViewHolder.ivAvatar, bean.getGoodsThumb());
        }
    }

    private int getFooter() {
        return isMore()?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size()+1:1;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() - 1 == position) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void addList(ArrayList<NewGoodsBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    class GoodsViewHolder extends RecyclerView.ViewHolder {
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

   class FooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

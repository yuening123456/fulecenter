package cn.ucai.fulicenter_2017.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.data.bean.CategoryChildBean;
import cn.ucai.fulicenter_2017.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter_2017.data.utils.ImageLoader;

/**
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    List<CategoryGroupBean> groupList;

    public CategoryAdapter(Context context, List<CategoryGroupBean> groupList, List<List<CategoryChildBean>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
    }

    List<List<CategoryChildBean>> childList;


    @Override
    public int getGroupCount() {
        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList != null && childList.get(groupPosition) != null ? childList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return groupList != null ? groupList.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return childList != null && childList.get(groupPosition) != null ?
                childList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_categorygroup, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.bind(groupPosition, isExpanded);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_categorychild, null);
            holder=new ChildViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ChildViewHolder) convertView.getTag();
        }
        holder.bind(groupPosition,childPosition);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        @BindView(R.id.iv_categoryGroup)
        ImageView ivCategoryGroup;
        @BindView(R.id.tv_categoryGroupName)
        TextView tvCategoryGroupName;
        @BindView(R.id.iv_expand)
        ImageView ivExpand;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bind(int groupPosition, boolean isExpanded) {
            CategoryGroupBean bean = groupList.get(groupPosition);
            tvCategoryGroupName.setText(bean.getName());
            ivExpand.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
            ImageLoader.downloadImg(context,ivCategoryGroup,bean.getImageUrl());
        }
    }

    class ChildViewHolder {
        @BindView(R.id.iv_categoryChild)
        ImageView ivCategoryChild;
        @BindView(R.id.tv_categoryChildName)
        TextView tvCategoryChildName;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bind(int groupPosition, int childPosition) {
            CategoryChildBean bean=getChild(groupPosition,childPosition);
            if(bean!=null){
                ImageLoader.downloadImg(context,ivCategoryChild,bean.getImageUrl());
                tvCategoryChildName.setText(bean.getName());
            }
        }
    }
}

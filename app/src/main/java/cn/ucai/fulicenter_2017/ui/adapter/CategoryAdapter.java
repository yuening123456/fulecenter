package cn.ucai.fulicenter_2017.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter_2017.data.bean.CategoryChildBean;
import cn.ucai.fulicenter_2017.data.bean.CategoryGroupBean;

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
        return groupList==null?0:groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList!=null&&childList.get(groupPosition)!=null?childList.get(groupPosition).size():0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return groupList!=null?groupList.get(groupPosition):null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return childList!=null&&childList.get(groupPosition)!=null?
        childList.get(groupPosition).get(childPosition):null;
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
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

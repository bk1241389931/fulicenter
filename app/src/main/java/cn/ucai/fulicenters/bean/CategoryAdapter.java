package cn.ucai.fulicenters.bean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;

import cn.ucai.fulicenters.R;

/**
 * Created by bk124 on 2016/10/20.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> groupList,
                           ArrayList<ArrayList<CategoryChildBean>> childList) {
        this.mContext = context;
        this.mGroupList = new ArrayList<>();
        mGroupList.addAll(groupList);
        this.mChildList = new ArrayList<>();
        mChildList.addAll(childList);
    }

    //获取大类的数量
    @Override
    public int getGroupCount() {
        return mGroupList!=null?mGroupList.size():0;
    }

    //获取小类的数量
    @Override
    public int getChildrenCount(int groupProsition) {
        return mChildList!=null&&mChildList.get(groupProsition)!=null?
                mChildList.get(groupProsition).size():0;
    }

    //获取一个大类的对象
    @Override
    public CategoryGroupBean getGroup(int groupProsition) {
        return mGroupList!=null?mGroupList.get(groupProsition):null;
    }

    //获取一个小类的对象
    @Override


    public CategoryChildBean getChild(int groupProsition, int childProsition) {
        return mChildList!=null&&mChildList.get(groupProsition)!=null?
                mChildList.get(groupProsition).get(childProsition):null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view==null){
            view=view.inflate(mContext, R.layout.item_category_grout,null);
        }else {
            view.getTag();
        }
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

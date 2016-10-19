package cn.ucai.fulicenters.adapter;

import android.content.Context;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenters.I;
import cn.ucai.fulicenters.R;
import cn.ucai.fulicenters.bean.BoutiqueBean;
import cn.ucai.fulicenters.utils.ImageLoader;
import cn.ucai.fulicenters.view.FooterViewHolder;

/**
 * Created by bk124 on 2016/10/19.
 */
public class BoutiqueAdapter extends Adapter<BoutiqueAdapter.BoutiqueViewHolder> {
    Context mcontext;
    ArrayList<BoutiqueBean> mlist;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mcontext = context;
        mlist = new ArrayList<>();
        mlist.addAll(list);
    }

    @Override
    public BoutiqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoutiqueViewHolder holder = new BoutiqueViewHolder(LayoutInflater.from(mcontext)
                    .inflate(R.layout.item_boutique, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BoutiqueViewHolder holder, int position) {
            BoutiqueBean boutiqueBean=mlist.get(position);
            ImageLoader.downloadImg(mcontext,holder.mIvBoutiqueImg,boutiqueBean.getImageurl());
            holder.mTvBoutiqueTitle.setText(boutiqueBean.getTitle());
            holder.mTvBoutiqueName.setText(boutiqueBean.getName());
            holder.mTvBoutiqueDescription.setText(boutiqueBean.getDescription());
    }

    @Override
    public int getItemCount() {
        //如果mlist不等于空，返回mlist.size()+1否则返回1
        return mlist != null ? mlist.size()  : 0;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (mlist!=null){
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    class BoutiqueViewHolder extends ViewHolder{
        @BindView(R.id.ivBoutiqueImg)
        ImageView mIvBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mTvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mTvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mTvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout mLayoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

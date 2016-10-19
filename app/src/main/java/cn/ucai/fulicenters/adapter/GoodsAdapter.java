package cn.ucai.fulicenters.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenters.I;
import cn.ucai.fulicenters.R;
import cn.ucai.fulicenters.bean.NewGoodsBean;
import cn.ucai.fulicenters.utils.ImageLoader;
import cn.ucai.fulicenters.utils.MFGT;
import cn.ucai.fulicenters.view.FooterViewHolder;

/**
 * Created by bk124 on 2016/10/18.
 */
public class GoodsAdapter extends Adapter {
    Context mcontext;
    List<NewGoodsBean> mList;
    boolean isMore;

    public GoodsAdapter(Context context, List<NewGoodsBean> list) {
        mcontext = context;
        mList=new ArrayList<>();
        mList.addAll(list);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mcontext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mcontext, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position)==I.TYPE_FOOTER){
                FooterViewHolder vh= (FooterViewHolder) holder;
            vh.mTvFooter.setText(getFootString());
        }else {
            GoodsViewHolder vh=(GoodsViewHolder)holder;
            NewGoodsBean goods=mList.get(position);
            ImageLoader.downloadImg(mcontext,vh.mIvGoodsThumb,goods.getGoodsThumb());
            vh.mIvGoodsName.setText(goods.getGoodsName());
            vh.mTvGoodsPrice.setText(goods.getCurrencyPrice());
            vh.mLayoutGoods.setTag(goods.getGoodsId());

        }

    }

    private int getFootString() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class GoodsViewHolder extends ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.ivGoodsName)
        TextView mIvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView mTvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout mLayoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.layout_goods)
        public void onGoodsItmenClick(){
            int goodsId= (int) mLayoutGoods.getTag();
            MFGT.gotoGoodsDetailsActivity(mcontext,goodsId);
        }
    }
}

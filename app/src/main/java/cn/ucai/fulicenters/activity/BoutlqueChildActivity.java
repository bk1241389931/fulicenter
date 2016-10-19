package cn.ucai.fulicenters.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenters.I;
import cn.ucai.fulicenters.R;
import cn.ucai.fulicenters.adapter.GoodsAdapter;
import cn.ucai.fulicenters.bean.NewGoodsBean;
import cn.ucai.fulicenters.net.NetDao;
import cn.ucai.fulicenters.net.OkHttpUtils;
import cn.ucai.fulicenters.utils.CommonUtils;
import cn.ucai.fulicenters.utils.ConvertUtils;
import cn.ucai.fulicenters.utils.L;
import cn.ucai.fulicenters.view.SpaceItemDecoration;

public class BoutlqueChildActivity extends BaseActivity {

    @BindView(R.id.backClickArea)
    LinearLayout mBackClickArea;
    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.tv_refresh)
    TextView tmTRefresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    BoutlqueChildActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mlist;
    int pageId=1;
    GridLayoutManager glm;
    int catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_boutlque_child);
        ButterKnife.bind(this);
        catId=getIntent().getIntExtra(I.Boutique.CAT_ID,0);
        if (catId==0){
            finish();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm=new GridLayoutManager(mContext, I.COLUM_NUM);
        mRv.setLayoutManager(glm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }



    @Override
    protected void setListener() {
        private void downLoadNewGoods(final int action) {
            NetDao.downloadNewGoods(mContext,I.CAT_ID ,pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
                @Override
                public void onSuccess(NewGoodsBean[] result) {
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    mAdapter.setMore(true);
                    L.e("result"+result);
                    if (result!=null&&result.length>0){
                        ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                        if (action==I.ACTION_DOWNLOAD||action==I.ACTION_PULL_DOWN){
                            mAdapter.initData(list);
                        }else {
                            mAdapter.addData(list);
                        }
                        if (list.size()<I.PAGE_SIZE_DEFAULT){
                            mAdapter.setMore(false);
                        }
                    }else mAdapter.setMore(false);
                }

                @Override
                public void onError(String error) {
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    mAdapter.setMore(false);
                    CommonUtils.showLongToast(error);
                    L.e("error"+error);
                }
            });
        }

    private void setPullUpListener() {
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition=glm.findLastCompletelyVisibleItemPosition();
                if (newState==RecyclerView.SCROLL_STATE_IDLE
                        &&lastPosition==mAdapter.getItemCount()-1
                        &&mAdapter.isMore()){
                    pageId++;
                    downLoadNewGoods(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition=glm.findFirstVisibleItemPosition();
                mSrl.setEnabled(firstPosition==0);
            }
        });
    }

    @Override
    protected void initData() {
        downLoadNewGoods(I.ACTION_DOWNLOAD);
    }

}

package cn.ucai.fulicenters.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenters.FuLiCenterApplication;
import cn.ucai.fulicenters.I;
import cn.ucai.fulicenters.R;
import cn.ucai.fulicenters.bean.AlbumsBean;
import cn.ucai.fulicenters.bean.GoodsDetailsBean;
import cn.ucai.fulicenters.bean.MessageBean;
import cn.ucai.fulicenters.bean.User;
import cn.ucai.fulicenters.net.NetDao;
import cn.ucai.fulicenters.net.OkHttpUtils;
import cn.ucai.fulicenters.utils.CommonUtils;
import cn.ucai.fulicenters.utils.L;
import cn.ucai.fulicenters.utils.MFGT;
import cn.ucai.fulicenters.view.FlowIndicator;
import cn.ucai.fulicenters.view.SlideAutoLoopView;

public class GoodsDeatilActivity extends BaseActivity {

    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_good_name_english)
    TextView mTvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView mTvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView mTvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView mTvGoodPriceCurrent;
    @BindView(R.id.indicator)
    FlowIndicator mIndicator;
    @BindView(R.id.salv)
    SlideAutoLoopView mSalv;
    @BindView(R.id.wn_good_brier)
    WebView mWnGoodBrier;

    int goodsId;
    GoodsDeatilActivity mContext;
    private boolean collected;
    boolean isCollected=false;
    @BindView(R.id.iv_good_collect)
    ImageView mIvGoodCollect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_deatil);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("detafls", "goodsId" + goodsId);
        if (goodsId==0){
            finish();
        }

        mContext=this;
        super.onCreate(savedInstanceState);
    }




    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details"+result);
                if (result!=null){
                    showGoodDetails(result);
                }else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details"+error);
                CommonUtils.showLongToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        mTvGoodNameEnglish.setText(details.getGoodsEnglishName());
        mTvGoodName.setText(details.getGoodsName());
        mTvGoodPriceCurrent.setText(details.getCurrencyPrice());
        mTvGoodPriceShop.setText(details.getShopPrice());
        mSalv.startPlayLoop(mIndicator,getAlbumImgUrl(details),getAlbumImgCount(details));
        mWnGoodBrier.loadDataWithBaseURL(null,details.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getProperties()!=null&&details.getProperties().length>0){
           return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls=new String[]{};
        if (details.getProperties()!=null&&details.getProperties().length>0){
            AlbumsBean[] albums=details.getProperties()[0].getAlbums();
            urls=new String[albums.length];
            for (int i=0;i<albums.length;i++){
                urls[i]=albums[i].getImgUrl();
            }
        }
            return urls;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        isCollected();
    }

    @OnClick(R.id.backClickArea)
    public void onBackClick(){
        MFGT.finish(this);
    }
    public void onback(View v){
        MFGT.finish(this);
    }

    @OnClick(R.id.iv_good_collect)
    public void onCollectClick(){
        User user = FuLiCenterApplication.getUser();
        if(user==null){
            MFGT.gotoLogin(mContext);
        }else{
            if(isCollected){
                NetDao.deleteCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if(result!=null && result.isSuccess()){
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                            mContext.sendStickyBroadcast(new Intent("update_collect").putExtra(I.Collect.GOODS_ID,goodsId));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }else{
                NetDao.addCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if(result!=null && result.isSuccess()){
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }
    }

    private void isCollected() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.isColected(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollected = true;
                    }else{
                        isCollected = false;
                    }
                    updateGoodsCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollected = false;
                    updateGoodsCollectStatus();
                }
            });
        }
        updateGoodsCollectStatus();
    }

    private void updateGoodsCollectStatus() {
        if (isCollected) {
            mIvGoodCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            mIvGoodCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }
}

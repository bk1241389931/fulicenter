package cn.ucai.fulicenters.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenters.FuLiCenterApplication;
import cn.ucai.fulicenters.R;
import cn.ucai.fulicenters.activity.MainActivity;
import cn.ucai.fulicenters.bean.MessageBean;
import cn.ucai.fulicenters.bean.Result;
import cn.ucai.fulicenters.bean.User;
import cn.ucai.fulicenters.dao.UserDao;
import cn.ucai.fulicenters.net.NetDao;
import cn.ucai.fulicenters.net.OkHttpUtils;
import cn.ucai.fulicenters.utils.ImageLoader;
import cn.ucai.fulicenters.utils.L;
import cn.ucai.fulicenters.utils.MFGT;
import cn.ucai.fulicenters.utils.ResultUtils;

/**
 * Created by bk124 on 2016/10/25.
 */
public class PersonalCenterFragment extends BaseFragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();

    @BindView(R.id.tv_center_settings)
    TextView mTvCenterSettings;
    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_collect_count)
    TextView mTvCollectCount;

    MainActivity mContext;
    User user=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        mContext= (MainActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        user=FuLiCenterApplication.getUser();
        if (user==null){
            MFGT.gotoLogin(mContext);
        }else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());

        }
    }

    @Override
    protected void setListener() {

    }
    @Override
    public void onResume(){
        super.onResume();
        user=FuLiCenterApplication.getUser();
        if (user!=null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
            syncUserInfo();
            syncCollectsCount();
        }
    }

    @OnClick({R.id.tv_center_settings,R.id.center_user_info})
    public void gotoSettings() {
        MFGT.gotoSettings(mContext);
    }

    @OnClick(R.id.layout_center_collect)
    public void gotoCollectsList(){
        MFGT.gotoCollects(mContext);
    }

    private void syncUserInfo(){
        NetDao.syncUserInfo(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if(result!=null){
                    User u = (User) result.getRetData();
                    if(!user.equals(u)){
                        UserDao dao = new UserDao(mContext);
                        boolean b = dao.saveUser(u);
                        if(b){
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAvatar);
                            mTvUserName.setText(user.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void syncCollectsCount() {
        NetDao.getCollectsCount(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    mTvCollectCount.setText(result.getMsg());
                }else{
                    mTvCollectCount.setText(String.valueOf(0));
                }
            }

            @Override
            public void onError(String error) {
                mTvCollectCount.setText(String.valueOf(0));
                L.e(TAG,"error="+error);
            }
        });
    }
}

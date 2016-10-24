package cn.ucai.fulicenters.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenters.FuLiCenterApplication;
import cn.ucai.fulicenters.R;
import cn.ucai.fulicenters.bean.User;
import cn.ucai.fulicenters.dao.UserDao;
import cn.ucai.fulicenters.utils.L;
import cn.ucai.fulicenters.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG=SplashActivity.class.getSimpleName();

    private final long sleepTime=2000;
    SplashActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user= FuLiCenterApplication.getUser();
                if (user==null) {
                    UserDao dao = new UserDao(mContext);
                    user = dao.getUser("a9527010");
                    L.e(TAG,"database,user"+user);
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        },sleepTime);
    }
}



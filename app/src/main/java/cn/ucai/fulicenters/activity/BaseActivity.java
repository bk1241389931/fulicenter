package cn.ucai.fulicenters.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.ucai.fulicenters.utils.MFGT;

/**
 * Created by bk124 on 2016/10/20.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void setListener();
    public void onBackPressed(){
        MFGT.finish(this);
    }
}

package cn.ucai.fulicenters;

import android.app.Application;

/**
 * Created by bk124 on 2016/10/18.
 */
public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;

    public FuLiCenterApplication(){
        instance=this;
    }

    public static FuLiCenterApplication getInstance(){
        if (instance==null){
            instance=new FuLiCenterApplication();
        }
        return instance;
    }
}

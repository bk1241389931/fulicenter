package cn.ucai.fulicenters.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenters.activity.GoodsDeatilActivity;
import cn.ucai.fulicenters.I;
import cn.ucai.fulicenters.R;
import cn.ucai.fulicenters.activity.MainActivity;
import cn.ucai.fulicenters.bean.BoutiqueBean;
import cn.ucai.fulicenters.bean.CategoryChildBean;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent);
    }
    public static void gotoGoodsDetailsActivity(Context context, int goodsId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDeatilActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
        startActivity(context,intent);
    }
    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
//    public static void gotoBoutlqueChildActivity(Context context,BoutiqueBean bean){
//        Intent intent = new Intent();
//        intent.setClass(context,BoutiqueChildActivity.class);
//        intent.putExtra(I.Boutique.CAT_ID,bean);
//        startActivity(context,intent);
//    }


}

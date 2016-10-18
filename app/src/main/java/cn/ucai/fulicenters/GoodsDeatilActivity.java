package cn.ucai.fulicenters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenters.utils.L;

public class GoodsDeatilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_deatil);
        int goodsId=getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID,0);
        L.e("detafls","goodsId"+goodsId);
    }
}

package cn.ucai.fulicenters.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenters.FuLiCenterApplication;
import cn.ucai.fulicenters.I;
import cn.ucai.fulicenters.R;
import cn.ucai.fulicenters.fragment.BoutiqueFragment;
import cn.ucai.fulicenters.fragment.CartFragment;
import cn.ucai.fulicenters.fragment.CategoryFragment;
import cn.ucai.fulicenters.fragment.NewGoodsFragment;
import cn.ucai.fulicenters.fragment.PersonalCenterFragment;
import cn.ucai.fulicenters.utils.L;
import cn.ucai.fulicenters.utils.MFGT;

public class MainActivity extends BaseActivity {
    private static final String TAG=MainActivity.class.getSimpleName();

    @BindView(R.id.layout_new_good)
    RadioButton layoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton layoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton layoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton layoutCart;
    @BindView(R.id.tvCartHint)
    TextView tvCartHint;
    @BindView(R.id.layout_personal_center)
    RadioButton layoutPersonalCenter;

    int index;
    int currentIndex=0;
    RadioButton[] rbs;
    Fragment[] mFragment;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    CartFragment mCartFragment;
    PersonalCenterFragment mPersonalCenterFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActively onCreate");
        super.onCreate(savedInstanceState);
    }

    private void initFragment() {
        mFragment=new Fragment[5];
        mNewGoodsFragment=new NewGoodsFragment();
        mBoutiqueFragment=new BoutiqueFragment();
        mCategoryFragment=new CategoryFragment();
        mPersonalCenterFragment=new PersonalCenterFragment();
        mFragment[0]=mNewGoodsFragment;
        mFragment[1]=mBoutiqueFragment;
        mFragment[2]=mCategoryFragment;
        mFragment[3] = mCartFragment;
        mFragment[4]=mPersonalCenterFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,mNewGoodsFragment)
//                .add(R.id.fragment_container,mBoutiqueFragment)
//                .add(R.id.fragment_container,mCategoryFragment)
//                .hide(mBoutiqueFragment)
//                .hide(mCategoryFragment)
                .show(mNewGoodsFragment)
                .commit();
    }
    @Override
    protected void initView() {
        rbs=new RadioButton[5];
        rbs[0]=layoutNewGood;
        rbs[1]=layoutBoutique;
        rbs[2]=layoutCategory;
        rbs[3]=layoutCart;
        rbs[4]=layoutPersonalCenter;

    }

    public void onCheckedChange(View v) {
    switch (v.getId()){
        case R.id.layout_new_good:
            index=0;
            break;
        case R.id.layout_boutique:
            index=1;
            break;
        case R.id.layout_category:
            index=2;
            break;
        case R.id.layout_cart:
            index=3;
            if (FuLiCenterApplication.getUser() == null) {
                MFGT.gotoLoginFromCart(this);
            } else {
                index=3;
            }
            break;
        case R.id.layout_personal_center:
            index=4;
            break;
    }
        setFragment();
    }

    private void setFragment() {
        if (index!=currentIndex){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragment[currentIndex]);
            if (!mFragment[index].isAdded()){
                ft.add(R.id.fragment_container,mFragment[index]);
            }
            ft.show(mFragment[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex=index;
    }

    private void setRadioButtonStatus() {
        L.i("index"+index);
        for (int i=0;i<rbs.length;i++){
            if (i==index){
                rbs[i].setChecked(true);
            }else{
                rbs[i].setChecked(false);
            }
        }
    }


    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void setListener() {

    }

    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.e(TAG,"onResume...");
        if (index == 4 && FuLiCenterApplication.getUser() == null) {
            index=0;
        }
        setFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG,"onActivityResult,requestCode"+requestCode);
//        if (requestCode== I.REQUEST_CODE_LOGIN&&FuLiCenterApplication.getUser()!=null);{
//            index=4;

        if(FuLiCenterApplication.getUser()!=null){
            if(requestCode == I.REQUEST_CODE_LOGIN) {
                index = 4;
            }
            if(requestCode == I.REQUEST_CODE_LOGIN_FROM_CART){
                index = 3;
            }
        }
    }

}

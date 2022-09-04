package com.example.agsale.mvp.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.InformationApiService;
import com.example.agsale.mvp.model.api.service.LoginApiService;
import com.example.agsale.mvp.model.bean.InformationTranslation;
import com.example.agsale.mvp.model.bean.RegisterTranslation;
import com.example.agsale.mvp.ui.log.PasswordLogActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private TextView first;
    private TextView shop;
    private TextView farm;
    private TextView tmy;

    private Fragment homeFragment;
    private Fragment shopFragment;
    private Fragment farmFragment;
    private Fragment tmyFragment;
    @Override
    protected void initBaseData() {

    }

    @Override
    protected void baseConfigView() {
        init();
        setFragment(0);
        search();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
    private void search(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<InformationTranslation> call=informationApiService.getInformation();
        call.enqueue(new Callback<InformationTranslation>() {
            @Override
            public void onResponse(Call<InformationTranslation> call, Response<InformationTranslation> response) {
                InformationTranslation informationTranslation = response.body();
                if (response.body()==null){
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this, PasswordLogActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                    MainActivity.this.finish();
                    return;
                }
                int code=informationTranslation.getCode();
                String msg=informationTranslation.getMsg();
                System.out.println("code"+code+"\n"+"msg"+msg);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        if(!(code ==200)){
                            Intent intent=new Intent();
                            intent.setClass(MainActivity.this, PasswordLogActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                            MainActivity.this.finish();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<InformationTranslation> call, Throwable t) {

            }
        });
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
            case R.id.first2:
                setFragment(0);
                break;
            case R.id.shop2:
                setFragment(1);
                break;
            case R.id.farm2:
                setFragment(2);
                break;
            case R.id.my2:
                setFragment(3);
                break;
        }
    }

    private void init(){
        first = findViewById(R.id.first2);
        shop = findViewById(R.id.shop2);
        farm = findViewById(R.id.farm2);
        tmy = findViewById(R.id.my2);

        first.setOnClickListener(this);
        shop.setOnClickListener(this);
        farm.setOnClickListener(this);
        tmy.setOnClickListener(this);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setFragment(int index){
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        hideFragments(mTransaction);
        switch (index){
            case 0:
                first.setTextColor(getResources()
                        .getColor(R.color.press));
                first.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_first_press,0,0);
                shop.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_shop_green,0,0);
                farm.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_farm_green,0,0);
                tmy.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_home_green,0,0);
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                    mTransaction.add(R.id.container, homeFragment,
                            "clothes_fragment");
                }else {
                    mTransaction.show(homeFragment);
                }
                break;
            case 1:
                shop.setTextColor(getResources()
                        .getColor(R.color.press));
                shop.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_shop_press,0,0);
                first.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_first_green,0,0);
                farm.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_farm_green,0,0);
                tmy.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_home_green,0,0);
                if(shopFragment == null){
                    shopFragment = new ShopFragment();
                    mTransaction.add(R.id.container, shopFragment,
                            "clothes_fragment");
                }else {
                    mTransaction.show(shopFragment);
                }
                break;
            case 2:
                farm.setTextColor(getResources()
                        .getColor(R.color.press));
                farm.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_farm_press,0,0);
                tmy.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_home_greenlittle,0,0);
                first.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_first_greenlittle,0,0);
                shop.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_shop_greenlittle,0,0);
                if(farmFragment == null){
                    farmFragment = new FarmFragment();
                    mTransaction.add(R.id.container, farmFragment,
                            "clothes_fragment");
                }else {
                    mTransaction.show(farmFragment);
                }
                break;
            case 3:
                tmy.setTextColor(getResources()
                        .getColor(R.color.press));
                tmy.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_home_press,0,0);
                farm.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_farm_blue,0,0);
                first.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_first_blue,0,0);
                shop.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.bottom_shop_blue,0,0);
                if(tmyFragment == null){
                    tmyFragment = new TMyFragment();
                    mTransaction.add(R.id.container, tmyFragment,
                            "clothes_fragment");
                }else {
                    mTransaction.show(tmyFragment);
                }
                break;
        }
        mTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction){
        if(homeFragment != null){
            transaction.hide(homeFragment);
            first.setTextColor(getResources()
                    .getColor(R.color.grey));
            first.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.bottom_first_green,0,0);
        }
        if(shopFragment != null){
            transaction.hide(shopFragment);
            shop.setTextColor(getResources()
                    .getColor(R.color.grey));
            shop.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.bottom_shop_green,0,0);
        }
        if(farmFragment != null){
            transaction.hide(farmFragment);
            farm.setTextColor(getResources()
                    .getColor(R.color.grey));
            farm.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.bottom_farm_green,0,0);
        }
        if(tmyFragment != null){
            transaction.hide(tmyFragment);
            tmy.setTextColor(getResources()
                    .getColor(R.color.grey));
            tmy.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.bottom_home_green,0,0);
        }
    }
}

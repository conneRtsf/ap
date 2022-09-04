package com.example.agsale.mvp.ui.packages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;

import butterknife.OnClick;

public class PurchaseActivity extends BaseActivity {
    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void initBaseData() {

    }

    @Override
    protected void baseConfigView() {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_purchase;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
}
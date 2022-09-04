package com.example.agsale.mvp.ui.packages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.mvp.adapter.BucketAdapter;

import butterknife.BindView;

public class ChooseAreaActivity extends BaseActivity {
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void initBaseData() {

    }

    @Override
    protected void baseConfigView() {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_package;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
}
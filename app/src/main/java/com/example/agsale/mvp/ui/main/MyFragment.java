package com.example.agsale.mvp.ui.main;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agsale.R;
import com.example.agsale.base.BaseFragment;
import com.example.agsale.intercept.InformationIntercept;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.InformationApiService;
import com.example.agsale.mvp.model.bean.InformationTranslation;
import com.example.agsale.mvp.model.bean.PhotoTranslation;
import com.example.agsale.mvp.model.bean.RegisterTranslation;
import com.example.agsale.mvp.ui.my.SettingActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyFragment extends BaseFragment {
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.account)
    TextView account;
    @OnClick(R.id.setting)
    void goToSetting(){
        Intent intent=new Intent();
        intent.setClass(getActivity(), SettingActivity.class);
        startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        getInformation();
        getPhoto();
    }

    @Override
    public void onPause() {
        super.onPause();
        getInformation();
        getPhoto();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInformation();
        getPhoto();
    }

    @Override
    protected void initData() {

    }
    private void getPhoto(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<PhotoTranslation> call=informationApiService.getPhoto();
        call.enqueue(new Callback<PhotoTranslation>() {
            @Override
            public void onResponse(Call<PhotoTranslation> call, Response<PhotoTranslation> response) {
                PhotoTranslation photoTranslation=response.body();
                assert photoTranslation != null;
                PhotoTranslation.MyData photo=photoTranslation.getData();
                System.out.println(photo);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        head.setImageBitmap(returnBitMap(photo.getPersonalphoto()));
                    }
                });
            }

            @Override
            public void onFailure(Call<PhotoTranslation> call, Throwable t) {
                Log.e(TAG, "info：" + t.getMessage() + "," + t.toString());
                Toast.makeText(requireActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getInformation(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<InformationTranslation> call=informationApiService.getInformation();
        call.enqueue(new Callback<InformationTranslation>() {
            @Override
            public void onResponse(Call<InformationTranslation> call, Response<InformationTranslation> response) {
                InformationTranslation informationTranslation = response.body();
                assert informationTranslation != null;
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InformationTranslation.MyData data=informationTranslation.getData();
                        name.setText(data.getUsername());
                        account.setText(data.getUid());
                    }
                });
            }

            @Override
            public void onFailure(Call<InformationTranslation> call, Throwable t) {

            }
        });
    }
}
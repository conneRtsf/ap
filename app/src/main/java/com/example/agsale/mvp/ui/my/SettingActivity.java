
package com.example.agsale.mvp.ui.my;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.InformationApiService;
import com.example.agsale.mvp.model.api.service.LoginApiService;
import com.example.agsale.mvp.model.bean.InformationTranslation;
import com.example.agsale.mvp.model.bean.PhotoTranslation;
import com.example.agsale.mvp.model.bean.RegisterTranslation;
import com.example.agsale.mvp.ui.log.PasswordLogActivity;
import com.example.agsale.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.account)
    TextView account;
    @OnClick(R.id.back)
    void back(){

        finish();
    }
    @OnClick(R.id.exit)
    void exit(){
        exitRequest();
    }
    @OnClick(R.id.privacy)
    void goToPrivacy(){
        Intent intent=new Intent();
        intent.setClass(SettingActivity.this,PravacyActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.address)
    void goToAddress(){
        Intent intent=new Intent();
        intent.setClass(SettingActivity.this,AddressActivity.class);
        startActivity(intent);
    }
    @Override
    protected void initBaseData() {

    }
    @Override
    public void onPause() {
        super.onPause();
        getInformation();
        getPhoto();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInformation();
        getPhoto();
    }

    @Override
    protected void baseConfigView() {
        getInformation();
        getPhoto();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
    private void exitRequest(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        LoginApiService loginApiService=retrofit.create(LoginApiService.class);
        Call<RegisterTranslation> call=loginApiService.exit();
        call.enqueue(new Callback<RegisterTranslation>() {
            @Override
            public void onResponse(Call<RegisterTranslation> call, Response<RegisterTranslation> response) {
                RegisterTranslation registerTranslation=response.body();
                String msg=registerTranslation.getMsg();
                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingActivity.this, msg, Toast.LENGTH_SHORT).show();
                        if(msg.equals("退出成功")){
                            Intent intent=new Intent();
                            intent.setClass(SettingActivity.this, PasswordLogActivity.class);
                            startActivity(intent);
                            SharedPreferencesUtil.getInstance().delete();
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<RegisterTranslation> call, Throwable t) {

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
                InformationTranslation.MyData data=informationTranslation.getData();

                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
    private void getPhoto(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<PhotoTranslation> call=informationApiService.getPhoto();
        call.enqueue(new Callback<PhotoTranslation>() {
            @Override
            public void onResponse(Call<PhotoTranslation> call, Response<PhotoTranslation> response) {
                PhotoTranslation photoTranslation=response.body();
                if (photoTranslation==null){
                    return;
                }
                PhotoTranslation.MyData photo=photoTranslation.getData();
                System.out.println(photo);
                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        head.setImageBitmap(returnBitMap(photo.getPersonalphoto()));
                    }
                });
            }

            @Override
            public void onFailure(Call<PhotoTranslation> call, Throwable t) {
                Log.e(TAG, "info：" + t.getMessage() + "," + t.toString());
                Toast.makeText(SettingActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
package com.example.agsale.mvp.ui.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.InformationApiService;
import com.example.agsale.mvp.model.bean.AreaTranslation;
import com.example.agsale.mvp.model.bean.DivisionModel;
import com.example.agsale.mvp.model.bean.Divisions;
import com.example.agsale.mvp.model.bean.InformationTranslation;
import com.example.agsale.mvp.model.bean.PhotoTranslation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import top.defaults.view.Division;
import top.defaults.view.DivisionPickerView;

public class AddressActivity extends BaseActivity {
    @BindView(R.id.people)
    TextView people;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.xiangxi)
    TextView more;

    private static final String TAG = "test";
//
    @OnClick(R.id.change)
    void add(){
        Intent intent=new Intent();
        intent.setClass(AddressActivity.this,AddAreaActivity.class);
        startActivity(intent);
    }
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.head1)
    ImageView head1;
    @OnClick(R.id.backS)
    void back(){
        finish();
    }
    private String paths;
    public File png;

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public File getPng() {
        return png;
    }

    public void setPng(File png) {
        this.png = png;
    }
    @Override
    protected void initBaseData() {

    }

    @Override
    protected void baseConfigView() {
        getInformation();
        getPhoto();
        getArea();
    }
    @Override
    public void onPause() {
        super.onPause();
        getInformation();
        getPhoto();
        getArea();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInformation();
        getPhoto();
        getArea();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
    private void getInformation(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<InformationTranslation> call=informationApiService.getInformation();
        call.enqueue(new Callback<InformationTranslation>() {
            @Override
            public void onResponse(@NonNull Call<InformationTranslation> call, @NonNull Response<InformationTranslation> response) {
                InformationTranslation informationTranslation = response.body();
                assert informationTranslation != null;
                InformationTranslation.MyData data=informationTranslation.getData();

                AddressActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(data.getUsername());
                        account.setText(data.getUid());
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<InformationTranslation> call, Throwable t) {

            }
        });
    }
    private void getArea(){
        Retrofit retrofit=HttpClientUtils.getRetrofitWithGsonAdapterConsumption();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<AreaTranslation> call=informationApiService.getArea();
        call.enqueue(new Callback<AreaTranslation>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AreaTranslation> call, @NonNull Response<AreaTranslation> response) {
                AreaTranslation areaTranslation=response.body();
                if (areaTranslation.getData()==null||areaTranslation.getData().getProvinces()==null){
                    area.setText("请添加地址");
                    return;
                }
                String province;
                String city;
                String county;
                people.setText(areaTranslation.getData().getName());
                phone.setText(areaTranslation.getData().getPhone());
                province=areaTranslation.getData().getProvinces();
                city=areaTranslation.getData().getCity();
                county=areaTranslation.getData().getCounty();
                area.setText(province+" "+city+""+county);
                more.setText(areaTranslation.getData().getDetail());
            }

            @Override
            public void onFailure(@NonNull Call<AreaTranslation> call, @NonNull Throwable t) {
                Log.e(TAG, "info：" + t.getMessage() + "," + t.toString());
                Toast.makeText(AddressActivity.this, "error", Toast.LENGTH_SHORT).show();
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
                assert photoTranslation != null;
                PhotoTranslation.MyData photo=photoTranslation.getData();
                System.out.println(photo);
                AddressActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        head1.setImageBitmap(returnBitMap(photo.getPersonalphoto()));
                    }
                });
            }

            @Override
            public void onFailure(Call<PhotoTranslation> call, Throwable t) {
                Log.e(TAG, "info：" + t.getMessage() + "," + t.toString());
                Toast.makeText(AddressActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
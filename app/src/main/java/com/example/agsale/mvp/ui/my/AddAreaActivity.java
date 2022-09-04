package com.example.agsale.mvp.ui.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.InformationApiService;
import com.example.agsale.mvp.model.bean.AreaTranslation;
import com.example.agsale.mvp.model.bean.DivisionModel;
import com.example.agsale.mvp.model.bean.Divisions;
import com.example.agsale.mvp.model.bean.RegisterTranslation;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import top.defaults.view.Division;
import top.defaults.view.DivisionPickerView;

public class AddAreaActivity extends BaseActivity {
    String TAG="tasd";
    @BindView(R.id.area)
    EditText area;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.total)
    TextView total;
    @OnClick(R.id.back)
    void back(){
        finish();
    }
    final List<DivisionModel> divisions = Divisions.get(this);
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.divisionPicker)
    DivisionPickerView divisionPicker;
    @Override
    protected void initBaseData() {

    }

    @Override
    protected void baseConfigView() {
        getArea();
        divisionPicker.setDivisions(divisions);
        divisionPicker.setOnSelectedDateChangedListener(new DivisionPickerView.OnSelectedDivisionChangedListener() {
            @Override
            public void onSelectedDivisionChanged(Division division) {
                System.out.println(division.getParent().getParent().getName() + " " + division.getParent().getName() + " " + division.getName());
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitArea(division);
                    }
                });
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_add_area;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
    private void submitArea(Division division){
        String detail=String.valueOf(area.getText());
        String number=String.valueOf(phone.getText());
        String name2=String.valueOf(name.getText());
        if(detail.equals("")||number.equals("")||name2.equals("")||division.getParent().getName().equals("")){
            Toast.makeText(this, "请完整输入信息", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapterConsumption();
        InformationApiService informationApiService =retrofit.create(InformationApiService.class);
        System.out.println(division.getParent().getParent().getName());
        Call<RegisterTranslation> call=informationApiService.area(division.getParent().getName(),division.getName(),detail,name2,number,division.getParent().getParent().getName());
        call.enqueue(new Callback<RegisterTranslation>() {
            @Override
            public void onResponse(Call<RegisterTranslation> call, Response<RegisterTranslation> response) {
                Log.e( "onResponse: ", String.valueOf(response));
                RegisterTranslation registerTranslation=response.body();
                AddAreaActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("run: ",registerTranslation.getMsg() );
                        Toast.makeText(AddAreaActivity.this, registerTranslation.getMsg(), Toast.LENGTH_SHORT).show();
                        if(registerTranslation.getMsg().equals("更新成功")){
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
    private void getArea(){
        Retrofit retrofit=HttpClientUtils.getRetrofitWithGsonAdapterConsumption();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<AreaTranslation> call=informationApiService.getArea();
        call.enqueue(new Callback<AreaTranslation>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AreaTranslation> call, @NonNull Response<AreaTranslation> response) {
                String province;
                String city;
                String county;
                AreaTranslation areaTranslation=response.body();
                province=areaTranslation.getData().getProvinces();
                city=areaTranslation.getData().getCity();
                county=areaTranslation.getData().getCounty();
                AddAreaActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String mName;
                        String phones;
                        String details;
                        if(province==(null)||city==(null)||county==(null)){
                            total.setText("尚未选择地区");
                        }else{
                            total.setText("您上次选择地区为\n"+province+city+county);
                        }
                        if(areaTranslation.getData().getName()==(null)){
                            mName="";
                        }else{
                            mName=areaTranslation.getData().getName();
                        }
                        name.setText(mName);
                        if(areaTranslation.getData().getPhone()==(null)){
                            phones="";
                        }else{
                            phones=areaTranslation.getData().getPhone();
                        }
                        phone.setText(phones);
                        if(areaTranslation.getData().getDetail()==(null)){
                            details="";
                        }else{
                            details=areaTranslation.getData().getDetail();
                        }
                        area.setText(details);
                    }
                });
            }
            @Override
            public void onFailure(@NonNull Call<AreaTranslation> call, @NonNull Throwable t) {
                Log.e(TAG, "info：" + t.getMessage() + "," + t.toString());
                Toast.makeText(AddAreaActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
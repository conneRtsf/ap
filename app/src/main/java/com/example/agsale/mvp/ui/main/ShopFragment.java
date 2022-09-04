

package com.example.agsale.mvp.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agsale.R;
import com.example.agsale.base.BaseFragment;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.InformationApiService;
import com.example.agsale.mvp.model.api.service.ShopApiService;
import com.example.agsale.mvp.model.bean.AreaTranslation;
import com.example.agsale.mvp.model.bean.Package;
import com.example.agsale.mvp.model.bean.ShopTranslation;
import com.example.agsale.mvp.ui.my.AddAreaActivity;
import com.example.agsale.mvp.ui.packages.PurchaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopFragment extends BaseFragment {
    String myArea=null;
    @OnClick(R.id.more)
    void purchase(){
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(requireActivity());
        normalDialog.setTitle("您当前的配送地址为"+myArea).setMessage("需要更改配送地址吗");
        normalDialog.setPositiveButton("更改地址",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.setClass(requireActivity(), AddAreaActivity.class);
                        startActivity(intent);
                    }
                });
        normalDialog.setNeutralButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNegativeButton("直接购买", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent();
                intent.setClass(requireActivity(), PurchaseActivity.class);
                startActivity(intent);
            }
        });
        // 创建实例并显示
        normalDialog.show();
    }
    @BindView(R.id.ivPicture)
    ImageView ivPicture;
    @BindView(R.id.produce)
    TextView produce;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.item)
    TextView item;
    @BindView(R.id.more)
    Button more;
    @BindView(R.id.p1)
    Button p1;
    @BindView(R.id.p2)
    Button p2;
    @BindView(R.id.p3)
    Button p3;
    @BindView(R.id.p4)
    Button p4;
    @BindView(R.id.p5)
    Button p5;
    @OnClick(R.id.p1)
    void get1(){
        getPackage(0);
        getArea();
        p1.setBackgroundColor(getResources().getColor(R.color.yellow));
        p2.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p3.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p4.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p5.setBackgroundColor(getResources().getColor(R.color.green_bottom));
    }
    @OnClick(R.id.p2)
    void get2(){
        getPackage(1);
        getArea();
        p2.setBackgroundColor(getResources().getColor(R.color.yellow));
        p1.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p3.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p4.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p5.setBackgroundColor(getResources().getColor(R.color.green_bottom));
    }
    @OnClick(R.id.p3)
    void get3(){
        getPackage(2);
        getArea();
        p3.setBackgroundColor(getResources().getColor(R.color.yellow));
        p2.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p1.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p4.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p5.setBackgroundColor(getResources().getColor(R.color.green_bottom));
    }
    @OnClick(R.id.p4)
    void get4(){
        getPackage(3);
        getArea();
        p4.setBackgroundColor(getResources().getColor(R.color.yellow));
        p2.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p3.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p1.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p5.setBackgroundColor(getResources().getColor(R.color.green_bottom));
    }
    @OnClick(R.id.p5)
    void get5(){
        getPackage(4);
        getArea();
        p5.setBackgroundColor(getResources().getColor(R.color.yellow));
        p2.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p3.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p4.setBackgroundColor(getResources().getColor(R.color.green_bottom));
        p1.setBackgroundColor(getResources().getColor(R.color.green_bottom));
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        getArea();
    }

    @Override
    public void onResume() {
        super.onResume();
        getArea();
    }

    @Override
    public void onPause() {
        super.onPause();
        getArea();
    }

    @Override
    protected void initData() {
        getArea();
        getPackage(0);
        p1.setBackgroundColor(getResources().getColor(R.color.yellow));
    }
    private void getPackage(int n){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapterConsumption();
        ShopApiService shopApiService=retrofit.create(ShopApiService.class);
        Call<ShopTranslation> call=shopApiService.getPackage();
        call.enqueue(new Callback<ShopTranslation>() {
            @Override
            public void onResponse(Call<ShopTranslation> call, Response<ShopTranslation> response) {
                ShopTranslation shopTranslation=response.body();
                assert shopTranslation != null;
                List<Package> list=shopTranslation.getData();
                Package a=list.get(n);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        item.setText(a.getType());
                        name.setText(a.getName());
                        ivPicture.setImageBitmap(returnBitMap(a.getPicture().get(0)));
                        produce.setText(a.getDescription());
                        more.setText("购买"+a.getPrice()+"/月");
                    }
                });
            }

            @Override
            public void onFailure(Call<ShopTranslation> call, Throwable t) {
            }
        });
    }
    private void getArea(){
        Retrofit retrofit=HttpClientUtils.getRetrofitWithGsonAdapterConsumption();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<AreaTranslation> call=informationApiService.getAreas();
        call.enqueue(new Callback<AreaTranslation>() {
            @Override
            public void onResponse(@NonNull Call<AreaTranslation> call, @NonNull Response<AreaTranslation> response) {
                AreaTranslation areaTranslation=response.body();
                if (areaTranslation.getData()==null||areaTranslation.getData().getProvinces()==null){
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(requireActivity());
                    normalDialog.setTitle("您尚未设置配送地址");
                    normalDialog.setMessage("需要现在设置吗");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent();
                                    intent.setClass(requireActivity(), AddAreaActivity.class);
                                    startActivity(intent);
                                }
                            });
                    normalDialog.setNegativeButton("关闭",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                }
                            });
                    // 显示
                    normalDialog.show();
                    return;
                }
                myArea=areaTranslation.getData().getProvinces()+areaTranslation.getData().getCity()+areaTranslation.getData().getCounty()+areaTranslation.getData().getDetail();
            }

            @Override
            public void onFailure(@NonNull Call<AreaTranslation> call, Throwable t) {
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.agsale.mvp.model.api.service;

import com.example.agsale.mvp.model.bean.ShopTranslation;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ShopApiService {
    @GET("combo/list/zjl123")
    Call<ShopTranslation> getPackage();
}

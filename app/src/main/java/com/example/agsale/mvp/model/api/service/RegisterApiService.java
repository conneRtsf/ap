package com.example.agsale.mvp.model.api.service;

import com.example.agsale.mvp.model.bean.RegisterTranslation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterApiService {
    @FormUrlEncoded
    @POST("consumer/register")
    Call<RegisterTranslation> register(@Field("username") String username, @Field("password") String password, @Field("vcode") String vcode);
}

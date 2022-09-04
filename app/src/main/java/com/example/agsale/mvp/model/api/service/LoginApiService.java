package com.example.agsale.mvp.model.api.service;

import android.icu.text.IDNA;

import com.example.agsale.mvp.model.bean.RegisterTranslation;
import com.example.agsale.mvp.model.bean.ReqBackData;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginApiService {

        @FormUrlEncoded
        @POST("consumer/vlogin")
        Call<RegisterTranslation> messageLog(@Field("username") String username, @Field("vcode") String vcode);

        @FormUrlEncoded
        @POST("consumer/login")
        Call<RegisterTranslation> passwordLog(@Field("username") String username, @Field("password") String password);

        @GET("consumer/logout")
        Call<RegisterTranslation> exit();
}

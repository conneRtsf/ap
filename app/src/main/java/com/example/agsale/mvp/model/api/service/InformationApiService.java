package com.example.agsale.mvp.model.api.service;

import com.example.agsale.mvp.model.bean.AreaTranslation;
import com.example.agsale.mvp.model.bean.InformationTranslation;
import com.example.agsale.mvp.model.bean.PersonalPhotoTranslation;
import com.example.agsale.mvp.model.bean.PhotoTranslation;
import com.example.agsale.mvp.model.bean.RegisterTranslation;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InformationApiService {
    @GET("consumersurface/queryOne")
    Call<InformationTranslation> getInformation();

    @POST("consumerpersonalphoto/upload")
    Call<PersonalPhotoTranslation> uploadPhoto(@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("consumersurface/updateUsername")
    Call<RegisterTranslation> updataName(@Field("username")String username);

    @GET("consumerpersonalphoto/queryOne")
    Call<PhotoTranslation> getPhoto();

    @FormUrlEncoded
    @POST("address/update")
    Call<RegisterTranslation> area(@Field("city")String city,
                                   @Field("county")String county,
                                   @Field("detail")String detail,
                                   @Field("name")String name,
                                   @Field("phone")String phone,
                                   @Field("provinces")String provinces);

    @GET("address/queryOne")
    Call<AreaTranslation> getArea();
    @GET("address/queryOne")
    Call<AreaTranslation> getAreas();
}

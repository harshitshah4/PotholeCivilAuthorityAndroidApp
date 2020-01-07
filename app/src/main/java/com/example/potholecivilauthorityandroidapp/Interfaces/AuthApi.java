package com.example.potholecivilauthorityandroidapp.Interfaces;


import com.example.potholecivilauthorityandroidapp.Models.CivilAuthority;
import com.example.potholecivilauthorityandroidapp.Models.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("auth/getsignupotp")
    Call<ResponseBody> getSignupOtp(@Body CivilAuthority civilAuthority);

    @POST("auth/signup")
    Call<ResponseBody> signup(@Body CivilAuthority civilAuthority);

    @POST("auth/login")
    Call<ResponseBody> login(@Body CivilAuthority civilAuthority);



}


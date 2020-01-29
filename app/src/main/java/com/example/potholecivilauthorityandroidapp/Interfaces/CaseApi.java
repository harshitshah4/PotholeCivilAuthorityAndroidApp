package com.example.potholecivilauthorityandroidapp.Interfaces;

import com.example.potholecivilauthorityandroidapp.Models.Case;
import com.example.potholecivilauthorityandroidapp.Models.HeatMap;
import com.example.potholecivilauthorityandroidapp.Models.Post;
import com.example.potholecivilauthorityandroidapp.Models.ResponseBody;
import com.example.potholecivilauthorityandroidapp.Models.Signed;
import com.example.potholecivilauthorityandroidapp.Models.Status;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface CaseApi {



    @GET("case/cases")
    Call<List<Case>> getCases(@Query("pageno") int pageno ,@Query("status") String status);

    @GET("case/status")
    Call<List<Status>> getStatus(@Query("cid") String cid);

    @POST("case/resolve")
    Call<ResponseBody> resolveCase(@Body Status status);


    @GET("case/upload")
    Call<Signed> getSignedUpload(@Query("filename") String filename);

    @Multipart
    @POST
    Call<String> uploadMedia(@Url String url, @Part("key") RequestBody key, @Part("Content-Disposition") RequestBody contentDisposition, @Part("Content-Type") RequestBody contentType, @Part("bucket") RequestBody bucket, @Part("X-Amz-Algorithm") RequestBody XAMZAlgorithm, @Part("X-Amz-Credential") RequestBody XAMZCredentials, @Part("X-Amz-Date") RequestBody XAMZDate , @Part("Policy") RequestBody Policy, @Part("X-Amz-Signature")  RequestBody XAMZSignature, @Part MultipartBody.Part file);

    @GET("case/heatmaps")
    Call<List<HeatMap>> getHitmaps(@Query("startDate") long startDate,@Query("endDate") long endDate);
//    @GET("/case/")
//    Call<>
}

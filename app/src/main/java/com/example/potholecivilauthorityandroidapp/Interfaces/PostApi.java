package com.example.potholecivilauthorityandroidapp.Interfaces;


import com.example.potholecivilauthorityandroidapp.Models.Location;
import com.example.potholecivilauthorityandroidapp.Models.Post;
import com.example.potholecivilauthorityandroidapp.Models.ResponseBody;
import com.example.potholecivilauthorityandroidapp.Models.Signed;

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

public interface PostApi {

    @POST("post")
    Call<ResponseBody> addPost(@Body Post post);


    @GET("post")
    Call<Post> getPost(@Query("pid") String pid);

    @GET("post/posts")
    Call<List<Post>> getPosts(@Query("pageno") int pageno);


    @POST("case/merge")
    Call<ResponseBody> mergePost(@Body Post post);
}

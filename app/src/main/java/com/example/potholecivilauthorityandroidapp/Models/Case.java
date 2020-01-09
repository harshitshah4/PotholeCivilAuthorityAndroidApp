package com.example.potholecivilauthorityandroidapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Case {

    @SerializedName("cid")
    String cid;
    @SerializedName("posts")
    List<Post> posts;
    @SerializedName("status")
    Status status;
    @SerializedName("statuses")
    List<Status> statuses;


    public Case(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }


}

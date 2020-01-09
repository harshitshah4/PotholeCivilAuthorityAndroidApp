package com.example.potholecivilauthorityandroidapp.Models;

import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("cid")
    String cid;
    @SerializedName("status")
    String status;
    @SerializedName("timestamp")
    long timestamp;
    @SerializedName("message")
    String message;
    @SerializedName("proof_image")
    String proof_image;

    public Status(String cid, String message , String proof_image){
        this.cid = cid;
        this.message = message;
        this.proof_image = proof_image;


    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProof_image() {
        return proof_image;
    }

    public void setProof_image(String proof_image) {
        this.proof_image = proof_image;
    }
}

package com.example.potholecivilauthorityandroidapp.Models;

import com.google.gson.annotations.SerializedName;

public class HeatMap {

    @SerializedName("lat")
    double lat;
    @SerializedName("lng")
    double lng;

    public HeatMap(double lat, double lon) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}


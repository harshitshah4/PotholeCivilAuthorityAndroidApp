package com.example.potholecivilauthorityandroidapp.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.potholecivilauthorityandroidapp.Activities.HomeActivity;
import com.example.potholecivilauthorityandroidapp.Helpers.NetworkHelper;
import com.example.potholecivilauthorityandroidapp.Interfaces.CaseApi;
import com.example.potholecivilauthorityandroidapp.Models.HeatMap;
import com.example.potholecivilauthorityandroidapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeatMapFragment extends Fragment{

    int filter = 0;


    Context context;

    Spinner heatMapFilterSpinner;

    GoogleMap mMap;

    List<String> list = new ArrayList<>();

    public HeatMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heat_map, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        heatMapFilterSpinner = view.findViewById(R.id.hitmapfilterspinnerid);

        list.add("Today");
        list.add("Yesterday");
        list.add("Last Week");
        list.add("Last Month");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        heatMapFilterSpinner.setAdapter(dataAdapter);

        heatMapFilterSpinner.setSelection(filter);

        heatMapFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter = position;
                getHitmaps();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getHitmaps();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;



            }
        });

    }

    private void getHitmaps(){
        Retrofit retrofit = NetworkHelper.getRetrofitInstance(context);

        CaseApi caseApi = retrofit.create(CaseApi.class);

        Pair<Long,Long> dates = getStartDateEndDate();

        Call<List<HeatMap>> heatMapsCall = caseApi.getHitmaps(dates.first,dates.second);

        heatMapsCall.enqueue(new Callback<List<HeatMap>>() {
            @Override
            public void onResponse(Call<List<HeatMap>> call, Response<List<HeatMap>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    List<HeatMap> heatMapList = response.body();

                    List<LatLng> list = new ArrayList<LatLng>();

                    for(HeatMap heatMap:heatMapList){

                        list.add(new LatLng(heatMap.getLat(),heatMap.getLng()));
                    }

                    if(list.size()>0){
                        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                                .data(list)
                                .build();
                        // Add a tile overlay to the map, using the heat map tile provider.
                        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

                    }

                }else{
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HeatMap>> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Pair<Long,Long> getStartDateEndDate(){

        long startDate;
        long endDate = System.currentTimeMillis();

        switch (filter)
        {
            case 0:
                startDate = endDate - 86400000;
                break;
            case 1:
                startDate = endDate - 172800000;
                break;

            case 2:
                startDate = endDate - 604800000;
                break;

            case 3:
                startDate = endDate - 2592000L;
                break;
            default:
                startDate = 0;
                break;
        }

        return new Pair<>(startDate,endDate);
    }



}

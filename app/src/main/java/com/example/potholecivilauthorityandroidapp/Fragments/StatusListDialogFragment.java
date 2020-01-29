package com.example.potholecivilauthorityandroidapp.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.example.potholecivilauthorityandroidapp.Adapters.StatusListRecyclerAdapter;
import com.example.potholecivilauthorityandroidapp.Helpers.NetworkHelper;
import com.example.potholecivilauthorityandroidapp.Interfaces.CaseApi;
import com.example.potholecivilauthorityandroidapp.Interfaces.PostApi;
import com.example.potholecivilauthorityandroidapp.Models.Status;
import com.example.potholecivilauthorityandroidapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusListDialogFragment extends DialogFragment {

    Context context;

    List<Status> statusList;

    RecyclerView postStatusListRecyclerView;

    String cid;

    StatusListRecyclerAdapter statusListRecyclerAdapter;

    public StatusListDialogFragment() {
        // Required empty public constructor
    }

    public StatusListDialogFragment(String cid) {
        this.cid = cid;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_list_dialog, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        postStatusListRecyclerView = view.findViewById(R.id.casestatuslistrecyclerviewid);
        postStatusListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        postStatusListRecyclerView.setHasFixedSize(true);


        if(cid!=null){
            Retrofit retrofit = NetworkHelper.getRetrofitInstance(context);

            CaseApi caseApi = retrofit.create(CaseApi.class);

            Call<List<Status>> statusCall = caseApi.getStatus(cid);

            statusCall.enqueue(new Callback<List<Status>>() {
                @Override
                public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {

                    if(response.isSuccessful() && response.body()!=null){
                        statusList = response.body();

                        statusListRecyclerAdapter = new StatusListRecyclerAdapter(context,statusList);

                        postStatusListRecyclerView.setAdapter(statusListRecyclerAdapter);

                    }else{
                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<Status>> call, Throwable t) {
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });


        }


    }
}

package com.example.potholecivilauthorityandroidapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.potholecivilauthorityandroidapp.Helpers.NetworkHelper;
import com.example.potholecivilauthorityandroidapp.Interfaces.CaseApi;
import com.example.potholecivilauthorityandroidapp.Models.Case;
import com.example.potholecivilauthorityandroidapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CaseListDialogFragment extends DialogFragment {

    Context context;

    ListView caseListDialogListView;

    List<Case> caseList;

    String removeCid;

    OnCaseListItemSelectedListener onCaseListItemSelectedListener;

    public CaseListDialogFragment(Context context,String removeCid,OnCaseListItemSelectedListener onCaseListItemSelectedListener){
        this.context = context;
        this.removeCid = removeCid;
        this.onCaseListItemSelectedListener = onCaseListItemSelectedListener;


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.case_list_dialog_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        caseListDialogListView = view.findViewById(R.id.dialogcaselistviewid);



        Retrofit retrofit = NetworkHelper.getRetrofitInstance(context);

        CaseApi caseApi = retrofit.create(CaseApi.class);

        Call<List<Case>> casesCall = caseApi.getCases(0,"All");


        casesCall.enqueue(new Callback<List<Case>>() {
            @Override
            public void onResponse(Call<List<Case>> call, Response<List<Case>> response) {
                if(response.isSuccessful()){

                    caseList = response.body();

                    assert caseList != null;

                    caseList.add(new Case("NEW"));
                    String[] cases = new String[caseList.size()-1];

                    int index = 0;
                    for(Case currentCase : caseList){

                        if(!currentCase.getCid().equals(removeCid)){
                            cases[index] = currentCase.getCid();
                            index++;
                        }


                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,cases);

                    caseListDialogListView.setAdapter(arrayAdapter);

                }else{
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Case>> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        caseListDialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onCaseListItemSelectedListener.onSelected(caseList.get(position).getCid());
                dismiss();
            }
        });


    }

    public interface OnCaseListItemSelectedListener{

        void onSelected(String cid);

    }

}

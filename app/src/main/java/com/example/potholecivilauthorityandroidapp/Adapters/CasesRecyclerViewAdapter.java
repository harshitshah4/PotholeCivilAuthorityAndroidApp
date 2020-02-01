package com.example.potholecivilauthorityandroidapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.potholecivilauthorityandroidapp.Activities.HomeActivity;
import com.example.potholecivilauthorityandroidapp.Fragments.CaseListDialogFragment;
import com.example.potholecivilauthorityandroidapp.Fragments.FullScreenImageDialogFragment;
import com.example.potholecivilauthorityandroidapp.Fragments.HomeFragment;
import com.example.potholecivilauthorityandroidapp.Fragments.StatusListDialogFragment;
import com.example.potholecivilauthorityandroidapp.Helpers.MediaUploader;
import com.example.potholecivilauthorityandroidapp.Helpers.NetworkHelper;
import com.example.potholecivilauthorityandroidapp.Interfaces.CaseApi;
import com.example.potholecivilauthorityandroidapp.Models.Case;
import com.example.potholecivilauthorityandroidapp.Models.ResponseBody;
import com.example.potholecivilauthorityandroidapp.Models.Status;
import com.example.potholecivilauthorityandroidapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CasesRecyclerViewAdapter extends RecyclerView.Adapter<CasesRecyclerViewAdapter.CasesRecyclerViewHolder> {



    Context context;
    Fragment fragment;
    List<Case> caseList;

    String resolveMessage = null;
    String resolveCid = null;



    public CasesRecyclerViewAdapter(Context context, Fragment fragment, List<Case> caseList) {
        this.context = context;
        this.caseList = caseList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CasesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.case_card,parent,false);
        return new CasesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CasesRecyclerViewHolder holder, int position) {

        final Case currentCase = caseList.get(position);

        holder.caseIdTextView.setText(currentCase.getCid());

        if(currentCase.getStatus()!=null){

            if(currentCase.getStatus().getStatus()!=null){
                if(currentCase.getStatus().getStatus().equals("Assigned")){
                    holder.caseResolveButton.setEnabled(true);
                    holder.caseResolveButton.setVisibility(View.VISIBLE);

                }else{
                    holder.caseResolveButton.setEnabled(false);
                    holder.caseResolveButton.setVisibility(View.GONE);
                }

                if(currentCase.getStatus().getStatus().equals("Resolved")){
                    holder.caseViewProofButton.setEnabled(true);
                    holder.caseViewProofButton.setVisibility(View.VISIBLE);

                }else{
                    holder.caseViewProofButton.setEnabled(false);
                    holder.caseViewProofButton.setVisibility(View.GONE);
                }

                switch (currentCase.getStatus().getStatus()){
                    case "Processing":
                        holder.caseStatusTestView.setTextColor(context.getResources().getColor(R.color.processing));
                        holder.caseStatusTestView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_unchecked_black_24dp,0,0,0);
                        break;
                    case "Resolved":
                        holder.caseStatusTestView.setTextColor(context.getResources().getColor(R.color.resolved));
                        holder.caseStatusTestView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp,0,0,0);
                        break;
                    case "Assigned":
                        holder.caseStatusTestView.setTextColor(context.getResources().getColor(R.color.assigned));
                        holder.caseStatusTestView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_outline_black_24dp,0,0,0);
                        break;
                    case "Rejected":
                        holder.caseStatusTestView.setTextColor(context.getResources().getColor(R.color.rejected));
                        holder.caseStatusTestView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info_outline_black_24dp,0,0,0);
                        break;

                }



                holder.caseStatusTestView.setText(currentCase.getStatus().getStatus());
            }

            if(currentCase.getStatus().getMessage()!=null){
                holder.caseStatusMessageTextView.setVisibility(View.VISIBLE);
                holder.caseStatusMessageTextView.setText(currentCase.getStatus().getMessage());
            }else{
                holder.caseStatusMessageTextView.setVisibility(View.GONE);
            }

            if(currentCase.getStatus().getTimestamp()>0){
                holder.caseStatusTimeStampTextView.setVisibility(View.VISIBLE);
                holder.caseStatusTimeStampTextView.setText(String.valueOf(currentCase.getStatus().getTimestamp()));
            }else{
                holder.caseStatusTimeStampTextView.setVisibility(View.GONE);
            }
        }else{
            holder.caseResolveButton.setEnabled(false);
            holder.caseResolveButton.setVisibility(View.GONE);
        }


        holder.caseViewProofButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();

                FullScreenImageDialogFragment fullScreenImageDialogFragment = new FullScreenImageDialogFragment(currentCase.getStatus().getProof_image());

                fullScreenImageDialogFragment.show(fragmentManager,"imagedialog");
            }
        });

        holder.caseResolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentCase.getStatus()!=null && currentCase.getStatus().getStatus().equals("Assigned")){

                    resolveCid = currentCase.getCid();

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    fragment.startActivityForResult(intent,0);



                }else{
                    Toast.makeText(context, "Cannot Resolve Case", Toast.LENGTH_SHORT).show();
                }


            }
        });

        holder.caseViewStatusesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();

                StatusListDialogFragment caseListDialogFragment = new StatusListDialogFragment(currentCase.getCid());

                caseListDialogFragment.show(fragmentManager,"statuslistdialog");
            }
        });


        holder.postsRecyclerView.setAdapter(new PostsRecyclerViewAdapter(context,currentCase,currentCase.getPosts()));


    }

    @Override
    public int getItemCount() {
        return caseList.size();
    }

    public void resolveCase(Bitmap bitmap){
        MediaUploader.uploadMedia(context, bitmap, new MediaUploader.MediaUploaderListener() {
            @Override
            public void onSuccess(String image) {
                Retrofit retrofit = NetworkHelper.getRetrofitInstance(context);
                CaseApi caseApi = retrofit.create(CaseApi.class);
                Call<ResponseBody> resolveCall = caseApi.resolveCase(new Status(resolveCid,resolveMessage,image));

                resolveCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){

                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }


    class CasesRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView caseIdTextView;
        Button caseResolveButton;

        RecyclerView postsRecyclerView;

        TextView caseStatusTestView;
        TextView caseStatusMessageTextView;
        TextView caseStatusTimeStampTextView;

        ImageButton caseViewProofButton;

        Button caseViewStatusesButton;



        public CasesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            caseResolveButton = itemView.findViewById(R.id.caseresolvebuttonid);

            caseIdTextView = itemView.findViewById(R.id.casecaseidtextviewid);

            postsRecyclerView = itemView.findViewById(R.id.casepostsrecyclerviewid);

            caseStatusTestView = itemView.findViewById(R.id.casestatustextviewid);
            caseStatusMessageTextView = itemView.findViewById(R.id.casestatusmessagetextviewid);
            caseStatusTimeStampTextView = itemView.findViewById(R.id.casestatustimestamptextviewid);

            caseViewProofButton = itemView.findViewById(R.id.caseviewproofbuttonid);

            caseViewStatusesButton = itemView.findViewById(R.id.caseviewstatusesbuttonid);

            postsRecyclerView.setHasFixedSize(true);
            postsRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));


        }
    }



}

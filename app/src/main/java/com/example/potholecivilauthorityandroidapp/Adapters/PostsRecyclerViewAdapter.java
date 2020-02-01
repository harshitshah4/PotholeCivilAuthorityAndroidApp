package com.example.potholecivilauthorityandroidapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.potholecivilauthorityandroidapp.Activities.HomeActivity;
import com.example.potholecivilauthorityandroidapp.Fragments.CaseListDialogFragment;
import com.example.potholecivilauthorityandroidapp.Helpers.NetworkHelper;
import com.example.potholecivilauthorityandroidapp.Interfaces.PostApi;
import com.example.potholecivilauthorityandroidapp.Models.Case;
import com.example.potholecivilauthorityandroidapp.Models.Post;
import com.example.potholecivilauthorityandroidapp.Models.ResponseBody;
import com.example.potholecivilauthorityandroidapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.PostsRecyclerViewHolder> {

    Context context;
    Case parentCase;
    List<Post> postList;

    String mergePid = null;

    CaseListDialogFragment.OnCaseListItemSelectedListener onCaseListItemSelectedListener;

    public PostsRecyclerViewAdapter(final Context context,Case parentCase ,List<Post> postList) {
        this.context = context;
        this.parentCase = parentCase;
        this.postList = postList;


        this.onCaseListItemSelectedListener = new CaseListDialogFragment.OnCaseListItemSelectedListener() {
            @Override
            public void onSelected(String cid) {
                Retrofit retrofit = NetworkHelper.getRetrofitInstance(context);
                PostApi postApi = retrofit.create(PostApi.class);

                Call<ResponseBody> mergeCall = postApi.mergePost(new Post(mergePid,cid));

                mergeCall.enqueue(new Callback<ResponseBody>() {
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
        };
    }

    @NonNull
    @Override
    public PostsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_card,parent,false);
        return new PostsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsRecyclerViewHolder holder, int position) {

        final Post post = postList.get(position);

        holder.postMergeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mergePid = post.getPid();

                FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();

                CaseListDialogFragment caseListDialogFragment = new CaseListDialogFragment(context,parentCase.getCid(),onCaseListItemSelectedListener);

                caseListDialogFragment.show(fragmentManager,"caselistdialog");

            }
        });

        if(post.getText()!=null){
            holder.postTextView.setText(post.getText());
        }

        if(post.getLocation()!=null && post.getLocation().getLongitude()>0 && post.getLocation().getLatitude()>0){
            holder.postLocationButton.setEnabled(true);
            holder.postLocationButton.setVisibility(View.VISIBLE);
        }else{
            holder.postLocationButton.setEnabled(false);
            holder.postLocationButton.setVisibility(View.GONE);
        }


        if(post.getLocation().getDescription()!=null){
            holder.postLocationTextView.setText(post.getLocation().getDescription());
        }


        holder.postLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(post.getLocation()!=null && post.getLocation().getLongitude()>0 && post.getLocation().getLatitude()>0){
                    Uri gmmUri = Uri.parse("google.navigation:q="+post.getLocation().getLatitude()+","+post.getLocation().getLongitude());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if(mapIntent.resolveActivity(context.getPackageManager())!=null){
                        context.startActivity(mapIntent);
                    }else{
                        Toast.makeText(context, "Suitable Apps Not Found", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });



        if(post.getImage() != null){
            Glide.with(context).load(post.getImage()).into(holder.postImageView);

        }
    }



    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostsRecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView postImageView;
        TextView postTextView;

        ImageButton postLocationButton;
        TextView postLocationTextView;

        Button postMergeButton;

        PostsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            postImageView = itemView.findViewById(R.id.postimageviewid);
            postTextView = itemView.findViewById(R.id.posttextviewid);

            postLocationButton = itemView.findViewById(R.id.postlocationbuttonid);
            postLocationTextView = itemView.findViewById(R.id.postlocationtextviewid);

            postMergeButton = itemView.findViewById(R.id.postmergebuttonid);
        }
    }

}

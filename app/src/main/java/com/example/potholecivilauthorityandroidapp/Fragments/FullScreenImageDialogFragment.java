package com.example.potholecivilauthorityandroidapp.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.potholecivilauthorityandroidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullScreenImageDialogFragment extends DialogFragment {

    Context context;


    ProgressBar fullScreenImageProgressBar;
    ImageView fullScreenImageView;

    String url;

    public FullScreenImageDialogFragment() {
        // Required empty public constructor
    }

    public FullScreenImageDialogFragment(String url){

        this.url = url;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_screen_image_dialog, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        fullScreenImageProgressBar = view.findViewById(R.id.fullscreenimageprogressbarid);

        fullScreenImageView = view.findViewById(R.id.fullscreenimageviewid);

        fullScreenImageProgressBar.setVisibility(View.VISIBLE);

        if(url!=null){
            Glide.with(context).load(url).into(fullScreenImageView);
            fullScreenImageProgressBar.setVisibility(View.GONE);
        }
    }
}

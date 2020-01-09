package com.example.potholecivilauthorityandroidapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.potholecivilauthorityandroidapp.Fragments.RegisterFragment;
import com.example.potholecivilauthorityandroidapp.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        replaceFragment(new RegisterFragment());
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.signupframelayoutid,fragment);
        fragmentTransaction.commit();


    }
}

package com.example.potholecivilauthorityandroidapp.Activities;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.potholecivilauthorityandroidapp.Fragments.HeatMapFragment;
import com.example.potholecivilauthorityandroidapp.Fragments.HomeFragment;
import com.example.potholecivilauthorityandroidapp.Fragments.ProfileFragment;
import com.example.potholecivilauthorityandroidapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    int permission_request_code = 1;

    BottomNavigationView homeBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("PREFERENCES",MODE_PRIVATE);

        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);

        if(!isLoggedIn){
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }







        homeBottomNavigationView  = findViewById(R.id.homebottomnavigationviewid);





        homeBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.homemenuid:
                        replaceFragment(new HomeFragment());
                        return true;
                    case R.id.heatmapmenuid:
                        replaceFragment(new HeatMapFragment());
                        return true;
                    case R.id.accountmenuid:
                        replaceFragment(new ProfileFragment());
                        return true;
                    default:
                        return false;
                }

            }
        });

        homeBottomNavigationView.setSelectedItemId(R.id.homemenuid);
    }




    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.homefragmentcontainerid,fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == permission_request_code){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                sharedPreferences.edit().putBoolean("allowDetectingPotholeAccelerometer",true).apply();

            }else{
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

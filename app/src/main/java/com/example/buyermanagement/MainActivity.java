package com.example.buyermanagement;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
//                if(id == R.id.action_goto_products){
//                    Toast.makeText(MainActivity.this, "GOTO Products", Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                    NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
//                    navController.navigate(R.id.action_);
//                    //getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProductCategoryFrag()).commit();
//                }
//                if(id == R.id.action_upi_share){
//                    Toast.makeText(MainActivity.this, "share upi details", Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                if(id == R.id.action_online_share){
//                    Toast.makeText(MainActivity.this, "share online bank details", Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                if(id == R.id.action_sign_out){
//                    Toast.makeText(MainActivity.this, "sign out", Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
                return true;
            }
        });


        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
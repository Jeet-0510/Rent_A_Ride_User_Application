package com.example.vehicleapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;

public class HomeAdmin extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    MeowBottomNavigation bottomNavigation;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView txtProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String mobileNumber = sharedPreferences.getString("mobileNumber", "");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        }

        drawerLayout = findViewById(R.id.drawerLayoutAdmin);
        navigationView = findViewById(R.id.navigationViewAdmin);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigation = findViewById(R.id.bottomNavigationAdmin);

        View headerView = navigationView.getHeaderView(0);
        txtProfile = headerView.findViewById(R.id.txtProfileUser);
        txtProfile.setText("Admin");

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.bicycle));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.bike));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.scooter));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.caricon));

        bottomNavigation.setOnClickMenuListener(model -> {
            // YOUR CODES
            switch (model.getId()) {
                case 1:
                    loadFrame(new BicycleAdmin(), false);
                    Toast.makeText(this, "Bicycle", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    loadFrame(new BikeAdmin(), false);
                    Toast.makeText(this, "Bike", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    loadFrame(new ScooterAdmin(), false);
                    Toast.makeText(this, "Scooter", Toast.LENGTH_SHORT).show();
                    break;

                case 4:
                    loadFrame(new CarAdmin(), false);
                    Toast.makeText(this, "Car", Toast.LENGTH_SHORT).show();
                    break;
            }
            return null;
        });

        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.itmOfferAdmin) {
//                bottomNavigation.show(2, true);
//                loadFrame(new BookingsFragmentUser(), false);
                Intent intent = new Intent(HomeAdmin.this,OfferAAdmin.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.itmLogOutAdmin) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("check", "");
                editor.putString("mobileNumber", "");
                editor.apply();

                Intent intent = new Intent(HomeAdmin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return false;
        });
    }

    void loadFrame(Fragment FragmentUser, Boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (flag) {
            ft.add(R.id.frameLayoutAdmin, FragmentUser);
        } else {
            ft.replace(R.id.frameLayoutAdmin, FragmentUser);
        }
        ft.commit();
    }
}
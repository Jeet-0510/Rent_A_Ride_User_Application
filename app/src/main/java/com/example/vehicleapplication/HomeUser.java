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

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;

public class HomeUser extends AppCompatActivity {

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
        setContentView(R.layout.activity_home_user);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String mobileNumber = sharedPreferences.getString("mobileNumber", "");

        //White Background
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        }

        drawerLayout = findViewById(R.id.drawerLayoutUser);
        navigationView = findViewById(R.id.navigationViewUser);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigation = findViewById(R.id.bottomNavigationUser);

        View headerView = navigationView.getHeaderView(0);
        txtProfile = headerView.findViewById(R.id.txtProfileUser);
        txtProfile.setText(mobileNumber);

        headerView.setOnClickListener(v -> {
            loadFrame(new ProfileFragmentUser(), false);
            bottomNavigation.show(4, true);
            drawerLayout.closeDrawer(GravityCompat.START);
            //Toast.makeText(HomeUser.this, "Header clicked", Toast.LENGTH_SHORT).show();
        });

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.bookingsbn));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.offer));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.profilebn));

        bottomNavigation.show(1, true);
        loadFrame(new HomeFragmentUser(), true);

        bottomNavigation.setOnClickMenuListener(model -> {
            // YOUR CODES
            switch (model.getId()) {
                case 1:
                    loadFrame(new HomeFragmentUser(), false);
                    break;

                case 2:
                    loadFrame(new BookingsFragmentUser(), false);
                    break;

                case 3:
                    loadFrame(new OffersFragmentUser(), false);
                    break;

                case 4:
                    loadFrame(new ProfileFragmentUser(), false);
                    break;
            }
            return null;
        });

//        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//                // YOUR CODES
//                switch (model.getId()){
//                    case 1:
//                        break;
//                }
//                return null;
//            }
//        });
//
//        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//                // YOUR CODES
//                switch (model.getId()){
//                    case 2:
//                        break;
//                }
//                return null;
//            }
//        });
//
//        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//                // YOUR CODES
//                switch (model.getId()){
//                    case 3:
//                        break;
//                }
//                return null;
//            }
//        });
//
//        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//                // YOUR CODES
//                switch (model.getId()){
//                    case 4:
//                        break;
//                }
//                return null;
//            }
//        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.itmBookingUser) {
                bottomNavigation.show(2, true);
                loadFrame(new BookingsFragmentUser(), false);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.itmLogOutUser) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("check", "");
                editor.putString("mobileNumber", "");
                editor.apply();

                Intent intent = new Intent(HomeUser.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else if (item.getItemId() == R.id.itmOfferUser){
                bottomNavigation.show(3,true);
                loadFrame(new OffersFragmentUser(),false);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if (item.getItemId() == R.id.itmServicesUser){
                Intent intent = new Intent(HomeUser.this, ServicesUser.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(item.getItemId() == R.id.itmKYCUser){
                Intent intent = new Intent(HomeUser.this, KYCUser.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.itmAboutUsUser) {
                Intent intent = new Intent(HomeUser.this, AboutUsUser.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return false;
        });
    }

    void loadFrame(Fragment FragmentUser, Boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (flag) {
            ft.add(R.id.frameLayoutUser, FragmentUser);
        } else {
            ft.replace(R.id.frameLayoutUser, FragmentUser);
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
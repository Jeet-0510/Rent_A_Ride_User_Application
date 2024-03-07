package com.example.vehicleapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    ViewPagerAdapter viewPagerAdapter;
    ViewPager mslideview;
    LinearLayout mdotlayout;
    Button skipbtn, Login;
    TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!check()) {

            skipbtn = findViewById(R.id.skip);
            Login = findViewById(R.id.login);

            skipbtn.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                //Intent intent = new Intent(MainActivity.this, HomeUser.class);
                startActivity(intent);
            });

            Login.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //Intent intent = new Intent(MainActivity.this, HomeUser.class);
                startActivity(intent);
            });

            mdotlayout = findViewById(R.id.indicator_layout);
            mslideview = findViewById(R.id.slidepage);

            viewPagerAdapter = new ViewPagerAdapter(this);

            mslideview.setAdapter(viewPagerAdapter);
            setupindicator(0);
            mslideview.addOnPageChangeListener(viewlisner);
        }
    }

    private boolean check() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("check", "");
        if (check.equals("trueU")) {
            Intent intent = new Intent(MainActivity.this, HomeUser.class);
            startActivity(intent);
            finish();
            return true;
        } else if (check.equals("trueA")) {
            Intent intent = new Intent(MainActivity.this, HomeAdmin.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    public void setupindicator(int position) {
        dots = new TextView[4];
        mdotlayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.black, getApplication().getTheme()));
            mdotlayout.addView(dots[i]);
            //Login.setVisibility(View.VISIBLE);

        }
        //  Login.setVisibility(View.VISIBLE);

        dots[position].setTextColor(getResources().getColor(R.color.white, getApplication().getTheme()));
    }

    ViewPager.OnPageChangeListener viewlisner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if (position == 3) {
                //Login.setVisibility(View.VISIBLE);
                Login.setEnabled(true);
                Login.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Login.setTextColor(Color.parseColor("#000000"));

                // skipbtn.setVisibility(View.GONE);
                // skipbtn.setEnabled(false);
                //skipbtn.setBackgroundColor(Color.parseColor("#FFC300"));
                skipbtn.setTextColor(Color.parseColor("#FDCA00"));

            } else {
                Login.setEnabled(false);
                Login.setBackgroundColor(Color.parseColor("#FDCA00"));
                Login.setTextColor(Color.parseColor("#FDCA00"));

                skipbtn.setEnabled(true);
                //skipbtn.setBackgroundColor(Color.parseColor("#FDCA00"));
                skipbtn.setTextColor(Color.parseColor("#000000"));

            }

        }

        @Override
        public void onPageSelected(int position) {
            setupindicator(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

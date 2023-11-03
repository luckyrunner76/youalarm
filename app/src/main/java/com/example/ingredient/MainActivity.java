package com.example.ingredient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        // ViewPager 어댑터 설정
        VPAdapter pagerAdapter = new VPAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // TabLayout과 ViewPager 연동
        tabLayout.setupWithViewPager(viewPager);
    }
}
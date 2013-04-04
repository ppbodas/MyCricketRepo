package com.example.cricket;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class ViewPagerActivity extends FragmentActivity {
    private MyAdapter mAdapter;
    private ViewPager mPager;
    private static String mInningId = new String();
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mInningId = getIntent().getStringExtra("InningId");
        mAdapter = new MyAdapter(getSupportFragmentManager());
 
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
    }
 
    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @Override
        public int getCount() {
            return 2;
        }
 
        @Override
        public Fragment getItem(int position) {
            switch (position) {
            case 0:
                return new FullScorecardBattingFragment(mInningId);
            case 1:
                return new FullScorecardBowlingFragment(mInningId);
            
 
            default:
                return null;
            }
        }
    }
}

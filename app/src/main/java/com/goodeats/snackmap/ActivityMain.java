package com.goodeats.snackmap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.goodeats.snackmap.objects.Business;
import com.goodeats.snackmap.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity implements FragmentMap.MapsInterface {

    private AppCompatActivity activity;
    private int lastConfig = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initialize, would be redrawn on rotate
        if (getSupportFragmentManager().getBackStackEntryCount() < 1) {
            FragmentMap fragmentMap = new FragmentMap();
            FragmentTransaction ft = getFragmentTransaction();
            ft.add(R.id.container, fragmentMap, FragmentMap.TAG);
            ft.commit();
            transitionToolbar(AppUtils.getCurrentFragment(activity));
        }
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                transitionToolbar(AppUtils.getCurrentFragment(activity));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final View rootView = getWindow().getDecorView().getRootView();
        final int config = getResources().getConfiguration().orientation;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (config != lastConfig) {
                    transitionToolbar(AppUtils.getCurrentFragment(activity));
                    lastConfig = config;
                }
            }
        });
        if (config != lastConfig) {
            transitionToolbar(AppUtils.getCurrentFragment(activity));
            lastConfig = config;
        }
    }

    @Override
    //open location fragment
    public void onOpenBusiness(Business business) {
        FragmentLocation fragmentLocation = new FragmentLocation();
        fragmentLocation.setBusiness(business);
        FragmentTransaction ft = getFragmentTransaction();
        ft.add(R.id.container, fragmentLocation, FragmentLocation.TAG);
        ft.addToBackStack(FragmentLocation.TAG);
        ft.commit();
        transitionToolbar(FragmentLocation.TAG);
    }

    @Override
    //open location list fragment
    public void onOpenGrid() {
        FragmentMapLocations fragmentMapLocations = new FragmentMapLocations();
        FragmentTransaction ft = getFragmentTransaction();
        ft.add(R.id.container, fragmentMapLocations, FragmentMapLocations.TAG);
        ft.addToBackStack(FragmentMapLocations.TAG);
        ft.commit();
        transitionToolbar(FragmentMapLocations.TAG);
    }

    //transitions the toolbar
    public void transitionToolbar(String tag){
        ActionBar actionBar = getSupportActionBar();
        FragmentManager fm = getSupportFragmentManager();
        if (actionBar != null) {
            switch (tag) {
                case FragmentMap.TAG:
                    toggleHomeButton(false);
                    actionBar.setTitle(R.string.title_map);
                    break;
                case FragmentLocation.TAG:
                    toggleHomeButton(true);
                    FragmentLocation fragmentLocation = (FragmentLocation)fm.findFragmentByTag(FragmentLocation.TAG);
                    if (fragmentLocation != null && fragmentLocation.getBusiness() != null)
                        actionBar.setTitle(fragmentLocation.getBusiness().getName());
                    else
                        actionBar.setTitle(R.string.title_location);
                    break;
                case FragmentMapLocations.TAG:
                    toggleHomeButton(true);
                    actionBar.setTitle(R.string.title_locations);
                    break;
            }
        }
    }

    public void toggleHomeButton(boolean show){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(show);
            actionBar.setDisplayHomeAsUpEnabled(show);
            actionBar.setDisplayShowHomeEnabled(show);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    //default fragment transaction for the activity
    public FragmentTransaction getFragmentTransaction() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        return ft;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        int backStackCount = fm.getBackStackEntryCount();
        if (backStackCount > 0)
            fm.popBackStack();
        else if (backStackCount == 0)
            super.onBackPressed();
        fm.executePendingTransactions();
    }
}

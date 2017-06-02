package com.goodeats.snackmap;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodeats.snackmap.objects.Business;
import com.goodeats.snackmap.utils.AppUtils;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by Tyler on 6/1/2017.
 */

public class FragmentLocation extends Fragment  {
    public static final String TAG = "FragmentLocation";
    private Business business;
    private TextView txtName, txtAddress, txtPhone, txtHours;

    public FragmentLocation() {
        //empty constructor
    }

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (business != null)
            outState.putParcelable(Business.TAG, business);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        if (savedInstanceState != null && savedInstanceState.containsKey(Business.TAG))
            setBusiness((Business)savedInstanceState.getParcelable(Business.TAG));
        //exit view if there is no business
        if (business == null) {
            getActivity().getSupportFragmentManager().popBackStack();
            return rootView;
        }
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(business.getName());
        txtName = (TextView) rootView.findViewById(R.id.txtName);
        txtAddress = (TextView) rootView.findViewById(R.id.txtAddress);
        txtPhone = (TextView) rootView.findViewById(R.id.txtPhone);
        txtHours = (TextView) rootView.findViewById(R.id.txtHours);
        txtName.setText(business.getName());
        txtAddress.setText(business.getAddress());
        txtPhone.setText(business.getPhone());
        txtHours.setText(business.getHours());

        txtAddress.setPaintFlags(txtAddress.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txtAddress.setTextColor(getResources().getColor(R.color.colorAccent));
        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.startNavigationIntent(getActivity(), business.getLatitude(), business.getLongitude());
            }
        });
        return rootView;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent calendarEditEvent in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
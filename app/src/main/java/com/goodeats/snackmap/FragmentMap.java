package com.goodeats.snackmap;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodeats.snackmap.objects.Business;
import com.goodeats.snackmap.utils.AppUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Tyler on 6/1/2017.
 */

public class FragmentMap extends Fragment  implements OnMapReadyCallback {
    public static final String TAG = "FragmentMap";
    public static final String TAG_MAP = "FragmentInnerMap";

    private FloatingActionButton btnGrid;
    private GoogleMap mMap;

    private MapsInterface mapsInterface;

    public FragmentMap() {
        //empty constructor
    }

    //interface for when a workout is selected
    public interface MapsInterface {
        void onOpenBusiness(Business business);
        void onOpenGrid();
    }

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mapsInterface = (MapsInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement MapsInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        // add the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = new SupportMapFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.containerMaps, mapFragment, FragmentMap.TAG);
        ft.commit();
        mapFragment.getMapAsync(this);
        btnGrid = (FloatingActionButton) rootView.findViewById(R.id.btnAction);
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapsInterface.onOpenGrid();
            }
        });
        return rootView;
    }

    //interface for the Google Maps library
    //when the map has been loaded
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final ArrayList<Business> businesses = AppUtils.generateBusinesses();
        for (int a=0; a < businesses.size(); a++) {
            Business business = businesses.get(a);
            String name;
            if (business.getName() != null)
                name = business.getName();
            else
                name = getResources().getString(R.string.empty_name);
            mMap.addMarker(new MarkerOptions().position(business.getCoordinates()).title(Integer.toString(a) + ":" + name));
        }
        // Add a marker and move the camera
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(business));
        Business mainBusiness = businesses.get(0);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mainBusiness.getCoordinates(), 10.0f));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                int index = Integer.parseInt(title.split(":")[0]);
                mapsInterface.onOpenBusiness(businesses.get(index));
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

}
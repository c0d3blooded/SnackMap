package com.goodeats.snackmap.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.goodeats.snackmap.FragmentMap;
import com.goodeats.snackmap.objects.Business;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Tyler on 6/1/2017.
 */

public class AppUtils {

    private static double[][] coords = {
            {41.414344, -73.381465},    //1
            {41.481962, -73.409755},    //2
            {41.379423, -73.425490},    //3
            {41.525631, -73.480653},    //4
            {41.368672, -73.272847},    //5
            {41.478551, -73.214950},    //6
            {41.335143, -73.263175},    //7
            {41.323617, -73.474880},    //8
            {41.555708, -73.416378},    //9
            {41.397143, -73.454690}     //10
    };


    private static String[] addresses = {
            "75 Stony Hill Rd, Bethel, CT 06801",               //1
            "814 Federal Rd, Brookfield, CT 06804",             //2
            "14 Grassy Plain St, Bethel, CT 06801",             //3
            "28 Route 39, Suite 11, New Fairfield, CT 06810",   //4
            "266 S Main St, Newtown, CT 06470",                 //5
            "14 Oak Tree Rd, Southbury, CT 06488",              //6
            "640 Main St, Monroe, CT 06468",                    //7
            "632 Danbury Rd, Ridgefield, CT 06877",             //8
            "139 Danbury Rd, New Milford, CT 06776",            //9
            "317 Main St, Danbury, CT 06810"                    //10
    };

    private static String[] times = {
            "8AM - 10PM",    //1
            "8AM - 9PM",    //2
            "10AM - 9PM",    //3
            "10AM - 10PM",    //4
            "9AM - 8PM",    //5
            "7AM - 9PM",    //6
            "9AM - 10PM",    //7
            "10AM - 9PM",    //8
            "9AM - 10PM",    //9
            "8AM - 10PM"     //10
    };


    private static String[] numbers = {
            "(203) 748-0515",    //1
            "(203) 740-2066",    //2
            "(203) 791-0676",    //3
            "(203) 746-3270",    //4
            "(203) 270-2222",    //5
            "(203) 267-7929",    //6
            "(203) 268-2702",    //7
            "(203) 894-1000",    //8
            "(860) 355-0053",    //9
            "(203) 748-4000"     //10
    };

    public static ArrayList<Business> generateBusinesses(){
        ArrayList<Business> businesses = new ArrayList<>();
        int businessCount = 10;
        for (int a=0; a < businessCount; a++){
            Business business = new Business("Sandwich Shop " + a, coords[a][0], coords[a][1]);
            business.setAddress(addresses[a]);
            business.setPhone(numbers[a]);
            business.setHours(times[a]);
            businesses.add(business);
        }
        return businesses;
    }

    //returns the tag of the current fragment in the main activity
    public static String getCurrentFragment(AppCompatActivity activity){
        FragmentManager fm = activity.getSupportFragmentManager();
        int backStackCount = fm.getBackStackEntryCount();
        if(backStackCount > 0)
            return fm.getBackStackEntryAt(backStackCount - 1).getName();
        else
            return FragmentMap.TAG;
    }

    //creates a nav intent to google maps
    public static void startNavigationIntent(Activity activity, double latitude, double longitude){
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(mapIntent);
        }
    }
}

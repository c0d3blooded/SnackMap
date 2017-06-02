package com.goodeats.snackmap.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Tyler on 6/1/2017.
 */

public class Business implements Parcelable {
    public static String TAG = "business";

    private String name, address, phone, hours;
    private double longitude, latitude;

    //constructor requires name and coordinates
    public Business(String name, double lat, double lon) {
        setName(name);
        setCoordinates(lat, lon);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHours(long start, long end){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getCoordinates(){
        return new LatLng(latitude, longitude);
    }

    //set the coordinates
    public void setCoordinates(double lat, double lon){
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.hours);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
    }

    protected Business(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.hours = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
    }

    public static final Creator<Business> CREATOR = new Creator<Business>() {
        @Override
        public Business createFromParcel(Parcel source) {
            return new Business(source);
        }

        @Override
        public Business[] newArray(int size) {
            return new Business[size];
        }
    };
}

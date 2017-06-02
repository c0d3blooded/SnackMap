package com.goodeats.snackmap.objects.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodeats.snackmap.FragmentMap;
import com.goodeats.snackmap.R;
import com.goodeats.snackmap.objects.Business;
import com.goodeats.snackmap.utils.AppUtils;

import java.util.ArrayList;

/**
 * Created by Tyler on 6/1/2017.
 */


public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {
    public static final String TAG = "BusinessAdapter";

    private Activity activity;
    private FragmentMap.MapsInterface mapsInterface;
    private ArrayList<Business> dataList;

    //used for Store items the user has access to
    public BusinessAdapter(Activity c){
        activity = c;
        dataList = AppUtils.generateBusinesses();
        try {
            mapsInterface = (FragmentMap.MapsInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MapsInterface");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_business, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final Business business = dataList.get(viewHolder.getLayoutPosition());
        viewHolder.txtName.setText(business.getName());
        viewHolder.txtAddress.setText(business.getAddress());
        viewHolder.txtPhone.setText(business.getPhone());
        viewHolder.txtHours.setText(business.getHours());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mapsInterface.onOpenBusiness(business);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtAddress, txtPhone, txtHours;

        public ViewHolder(View itemLayoutView){
            super(itemLayoutView);
            txtName = (TextView) itemLayoutView.findViewById(R.id.txtName);
            txtAddress = (TextView) itemLayoutView.findViewById(R.id.txtAddress);
            txtPhone = (TextView) itemLayoutView.findViewById(R.id.txtPhone);
            txtHours = (TextView) itemLayoutView.findViewById(R.id.txtHours);
        }
    }
}






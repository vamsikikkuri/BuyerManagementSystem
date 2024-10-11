package com.example.buyermanagement;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BuyerListAdapter extends RecyclerView.Adapter<BuyerListAdapter.BuyerListViewHolder> {

    public static final String BUYER_ID_IN_LIST = "BUYER_ID_IN_LIST";
    public static final String BUYER_NAME_IN_LIST = "BUYER_NAME_IN_LIST";
    private ArrayList<BuyerClass> mBuyers = new ArrayList<>();
    Context mContext;
    NavController navControllerToDetails;

    BuyerListAdapter(ArrayList<BuyerClass> buyers, NavController navController, Context context) {
        mBuyers = buyers;
        mContext = context;
        navControllerToDetails = navController;
    }

    @NonNull
    @Override
    public BuyerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyers_list_card, parent, false);
        return new BuyerListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerListViewHolder holder, final int position) {
        holder.buyerNameView.setText(mBuyers.get(position).getBuyerName());
        holder.buyerMobView.setText(mBuyers.get(position).getBuyerMobNo());
        holder.buyerAddressView.setText(mBuyers.get(position).getBuyerAddress());
        holder.buyerNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle argToBuyerDetails = new Bundle();
                argToBuyerDetails.putString(BUYER_ID_IN_LIST, mBuyers.get(position).getBuyerId());
                argToBuyerDetails.putString(BUYER_NAME_IN_LIST, mBuyers.get(position).getBuyerName());
                navControllerToDetails.navigate(R.id.action_buyersList_to_buyerDetailsFrag, argToBuyerDetails);
            }
        });

        holder.buyerMobView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + mBuyers.get(position).getBuyerMobNo()));
                mContext.startActivity(call);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, mBuyers.get(position).getBuyerName() + "'s Contact");
                share.putExtra(Intent.EXTRA_TEXT, "Name: "+ mBuyers.get(position).getBuyerName()+"\nMobile No: "+mBuyers.get(position).getBuyerMobNo() + "\nAddress: " + mBuyers.get(position).getBuyerAddress());
                mContext.startActivity(Intent.createChooser(share, "Share Contact via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBuyers.size();
    }

    static class BuyerListViewHolder extends RecyclerView.ViewHolder{
        private TextView buyerNameView;
        private TextView buyerMobView;
        private TextView buyerAddressView;
        private ImageButton share, gotoOrder;
        public BuyerListViewHolder(@NonNull View itemView) {
            super(itemView);
            buyerNameView = itemView.findViewById(R.id.buyer_name);
            buyerMobView = itemView.findViewById(R.id.buyer_mobile_number);
            buyerAddressView = itemView.findViewById(R.id.buyer_address);
            share = itemView.findViewById(R.id.share_image);
        }
    }
}

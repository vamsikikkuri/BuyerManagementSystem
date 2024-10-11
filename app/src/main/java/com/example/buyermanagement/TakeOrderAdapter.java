package com.example.buyermanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TakeOrderAdapter extends RecyclerView.Adapter<TakeOrderAdapter.TakeOrderViewHolder> {

    private ArrayList<ProductClass> mProducts = new ArrayList<>();
    private Context mContext;
    private NavController mNavController;
    private FragmentActivity fragmentActivity;
    private String mBuyerId;


    public TakeOrderAdapter(ArrayList<ProductClass> products, Context context, NavController navController, FragmentActivity activity, String buyerId) {
        mProducts = products;
        mContext = context;
        mNavController = navController;
        fragmentActivity = activity;
        mBuyerId = buyerId;
    }

    @NonNull
    @Override
    public TakeOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_take_order, parent, false);
        return new TakeOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TakeOrderViewHolder holder, final int position) {
        holder.name.setText(mProducts.get(position).getProductName());
        holder.cat.setText(mProducts.get(position).getProductModel());
        holder.price.setText(String.valueOf(mProducts.get(position).getProductPrice()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AskOrderDetailsFrag askOrderDetailsFrag = new AskOrderDetailsFrag(mBuyerId, mProducts.get(position).getProductId(), mProducts.get(position).getProductName(),
                        mProducts.get(position).getProductCategory(), mProducts.get(position).getProductModel());
                askOrderDetailsFrag.show(fragmentActivity.getSupportFragmentManager(), "ADD TO CART");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    static class TakeOrderViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView cat;
        private TextView price;
        private CardView cardView;
        public TakeOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            cat = itemView.findViewById(R.id.product_category);
            price = itemView.findViewById(R.id.product_price);
            cardView = itemView.findViewById(R.id.show_prod_card_parent);
        }
    }
}

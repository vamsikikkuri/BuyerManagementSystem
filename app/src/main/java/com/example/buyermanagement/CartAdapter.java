package com.example.buyermanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartListViewHolder>{

    private ArrayList<CartClass> mInCartList = new ArrayList<>();


    public CartAdapter(ArrayList<CartClass> inCartList){
        mInCartList = inCartList;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_card, parent, false);
        return new CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        holder.category.setText(mInCartList.get(position).getProductCategory().toUpperCase());
        holder.name.setText(mInCartList.get(position).getProductName().toUpperCase());
        holder.model.setText(mInCartList.get(position).getProductModel().toUpperCase());
        holder.price.setText(String.valueOf(mInCartList.get(position).getProductPrice()));
        holder.quantity.setText(String.valueOf(mInCartList.get(position).getProductQuantity()));
    }

    @Override
    public int getItemCount() {
        return mInCartList.size();
    }

    static class CartListViewHolder extends RecyclerView.ViewHolder{

        private TextView category, name, model, price, quantity;
        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.prod_category);
            name = itemView.findViewById(R.id.prod_comp);
            model = itemView.findViewById(R.id.prod_model);
            price = itemView.findViewById(R.id.prod_price);
            quantity = itemView.findViewById(R.id.prod_quant);

        }
    }
}

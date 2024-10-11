package com.example.buyermanagement;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BuyerDetailsAdapter extends RecyclerView.Adapter<BuyerDetailsAdapter.BuyerDetailsViewHolder> {

    private ArrayList<OrderClass> showOrders = new ArrayList<>();

    public BuyerDetailsAdapter(ArrayList<OrderClass> showOrders){
        this.showOrders = showOrders;
    }

    @NonNull
    @Override
    public BuyerDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_details_card_list, parent, false);
        return new BuyerDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerDetailsViewHolder holder, int position) {
        holder.date.setText(showOrders.get(position).getDate());
        holder.name.setText(showOrders.get(position).getProductName());
        holder.price.setText(String.valueOf(showOrders.get(position).getProductPrice()));
        holder.quant.setText(String.valueOf(showOrders.get(position).getProductQuantity()));
        holder.model.setText(showOrders.get(position).getProductModel());
    }

    @Override
    public int getItemCount() {
        return showOrders.size();
    }

    static class BuyerDetailsViewHolder extends RecyclerView.ViewHolder{
        private TextView date, name, model , price, quant;

        public BuyerDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            name = itemView.findViewById(R.id.name);
            model = itemView.findViewById(R.id.model);
            price = itemView.findViewById(R.id.price);
            quant = itemView.findViewById(R.id.quant);
        }
    }
}

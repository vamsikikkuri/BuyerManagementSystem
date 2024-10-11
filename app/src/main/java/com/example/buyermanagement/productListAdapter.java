package com.example.buyermanagement;

import android.content.Context;
import android.util.Log;
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

public class productListAdapter extends RecyclerView.Adapter<productListAdapter.productViewHolder> {

    private ArrayList<ProductClass> mProducts = new ArrayList<>();
    FragmentActivity mActivity;
    productListAdapter(ArrayList<ProductClass> products, FragmentActivity activity){
        mProducts = products;
        mActivity = activity;
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product, parent, false);
        return new productViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, final int position) {
        holder.catView.setText(mProducts.get(position).getProductCategory().toUpperCase());
        holder.compView.setText(mProducts.get(position).getProductName());
        holder.modelView.setText(mProducts.get(position).getProductModel());
        holder.priceView.setText(String.valueOf(mProducts.get(position).getProductPrice()));
        holder.stockView.setText(String.valueOf(mProducts.get(position).getProductStock()));
        holder.prodCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("TAG", "onLongClick: "+ mProducts.get(position).getProductId());
                UpdateProductInfo updateProductInfo = new UpdateProductInfo(mProducts.get(position).getProductCategory(), mProducts.get(position).getProductId());
                updateProductInfo.show(mActivity.getSupportFragmentManager(), "UPDATE PRODUCT INFO");
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class productViewHolder extends RecyclerView.ViewHolder{

        private TextView catView, compView, stockView, modelView, priceView;
        private CardView prodCard;
        public productViewHolder(@NonNull View itemView) {
            super(itemView);
            catView = itemView.findViewById(R.id.prod_category);
            compView = itemView.findViewById(R.id.prod_comp);
            stockView = itemView.findViewById(R.id.prod_stock);
            modelView = itemView.findViewById(R.id.prod_model);
            priceView = itemView.findViewById(R.id.prod_price);
            prodCard = itemView.findViewById(R.id.prod_card);
        }
    }
}

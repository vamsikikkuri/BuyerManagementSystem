package com.example.buyermanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.BundleCompat;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ProductCategoryListAdapter extends RecyclerView.Adapter<ProductCategoryListAdapter.ProductCategoryViewHolder>{
    public static final String PROD_CATEGORY = "PROD_CATEGORY";
    private ArrayList<String> mCategories = new ArrayList<>();
    private NavController mNavcontroller;

    public ProductCategoryListAdapter(ArrayList<String> categories, NavController navController){
        mCategories = categories;
        mNavcontroller = navController;
    }

    public ProductCategoryListAdapter(){}

    @NonNull
    @Override
    public ProductCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_category, parent, false);
        return new ProductCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCategoryViewHolder holder, final int position) {
        holder.prodCategory.setText(mCategories.get(position).toUpperCase());
        holder.prodCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle category = new Bundle();
                category.putString(PROD_CATEGORY, mCategories.get(position));
                mNavcontroller.navigate(R.id.action_productCategoryFrag_to_productFrag, category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    static class ProductCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView prodCategory;
        public ProductCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            prodCategory = itemView.findViewById(R.id.product_category_inCAt);
        }
    }
}

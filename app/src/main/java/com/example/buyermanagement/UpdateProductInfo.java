package com.example.buyermanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProductInfo extends DialogFragment {


    private EditText updatePrice, updateStock;
    private Button updateInfo;

    private String category, id;

    private DatabaseReference updateProductDataRef;
    public UpdateProductInfo(){}
    public UpdateProductInfo(String productCategory, String productId) {
        category = productCategory;
        id = productId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_product_info, container, false);
        updateInfo = view.findViewById(R.id.update_prod_info_btn);
        updatePrice = view.findViewById(R.id.update_prod_price);
        updateStock = view.findViewById(R.id.update_prod_stock);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updatePrice.setText("100");
        updateStock.setText("200");
        updateProductDataRef = FirebaseDatabase.getInstance().getReference("products");
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPrice = updatePrice.getText().toString().trim();
                String newStock = updateStock.getText().toString().trim();
                if(!(newPrice.equals("") || newStock.equals(""))){
                    updateProductDataRef.child(category).child(id).child("productPrice").setValue(Integer.parseInt(newPrice));
                    updateProductDataRef.child(category).child(id).child("productStock").setValue(Integer.parseInt(newStock));
                    dismiss();
                }
                else{
                    Toast.makeText(getActivity(), "PLEASE FILL ALL FIELDS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.example.buyermanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewProduct extends DialogFragment {

    private String category = "";

    private EditText newProdName, newprodModel, newProdPrice, newProdStock;
    private Button newProdSubmit;

    private DatabaseReference addingNewProd;

    public AddNewProduct() {
        // Required empty public constructor
    }

    public AddNewProduct(String category){
        this.category = category;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_product, container, false);
        newProdName = view.findViewById(R.id.edit_new_prod_comp_name);
        newprodModel = view.findViewById(R.id.edit_new_prod_comp_model);
        newProdPrice = view.findViewById(R.id.edit_new_prod_price);
        newProdStock = view.findViewById(R.id.edit_new_prod_stock);
        newProdSubmit = view.findViewById(R.id.submit_new_prod);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addingNewProd = FirebaseDatabase.getInstance().getReference("products");

        newProdSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productId =  addingNewProd.push().getKey();
                String name = newProdName.getText().toString().toUpperCase().trim();
                String model = newprodModel.getText().toString().toUpperCase().trim();
                String price = newProdPrice.getText().toString().trim();
                String stock = newProdStock.getText().toString().trim();
                if(!(name.equals("") || model.equals("") || price.equals("") || stock.equals(""))) {
                    addingNewProd.child(category).child(productId).setValue(new ProductClass(productId, category, name, model, Integer.parseInt(price), Integer.parseInt(stock)));
                    dismiss();
                }
                else{
                    Toast.makeText(getActivity(), "FILL EVERY FIELD", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
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

public class AskOrderDetailsFrag extends DialogFragment {

    private EditText enterPrice, enterQuant;
    private Button addToCart;
    private String buyerId, productName, productCategory, productModel, productId;
    private DatabaseReference addToCartRef;


    public AskOrderDetailsFrag() {
        // Required empty public constructor
    }

    public AskOrderDetailsFrag(String mBuyerId, String productId, String productName, String productCategory, String productModel) {
        buyerId = mBuyerId;
        this.productCategory = productCategory;
        this.productId = productId;
        this.productModel = productModel;
        this.productName = productName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_order_details, container, false);
        enterPrice = view.findViewById(R.id.enter_selling_price);
        enterQuant = view.findViewById(R.id.enter_selling_quantity);
        addToCart = view.findViewById(R.id.add_to_cart);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addToCartRef = FirebaseDatabase.getInstance().getReference("Buyers");

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = enterPrice.getText().toString();
                String quant = enterQuant.getText().toString();
                if(!(price.equals("") && quant.equals(""))){
                    String cartId = addToCartRef.push().getKey();
                    //Toast.makeText(getActivity(), price + "   " + quant, Toast.LENGTH_SHORT).show();
                    addToCartRef.child(buyerId).child("cart").child(cartId).setValue(new CartClass(productName, productCategory, Integer.parseInt(price), Integer.parseInt(quant), productModel, productId));
                    dismiss();
                }
            }
        });
    }
}
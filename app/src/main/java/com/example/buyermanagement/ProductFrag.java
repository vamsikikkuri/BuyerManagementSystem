package com.example.buyermanagement;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProductFrag extends Fragment {

    ArrayList<ProductClass> products = new ArrayList<>();
    private String prodCategory;
    private RecyclerView recyclerView;
    DatabaseReference prodDataRef;

    private FloatingActionButton fab;
    public ProductFrag() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            prodCategory = getArguments().getString(ProductCategoryListAdapter.PROD_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        fab = view.findViewById(R.id.add_product);

        prodDataRef = FirebaseDatabase.getInstance().getReference("products");

        Log.d("TAG", "onCreateView: "+prodCategory);
        getProductData(prodCategory);
        recyclerView = view.findViewById(R.id.prod_recycler_view);
        
        return view;
    }

    private void getProductData(String prodCategory) {
        prodDataRef.child(prodCategory).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for(DataSnapshot prodShot : snapshot.getChildren()){
                    ProductClass productClass = prodShot.getValue(ProductClass.class);
                    products.add(productClass);
                }
                populateDate(products);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateDate(ArrayList<ProductClass> products) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new productListAdapter(products, getActivity()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewProduct addNewProduct = new AddNewProduct(prodCategory);
                addNewProduct.show(getActivity().getSupportFragmentManager(), "ADD NEW PRODUCT");
            }
        });
    }
}
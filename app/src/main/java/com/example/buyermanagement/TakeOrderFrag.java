package com.example.buyermanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class TakeOrderFrag extends Fragment {

    public static final String BUYER_ID = "BUYER_ID";
    public static final String BUYER_NAME = "BUYER_NAME";

    private ArrayList<ProductClass> availableProducts = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();

    NavController navController;

    private DatabaseReference newOrderRef;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;

    private String BuyerId_fromDetails, BuyerId_fromHome, BuyerId;
    private String BuyerName_fromDetails, BuyerName_fromHome, BuyerName;
    public TakeOrderFrag() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            BuyerId_fromDetails = getArguments().getString(BuyerDetailsFrag.BUYER_ID);
            BuyerName_fromDetails = getArguments().getString(BuyerDetailsFrag.BUYER_NAME);
            BuyerId_fromHome = getArguments().getString(BuyerListAdapter.BUYER_ID_IN_LIST);
            BuyerName_fromHome = getArguments().getString(BuyerListAdapter.BUYER_NAME_IN_LIST);
        }

        if(BuyerId_fromDetails == null || BuyerName_fromDetails == null){
            BuyerId = BuyerId_fromHome;
            BuyerName = BuyerName_fromHome;
        }
        else if(BuyerId_fromHome == null || BuyerName_fromHome == null){
            BuyerId = BuyerId_fromDetails;
            BuyerName = BuyerName_fromDetails;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_order_list, container, false);
        recyclerView = view.findViewById(R.id.list);
        linearLayout = view.findViewById(R.id.linear_layout_categories);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onViewCreated(view, savedInstanceState);

         navController = Navigation.findNavController(view);
        newOrderRef = FirebaseDatabase.getInstance().getReference("products");

        newOrderRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot getProdshot : snapshot.getChildren()) {
                    final ProductClass getProds = getProdshot.getValue(ProductClass.class);
                    if(!(categories.contains(getProds.getProductCategory()))) {
                        categories.add(getProds.getProductCategory());
                        Button btn = new Button(getContext());
                        btn.setText(getProds.getProductCategory());
                        btn.setTag(categories.size());
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getDataFromfilteredCategory(getProds.getProductCategory());
                            }
                        });
                        btn.setBackgroundResource(R.drawable.button);
                        linearLayout.addView(btn);
                    }
                    availableProducts.add(getProds);
                }
                populateRecyclerView(availableProducts, navController);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG", "onResume: "+ categories.size());
        availableProducts.clear();
        if(categories.size() != 0){
           for(int i = 0;i<categories.size();i++){
               final String cat = categories.get(i);
               Button btn = new Button(getContext());
               btn.setText(cat);
               btn.setTag(categories.size());
               btn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       getDataFromfilteredCategory(cat);
                   }
               });
               btn.setBackgroundResource(R.drawable.button);
               linearLayout.addView(btn);
           }
        }
    }


    private void getDataFromfilteredCategory(String productCategory) {
        newOrderRef.child(productCategory).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                availableProducts.clear();
                for (DataSnapshot getProdshot : snapshot.getChildren()) {
                    ProductClass getProds = getProdshot.getValue(ProductClass.class);
                    availableProducts.add(getProds);
                }
                populateRecyclerView(availableProducts, navController);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void populateRecyclerView(ArrayList<ProductClass> products, NavController navController) {
        recyclerView.setAdapter(new TakeOrderAdapter(products, getContext(), navController, getActivity(), BuyerId));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.take_order_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart_option) {
            Bundle args = new Bundle();
            args.putString(BUYER_ID, BuyerId);
            args.putString(BUYER_NAME, BuyerName);
            navController.navigate(R.id.action_takeOrderFrag_to_cartFrag, args);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
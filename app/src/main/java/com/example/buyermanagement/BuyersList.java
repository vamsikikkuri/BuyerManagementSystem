package com.example.buyermanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BuyersList extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    BuyerListAdapter buyerListAdapter;

    private ArrayList<BuyerClass> buyers = new ArrayList<>();
    NavController navController;

    private DatabaseReference getBuyerDataRef;

    public BuyersList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyers_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        fab = view.findViewById(R.id.fab);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getBuyerDataRef = FirebaseDatabase.getInstance().getReference();
        getBuyerDataRef.child("Buyers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                buyers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BuyerClass buyerClass = dataSnapshot.getValue(BuyerClass.class);
                    buyers.add(buyerClass);
                }
                populateRecyclerView(buyers, navController);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBuyerFrag addBuyerFrag = new AddBuyerFrag();
                addBuyerFrag.show(getActivity().getSupportFragmentManager(), "ADD BUYER FRAG");
            }
        });

    }

    public void populateRecyclerView(ArrayList<BuyerClass> buyers, NavController navController){
        buyerListAdapter = new BuyerListAdapter(buyers, navController, getContext());
        recyclerView.setAdapter(buyerListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        if(id == R.id.action_goto_products){
            navController.navigate(R.id.action_buyersList_to_productCategoryFrag);
        }


        return super.onOptionsItemSelected(item);
    }

}
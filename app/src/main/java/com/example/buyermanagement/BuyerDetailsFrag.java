package com.example.buyermanagement;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class BuyerDetailsFrag extends Fragment {

    public static final String BUYER_ID = "BUYER_ID";
    public static final String BUYER_NAME = "BUYER_NAME";

    private TextView buyerNameDetails;
    private Button takeNewOrder;
    private String BuyerId, BuyerId_fromCart, BuyerId_fromList;
    private String BuyerName, BuyerName_fromCart, BuyerName_fromList;
    private RecyclerView recyclerView;
    private DatabaseReference getBuyerDetailsRef, filterByDate;
    private int mYear, mMonth, mDay;
    String dateSelected = new String();
    private ArrayList<OrderClass> showOrders = new ArrayList<>();
    private ArrayList<String> ordered_date = new ArrayList<>();
    public BuyerDetailsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            BuyerId_fromList = getArguments().getString(BuyerListAdapter.BUYER_ID_IN_LIST);
            BuyerName_fromList = getArguments().getString(BuyerListAdapter.BUYER_NAME_IN_LIST);
            BuyerId_fromCart = getArguments().getString(CartFrag.BUYER_ID);
            BuyerName_fromCart = getArguments().getString(CartFrag.BUYER_NAME);
        }

        if(BuyerName_fromCart == null){
            BuyerId = BuyerId_fromList;
            BuyerName = BuyerName_fromList;
        }
        else if (BuyerName_fromList == null){
            BuyerId = BuyerId_fromCart;
            BuyerName = BuyerName_fromCart;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer_details, container, false);
        takeNewOrder = view.findViewById(R.id.take_new_order);
        buyerNameDetails = view.findViewById(R.id.buyer_name_details);
        recyclerView = view.findViewById(R.id.recyclerViewBuyerDetails);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onViewCreated(view, savedInstanceState);

        buyerNameDetails.setText(BuyerName);
        final NavController navController = Navigation.findNavController(view);
        takeNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString(BUYER_ID, BuyerId);
                args.putString(BUYER_NAME, BuyerName);
                navController.navigate(R.id.action_buyerDetailsFrag_to_takeOrderFrag, args);
            }
        });

        getBuyerDetailsRef = FirebaseDatabase.getInstance().getReference("Orders").child(BuyerId);
        getBuyerDetailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showOrders.clear();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    OrderClass orderClass = ds.getValue(OrderClass.class);
                    showOrders.add(orderClass);
                }
                populatedBuyerDetails(showOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void populatedBuyerDetails(ArrayList<OrderClass> showOrders){
        recyclerView.setAdapter(new BuyerDetailsAdapter(showOrders));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.select_date) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            dateSelected = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                            filterOrdersByDate(dateSelected);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void filterOrdersByDate(String dateSelected) {
        Log.d("TAG", "filterOrdersByDate: "+dateSelected);
        Query filter = FirebaseDatabase.getInstance().getReference().child("Orders").child(BuyerId).orderByChild("date");
        filter.equalTo(dateSelected).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showOrders.clear();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    OrderClass orderClass = ds.getValue(OrderClass.class);
                    showOrders.add(orderClass);
                }
                populatedBuyerDetails(showOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
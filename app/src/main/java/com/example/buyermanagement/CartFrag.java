package com.example.buyermanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CartFrag extends Fragment {

    private String buyerId;
    private String buyerName;

    public static final String BUYER_ID = "BUYER_ID";
    public static final String BUYER_NAME = "BUYER_NAME";

    private RecyclerView recyclerView;
    private DatabaseReference mCartRef, mOrderRef;
    private EditText vehicleNo;
    private TextView editDate;
    private RadioGroup radioGroup;
    private Button submitOrder;
    private String vehicle_number;
//    private RadioButton radioButton;
    private String selected_date;
    private String order_date;
//    private String payment_mode;
    DatePickerDialog datePickerDialog;
    private ArrayList<CartClass> inCartList = new ArrayList<>();
    private NavController navController;

    public CartFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            buyerId = getArguments().getString(TakeOrderFrag.BUYER_ID);
            buyerName = getArguments().getString(TakeOrderFrag.BUYER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.cart_relative_layout);
        submitOrder = view.findViewById(R.id.submit_order_btn);
        vehicleNo = view.findViewById(R.id.enter_vehicle_no);
        radioGroup = view.findViewById(R.id.radioGroup);
        editDate = view.findViewById(R.id.editTextDate);
        mCartRef = FirebaseDatabase.getInstance().getReference("Buyers");
        mCartRef.child(buyerId).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Log.d("TAG", "onDataChange: "+ds);
                    CartClass inCart = ds.getValue(CartClass.class);
                    inCartList.add(inCart);
                }
                populateCart(inCartList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void populateCart(ArrayList<CartClass> inCartList) {
        recyclerView.setAdapter(new CartAdapter(inCartList));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        order_date = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault()).format(new Date());
        editDate.setText(order_date);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selected_date = day+"-"+(month+1)+'-'+year;
                        editDate.setText(selected_date);
                        order_date = selected_date;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                vehicle_number = vehicleNo.getText().toString();
//                if (vehicle_number.equals("")) {
//                    Toast.makeText(getActivity(), "Please enter Vehicle number", Toast.LENGTH_SHORT).show();
//                }
//                if(radioGroup.getCheckedRadioButtonId()==-1){
//                    Toast.makeText(getActivity(), "Please select payment mode", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    radioButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
//                    Log.d("TAG", "onClick: "+radioGroup.getCheckedRadioButtonId());
//                    payment_mode = radioButton.getText().toString();
//                }


                mOrderRef = FirebaseDatabase.getInstance().getReference("Orders").child(buyerId);//.child(order_date);

                if (inCartList.size() > 0) {
                    for (int i = 0; i < inCartList.size(); i++) {
                        String order_id = mOrderRef.push().getKey();
                        mOrderRef.child(order_id).setValue(new OrderClass(inCartList.get(i).getProductName(), inCartList.get(i).getProductCategory(),
                                inCartList.get(i).getProductPrice(), inCartList.get(i).getProductQuantity()
                                , inCartList.get(i).getProductModel(), inCartList.get(i).getProductId(), order_date, "LINK TO PDF"));
                    }
                }
                else {
                    Toast.makeText(getActivity(), "No Products in cart", Toast.LENGTH_SHORT).show();
                }

                mCartRef.child(buyerId).child("cart").removeValue();
                Bundle args = new Bundle();
                args.putString(BUYER_ID, buyerId);
                args.putString(BUYER_NAME, buyerName);
                navController.navigate(R.id.action_cartFrag_to_buyerDetailsFrag, args, new NavOptions.Builder().setPopUpTo(R.id.buyerDetailsFrag, true).build());
            }
        });

    }
}
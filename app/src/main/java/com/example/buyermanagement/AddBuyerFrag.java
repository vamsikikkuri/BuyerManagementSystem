package com.example.buyermanagement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddBuyerFrag extends DialogFragment {

    private EditText editName;
    private EditText editAddress;
    private EditText editMobileNo;
    private Button submitBtn;

    private DatabaseReference addBuyerRef;

    public AddBuyerFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_buyer, container, false);
        editName = view.findViewById(R.id.buyer_name);
        editAddress = view.findViewById(R.id.buyer_address);
        editMobileNo = view.findViewById(R.id.buyer_mob_no);
        submitBtn = view.findViewById(R.id.add_buyer);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //setHasOptionsMenu(true);

        super.onViewCreated(view, savedInstanceState);
//        final NavController AddBuyerController = Navigation.findNavController(view);

        addBuyerRef = FirebaseDatabase.getInstance().getReference("Buyers");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String address = editAddress.getText().toString().trim();
                String mob = editMobileNo.getText().toString().trim();
                    if(!(name.equals("") || address.equals("") || mob.equals(""))){
                        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                        String buyerId = addBuyerRef.push().getKey();
                        addBuyerRef.child(buyerId).setValue(new BuyerClass(name, buyerId, mob, address));
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new BuyersList());
                        dismiss();
                        //                   AddBuyerController.navigate(R.id.action_addBuyerFrag_to_buyersList, null, new NavOptions.Builder().setPopUpTo(R.id.buyersList, true).build());
                }
                    else
                    {
                        Toast.makeText(getActivity(), "Please fill every field", Toast.LENGTH_SHORT).show();
                    }

            }
        });

//        submitBtn.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Toast.makeText(getActivity(), "LONG CLICK", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }

}
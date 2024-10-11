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

public class AddProductCategory extends DialogFragment {

    private EditText enterNewCategory;
    private Button submit;
    private DatabaseReference addNewCatRef;
    public AddProductCategory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product_category, container, false);
        enterNewCategory = view.findViewById(R.id.new_category);
        submit = view.findViewById(R.id.submit_category);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addNewCatRef = FirebaseDatabase.getInstance().getReference("products");
//        String catId = addNewCatRef.push().getKey();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCategory = enterNewCategory.getText().toString().trim();
                if(!(newCategory.equals(""))) {
                    addNewCatRef.child(newCategory).setValue(newCategory);
                    dismiss();
                }
                else{
                    Toast.makeText(getActivity(), "Category cannot be null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class AddCityFragment extends DialogFragment {
    private City cityToEdit;
    interface AddCityDialogListener{
        void addCity(City city);
        void editCity(City old, City newCity);
    }
    public AddCityFragment() {}
    public static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private AddCityDialogListener listener;

    @Override
    public void onAttach (@NonNull Context context){
        super.onAttach(context);

        if (context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city");
        }
        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) ->{
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    City newCity = new City(cityName, provinceName);
                    if (cityToEdit == null) {
                        listener.addCity(newCity);
                    } else {
                        listener.editCity(cityToEdit, newCity);
                    }
                })
                .create();
    }

}

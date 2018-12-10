package com.example.arpan.ezpay;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.nio.charset.MalformedInputException;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuickSetup extends Fragment  {

    private Button btnNext;
    //DatabaseHelper databaseHelper;
    public QuickSetup() {
        //addMockData();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Quick Setup");
        MainActivity.chkAddOrganization=true;
        MainActivity.chkPaymentMethod=true;
        MainActivity.chkListPaymentMethod=false;
        MainActivity.chkListAddOrganization=false;
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_quick_setup, container, false);
        //Intialization Button
        btnNext = (Button) view.findViewById(R.id.btnNextQS);

        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddPaymentMethod()).addToBackStack(null).commit();

                // do something
            }
        });
    return view;
    }




}

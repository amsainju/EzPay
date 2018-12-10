package com.example.arpan.ezpay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddInternet extends Fragment {
    Spinner spBillInternetTvOrganizations;
    Spinner spInternetTvPaymentMethodSelection;
    List<String> listBillInternetTvOrganizations;
    List<String> listInternetTvPaymentMethodOptions;
    ArrayAdapter<String> adapterSpinner;
    CheckBox chkAutoPayment;
    private TextInputLayout textInternetTvAccountNumber;
    private Button btnAddBillInternetTvOrganization, btnSkipAddBillInternetTvOrganization;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;


    public AddInternet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Link Internet");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_internet, container, false);
        textInternetTvAccountNumber = view.findViewById(R.id.txtInternetTvAccountNumber);
        chkAutoPayment = (CheckBox) view.findViewById(R.id.chkAutoPayment);
        spBillInternetTvOrganizations = (Spinner) view.findViewById(R.id.sp_BillInternetTvOrganizationName);
        listBillInternetTvOrganizations = new ArrayList<>();
        String[] BillInternetTvOrganizations = {"Select Internet Provider", "AT&T", "Xfinity", "Spectrum"};
        Collections.addAll(listBillInternetTvOrganizations, BillInternetTvOrganizations);
        adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.spinner_test, listBillInternetTvOrganizations);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBillInternetTvOrganizations.setAdapter(adapterSpinner);

        spInternetTvPaymentMethodSelection = (Spinner) view.findViewById(R.id.sp_InternetTvPaymentMethodSelection);
        listInternetTvPaymentMethodOptions = new ArrayList<>();
        String[] InternetTvPaymentMethodOptions = {"Select Preferred Payment Method", "MyWellsFargo", "MyAmericanExpress", "MyVenmo"};
        Collections.addAll(listInternetTvPaymentMethodOptions, InternetTvPaymentMethodOptions);
        adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.spinner_test, listInternetTvPaymentMethodOptions);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInternetTvPaymentMethodSelection.setAdapter(adapterSpinner);      databaseHelper=new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        btnAddBillInternetTvOrganization = view.findViewById(R.id.btnAddBillInternetTvOrganization);
        btnSkipAddBillInternetTvOrganization = view.findViewById(R.id.btnSkipAddBillInternetTvOrganization);
        btnAddBillInternetTvOrganization.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!validateInternetTvBillOrganizationName() | !validateInternetTvAccountNumber() | !validateInternetTvPaymentOptionMethod()) {
                    return;
                }
                String textOrg = spBillInternetTvOrganizations.getSelectedItem().toString();
                String  accountNumber= textInternetTvAccountNumber.getEditText().getText().toString().trim();
                String pmalias = spInternetTvPaymentMethodSelection.getSelectedItem().toString();
                boolean bvalue= chkAutoPayment.isChecked();
                String autopay;
                if(bvalue){
                    autopay = "true";
                }
                else{
                    autopay = "false";
                }
                // MainActivity.chkBoxCreditCard.setChecked(false);
                boolean insertData = databaseHelper.addOrganization("Internet", textOrg,autopay,pmalias,accountNumber,null,null,null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage(" ")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.chkBoxInternet.setChecked(false);
                                if(MainActivity.chkListAddOrganization){
                                    MainActivity.chkListAddOrganization =false;
                                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new OrganizationList()).addToBackStack(null).commit();
                                }
                                    else{
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                                    }

                                }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle("Internet Account Sucessfully Added!");
                alert.show();




            }
        });

        btnSkipAddBillInternetTvOrganization.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkBoxInternet.setChecked(false);
                if(MainActivity.chkListAddOrganization){
                    MainActivity.chkListAddOrganization =false;
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new OrganizationList()).addToBackStack(null).commit();
                }
                    else{
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                    }
            }
        });




        return view;
    }
    private boolean validateInternetTvBillOrganizationName(){
        Integer billInternetTvOrganizationNameInput = spBillInternetTvOrganizations.getSelectedItemPosition();
        String aux0 = String.valueOf(billInternetTvOrganizationNameInput);
        if ("0".equals(aux0)){
            return false;
        } else {
            return true;
        }
    }

    private boolean validateInternetTvAccountNumber() {
        String internetTvAccountNumberInput = textInternetTvAccountNumber.getEditText().getText().toString().trim();
        if (internetTvAccountNumberInput.isEmpty()) {
            textInternetTvAccountNumber.setError("Field can't be empty");
            return false;
        } else {
            textInternetTvAccountNumber.setError(null);
            return true;
        }
    }

    private boolean validateInternetTvPaymentOptionMethod(){
        Integer internetTVPaymentMethodOptionInput = spInternetTvPaymentMethodSelection.getSelectedItemPosition();
        String aux1 = String.valueOf(internetTVPaymentMethodOptionInput);
        if ("0".equals(aux1)){
            return false;
        } else {
            return true;
        }
    }

    /*public void addBillInternetTvOrganizationInfo(View v){

        if (!validateInternetTvAccountNumber()) {
            return;
        } else if (validateInternetTvBillOrganizationName()) {
            Toast toast0 = new Toast(getApplicationContext());
            toast0.setGravity(Gravity.CENTER_VERTICAL,0, -200);
            TextView tv = new TextView(AddInternetTvOrganization.this);
            tv.setBackgroundColor(Color.DKGRAY);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(20);
            tv.setText("Select a valid organization!");
            toast0.setView(tv);
            toast0.show();
            return;
        }
        else if (validateInternetTvPaymentOptionMethod()) {
            Toast toast1 = new Toast(getApplicationContext());
            toast1.setGravity(Gravity.CENTER_VERTICAL,0, -200);
            TextView tv = new TextView(AddInternetTvOrganization.this);
            tv.setBackgroundColor(Color.DKGRAY);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(20);
            tv.setText("Select a valid payment method!");
            toast1.setView(tv);
            toast1.show();
            return;
        }
        Toast toast2 = new Toast(getApplicationContext());
        toast2.setGravity(Gravity.CENTER_VERTICAL,0, -200);
        TextView tv = new TextView(AddInternetTvOrganization.this);
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setText("Bill information registered!");
        toast2.setView(tv);
        toast2.show();
        return;
    }*/
}

package com.example.arpan.ezpay;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;

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
public class AddElectricity extends Fragment {
    Spinner spBillOrganizations;
    Spinner spPaymentMethodSelection;
    CheckBox chkAutoPayment;
    List<String> listBillOrganizations;
    List<String> listPaymentMethodOptions;
    ArrayAdapter<String> adapterSpinner;
    private Button btnAddBillElectricityOrganization, btnSkipAddBillElectricityOrganization;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    private TextInputLayout textAccountNumber;

    public AddElectricity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Link Electricity");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_electricity, container, false);
        textAccountNumber = view.findViewById(R.id.txtAccountNumber);
        chkAutoPayment = view.findViewById(R.id.chkAutoPayment);
        spBillOrganizations = (Spinner) view.findViewById(R.id.sp_BillOrganizationName);
        spBillOrganizations.requestFocus();
        listBillOrganizations = new ArrayList<>();
        String[] BillOrganizations = {"Select Electricity Provider", "Alabama Power", "Georgia Power", "Gulf Power and Mississippi Power"};
        Collections.addAll(listBillOrganizations, BillOrganizations);
        adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.spinner_test, listBillOrganizations);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBillOrganizations.setAdapter(adapterSpinner);

        spPaymentMethodSelection = (Spinner) view.findViewById(R.id.sp_PaymentMethodSelection);
        listPaymentMethodOptions = new ArrayList<>();
        String[] PaymentMethodOptions = {"Select Preferred Payment Method", "MyWellsFargo", "MyAmericanExpress", "MyVenmo"};
        Collections.addAll(listPaymentMethodOptions, PaymentMethodOptions);
        adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.spinner_test, listPaymentMethodOptions);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPaymentMethodSelection.setAdapter(adapterSpinner);
        databaseHelper=new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        btnAddBillElectricityOrganization = view.findViewById(R.id.btnAddBillElectricityOrganization);
        btnSkipAddBillElectricityOrganization = view.findViewById(R.id.btnSkipAddBillElectricityOrganization);
        btnAddBillElectricityOrganization.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!validateBillOrganizationName() | !validateAccountNumber() | !validateMethodPaymentOption()) {
                    return;
                }
                String textOrg = spBillOrganizations.getSelectedItem().toString();
                String  accountNumber= textAccountNumber.getEditText().getText().toString().trim();
                String pmalias = spPaymentMethodSelection.getSelectedItem().toString();
                boolean bvalue= chkAutoPayment.isChecked();
                String autopay;
                if(bvalue){
                    autopay = "true";
                }
                else{
                    autopay = "false";
                }
               // MainActivity.chkBoxCreditCard.setChecked(false);
                boolean insertData = databaseHelper.addOrganization("Electricity", textOrg,autopay,pmalias,accountNumber,null,null,null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage(" ")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.chkBoxElectricity.setChecked(false);
                                if(MainActivity.chkBoxInternet.isChecked()){
                                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddInternet()).addToBackStack(null).commit();
                                    // do something
                                }
                                else{
                                    if(MainActivity.chkListAddOrganization){
                                        MainActivity.chkListAddOrganization =false;
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new OrganizationList()).addToBackStack(null).commit();
                                    }
                                    else{
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                                    }

                                }                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle("Electircity Account Sucessfully Added!");
                alert.show();




            }
        });

        btnSkipAddBillElectricityOrganization.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkBoxElectricity.setChecked(false);
                if(MainActivity.chkBoxInternet.isChecked()){
                   MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddInternet()).addToBackStack(null).commit();
                    // do something
                }
                else{
                    if(MainActivity.chkListAddOrganization){
                        MainActivity.chkListAddOrganization =false;
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new OrganizationList()).addToBackStack(null).commit();
                    }
                    else{
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                    }

                }

            }
        });


        return view;
    }
    private boolean validateBillOrganizationName(){
        Integer billOrganizationNameInput = spBillOrganizations.getSelectedItemPosition();
        String aux0 = String.valueOf(billOrganizationNameInput);
        if ("0".equals(aux0)){
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAccountNumber() {
        String billOrganizationAccountNumberInput = textAccountNumber.getEditText().getText().toString().trim();
        if (billOrganizationAccountNumberInput.isEmpty()) {
            textAccountNumber.setError("Field can't be empty");
            return false;
        } else {
            textAccountNumber.setError(null);
            return true;
        }
    }

    private boolean validateMethodPaymentOption(){
        Integer methodPaymentOptionInput = spPaymentMethodSelection.getSelectedItemPosition();
        String aux1 = String.valueOf(methodPaymentOptionInput);
        if ("0".equals(aux1)){
            return false;
        } else {
            return true;
        }
    }

    public boolean addBillElectricityOrganizationInfo(View v){
        if (!validateAccountNumber()) {
            return false;
        } else if (validateBillOrganizationName()) {
            Toast toast0 = new Toast(getContext());
            toast0.setGravity(Gravity.CENTER_VERTICAL,0, -200);
            TextView tv = new TextView(getContext());
            tv.setBackgroundColor(Color.DKGRAY);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(20);
            tv.setText("Select a valid organization!");
            toast0.setView(tv);
            toast0.show();
            return false;
        }
        else if (validateMethodPaymentOption()) {
            Toast toast1 = new Toast(getContext());
            toast1.setGravity(Gravity.CENTER_VERTICAL,0, -200);
            TextView tv = new TextView(getContext());
            tv.setBackgroundColor(Color.DKGRAY);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(20);
            tv.setText("Select a valid payment method!");
            toast1.setView(tv);
            toast1.show();
            return false;
        }
        //Toast toast2 = new Toast(getContext());
        //toast2.setGravity(Gravity.CENTER_VERTICAL,0, -200);
        //TextView tv = new TextView(getContext());
       // tv.setBackgroundColor(Color.DKGRAY);
      //  tv.setTextColor(Color.WHITE);
      //  tv.setTextSize(20);
      //  tv.setText("Bill information registered!");
     //   toast2.setView(tv);
      //  toast2.show();
        return true;
    }


}



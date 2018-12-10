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
public class AddWater extends Fragment {
    Spinner spBillWaterOrganizations;
    Spinner spWaterPaymentMethodSelection;
    List<String> listBillWaterOrganizations;
    List<String> listWaterPaymentMethodOptions;
    ArrayAdapter<String> adapterSpinner;
    CheckBox chkAutoPayment;
    private TextInputLayout textWaterAccountNumber, textWaterCustomerNumber;
    private Button btnAddBillWaterOrganization, btnSkipAddBillWaterOrganization;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;


    public AddWater() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Link Water");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_water, container, false);
        textWaterAccountNumber = view.findViewById(R.id.txtWaterAccountNumber);
        textWaterCustomerNumber = view.findViewById(R.id.txtWaterCustomerNumber);
        spBillWaterOrganizations = (Spinner) view.findViewById(R.id.sp_BillWaterOrganizationName);
        chkAutoPayment = (CheckBox) view.findViewById(R.id.chkAutoPayment);
        listBillWaterOrganizations = new ArrayList<>();
        String[] BillWaterOrganizations = {"Select Water Organization", "Tuscaloosa Water", "American Water", "SouthWest Water"};
        Collections.addAll(listBillWaterOrganizations, BillWaterOrganizations);
        adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.spinner_test, listBillWaterOrganizations);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBillWaterOrganizations.setAdapter(adapterSpinner);

        spWaterPaymentMethodSelection = (Spinner) view.findViewById(R.id.sp_WaterPaymentMethodSelection);
        listWaterPaymentMethodOptions = new ArrayList<>();
        String[] WaterPaymentMethodOptions = {"Select Preferred Payment Method", "MyWellsFargo", "MyAmericanExpress", "MyVenmo"};
        Collections.addAll(listWaterPaymentMethodOptions, WaterPaymentMethodOptions);
        adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.spinner_test, listWaterPaymentMethodOptions);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWaterPaymentMethodSelection.setAdapter(adapterSpinner);      databaseHelper=new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        btnAddBillWaterOrganization = view.findViewById(R.id.btnAddBillWaterOrganization);
        btnSkipAddBillWaterOrganization = view.findViewById(R.id.btnSkipAddBillWaterOrganization);
        btnAddBillWaterOrganization.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!validateWaterBillOrganizationName() | !validateWaterAccountNumber() | !validateWaterPaymentOptionMethod()) {
                    return;
                }
                String textOrg = spBillWaterOrganizations.getSelectedItem().toString();
                String  accountNumber= textWaterAccountNumber.getEditText().getText().toString().trim();
                String pmalias = spWaterPaymentMethodSelection.getSelectedItem().toString();
                String customerNumber = textWaterCustomerNumber.getEditText().getText().toString().trim();
                boolean bvalue= chkAutoPayment.isChecked();
                String autopay;
                if(bvalue){
                    autopay = "true";
                }
                else{
                    autopay = "false";
                }
                // MainActivity.chkBoxCreditCard.setChecked(false);
                boolean insertData = databaseHelper.addOrganization("Water", textOrg,autopay,pmalias,accountNumber,customerNumber,null,null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage(" ")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.chkBoxInternet.setChecked(false);
                                if(MainActivity.chkBoxElectricity.isChecked()){
                                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddElectricity()).addToBackStack(null).commit();
                                }
                                else if(MainActivity.chkBoxInternet.isChecked()){
                                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddInternet()).addToBackStack(null).commit();
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
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle("Water Account Sucessfully Added!");
                alert.show();




            }
        });

        btnSkipAddBillWaterOrganization.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkBoxWater.setChecked(false);
                if(MainActivity.chkBoxElectricity.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddElectricity()).addToBackStack(null).commit();
                }
                else if(MainActivity.chkBoxInternet.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddInternet()).addToBackStack(null).commit();
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
    private boolean validateWaterBillOrganizationName(){
        Integer billWaterOrganizationNameInput = spBillWaterOrganizations.getSelectedItemPosition();
        String aux0 = String.valueOf(billWaterOrganizationNameInput);
        if ("0".equals(aux0)){
            return false;
        } else {
            return true;
        }
    }

    private boolean validateWaterAccountNumber() {
        String WaterAccountNumberInput = textWaterAccountNumber.getEditText().getText().toString().trim();
        if (WaterAccountNumberInput.isEmpty()) {
            textWaterAccountNumber.setError("Field can't be empty");
            return false;
        } else {
            textWaterAccountNumber.setError(null);
            return true;
        }
    }

    private boolean validateWaterPaymentOptionMethod(){
        Integer WaterPaymentMethodOptionInput = spWaterPaymentMethodSelection.getSelectedItemPosition();
        String aux1 = String.valueOf(WaterPaymentMethodOptionInput);
        if ("0".equals(aux1)){
            return false;
        } else {
            return true;
        }
    }

    /*public void addBillWaterOrganizationInfo(View v){

        if (!validateWaterAccountNumber()) {
            return;
        } else if (validateWaterBillOrganizationName()) {
            Toast toast0 = new Toast(getApplicationContext());
            toast0.setGravity(Gravity.CENTER_VERTICAL,0, -200);
            TextView tv = new TextView(AddWaterOrganization.this);
            tv.setBackgroundColor(Color.DKGRAY);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(20);
            tv.setText("Select a valid organization!");
            toast0.setView(tv);
            toast0.show();
            return;
        }
        else if (validateWaterPaymentOptionMethod()) {
            Toast toast1 = new Toast(getApplicationContext());
            toast1.setGravity(Gravity.CENTER_VERTICAL,0, -200);
            TextView tv = new TextView(AddWaterOrganization.this);
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
        TextView tv = new TextView(AddWaterOrganization.this);
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setText("Bill information registered!");
        toast2.setView(tv);
        toast2.show();
        return;
    }*/
}

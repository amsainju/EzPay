package com.example.arpan.ezpay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Intent;
import android.database.Cursor;

public class AddAccountDetails extends Fragment {

    private TextInputLayout textBankName;
    private TextInputLayout textBankAccountNumber;
    private TextInputLayout textBankRoutingNumber;
    private TextInputLayout textBankSaveAs;
    private Button btnSkipAddBankAccount,btnAddBankAccount;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    public AddAccountDetails() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Add Bank Account");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_account_details, container, false);

        textBankName = view.findViewById(R.id.txtBankName);
        textBankAccountNumber = view.findViewById(R.id.txtBankAccountNumber);
        textBankRoutingNumber = view.findViewById(R.id.txtBankRoutingNumber);
        textBankSaveAs = view.findViewById(R.id.txtBankSaveAs);
        databaseHelper=new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        btnSkipAddBankAccount = view.findViewById(R.id.btnSkipAddBankAccount);
        btnAddBankAccount = view.findViewById(R.id.btnAddBankAccount);
        btnAddBankAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!validateBankName() | !validateBankAccountNumber() | !validateBankRoutingNumber() | !validateBankSaveAs()) {
                    return;
                }
                String bankName =textBankName.getEditText().getText().toString().trim();
                String alias = textBankSaveAs.getEditText().getText().toString().trim();
                String rn = textBankRoutingNumber.getEditText().getText().toString().trim();
                String an = textBankAccountNumber.getEditText().getText().toString().trim();
                MainActivity.chkBoxBank.setChecked(false);
                boolean insertData = databaseHelper.addPaymentMethod("Bank", bankName,alias,rn,an,null,null,null,null,null,null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage("Do you want to add Another Bank Account?")
                        .setCancelable(false)
                        .setPositiveButton("Add Another", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddAccountDetails()).addToBackStack(null).commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(MainActivity.chkBoxCreditCard.isChecked()){
                                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddCreditCard()).addToBackStack(null).commit();
                                    // do something
                                }
                                else if(MainActivity.chkBoxPaypal.isChecked()){
                                     MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddPayPal()).addToBackStack(null).commit();
                                    // do something
                                }
                                else if(MainActivity.chkBoxVenmo.isChecked()){
                                     MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddVenmo()).addToBackStack(null).commit();
                                    // do something
                                }
                                else{
                                    if(MainActivity.chkAddOrganization) {
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddOrganizations()).addToBackStack(null).commit();
                                    }
                                    else if(MainActivity.chkListPaymentMethod){
                                        MainActivity.chkListPaymentMethod =false;
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new PaymentMethodList()).addToBackStack(null).commit();
                                    }
                                    else{
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                                    }
                                }
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle(alias + " Account Sucessfully Added!");
                alert.show();




            }
        });

        btnSkipAddBankAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkBoxBank.setChecked(false);
                if(MainActivity.chkBoxCreditCard.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddCreditCard()).addToBackStack(null).commit();
                    // do something
                }
                else if(MainActivity.chkBoxPaypal.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddPayPal()).addToBackStack(null).commit();
                    // do something
                }
                else if(MainActivity.chkBoxVenmo.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddVenmo()).addToBackStack(null).commit();
                    // do something
                }
                else{
                    if(MainActivity.chkAddOrganization) {
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddOrganizations()).addToBackStack(null).commit();
                    }
                    else if(MainActivity.chkListPaymentMethod){
                        MainActivity.chkListPaymentMethod =false;
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new PaymentMethodList()).addToBackStack(null).commit();
                    }
                    else{
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                    }
                }

            }
        });
        return view;
    }

    public boolean validateBankName(){
        String bankNameInput = textBankName.getEditText().getText().toString().trim();
        if (bankNameInput.isEmpty()) {
            textBankName.setError("Field can't be empty");
            return false;
        } else {
            textBankName.setError(null);
            return true;
        }
    }

    public boolean validateBankAccountNumber(){
        String bankAccountNumberInput = textBankAccountNumber.getEditText().getText().toString().trim();
        if (bankAccountNumberInput.isEmpty()) {
            textBankAccountNumber.setError("Field can't be empty");
            return false;
        } else if (bankAccountNumberInput.length()> 12) {
            textBankAccountNumber.setError("Bank account number may be incorrect");
            return false;
        } else {
            textBankAccountNumber.setError(null);
            return true;
        }
    }

    public boolean validateBankRoutingNumber(){
        String bankRoutingNumberInput = textBankRoutingNumber.getEditText().getText().toString().trim();
        if (bankRoutingNumberInput.isEmpty()) {
            textBankRoutingNumber.setError("Field can't be empty");
            return false;
        } else if (bankRoutingNumberInput.length()> 9) {
            textBankRoutingNumber.setError("Routing number number may be incorrect");
            return false;
        } else {
            textBankRoutingNumber.setError(null);
            return true;
        }
    }

    public boolean validateBankSaveAs(){
        String bankSaveAsInput = textBankSaveAs.getEditText().getText().toString().trim();
        if (bankSaveAsInput.isEmpty()) {
            textBankSaveAs.setError("Field can't be empty");
            return false;
        } else {
            textBankSaveAs.setError(null);
            return true;
        }
    }

    public void addBankAccountInfo(View v){
        if (!validateBankName() | !validateBankAccountNumber() | !validateBankRoutingNumber() | !validateBankSaveAs()) {
            return;
        }
        Toast toast1 = new Toast(v.getContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0, 450);

        TextView tv = new TextView(v.getContext());
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setPadding(10,10,10,10);

        tv.setText("Banck account registered!");
        toast1.setView(tv);
        toast1.show();
    }


}

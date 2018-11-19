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

public class AddAccountDetails extends Fragment {

    private TextInputLayout textBankName;
    private TextInputLayout textBankAccountNumber;
    private TextInputLayout textBankRoutingNumber;
    private TextInputLayout textBankSaveAs;
    private Button btnSkipAddBankAccount,btnAddBankAccount;
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
        btnSkipAddBankAccount = view.findViewById(R.id.btnSkipAddBankAccount);
        btnAddBankAccount = view.findViewById(R.id.btnAddBankAccount);
        btnAddBankAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {




            }
        });

        btnSkipAddBankAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MainActivity.chkBoxCreditCard.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddCreditCard()).addToBackStack(null).commit();
                    // do something
                }
                else if(MainActivity.chkBoxPaypal.isChecked()){
                   // MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddAccountDetails()).addToBackStack(null).commit();
                    // do something
                }
                else if(MainActivity.chkBoxVenmo.isChecked()){
                   // MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddAccountDetails()).addToBackStack(null).commit();
                    // do something
                }
                else{
                    //MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddOrganizationDetails()).addToBackStack(null).commit();

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

package com.example.arpan.ezpay;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddPayPal extends Fragment {

    private TextInputLayout textPaypal;
    private TextInputLayout textPaypalAccount;
    private TextInputLayout textPaypalPassword;
    private TextInputLayout textPaypalSaveAs;
    private Button btnAddPaypal, btnSkipAddPaypal;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    public AddPayPal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Add Paypal");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_pay_pal, container, false);

        textPaypal = view.findViewById(R.id.txtPaypal);
        textPaypalAccount = view.findViewById(R.id.txtPaypalAccount);
        textPaypalPassword = view.findViewById(R.id.txtPaypalPassword);
        textPaypalSaveAs = view.findViewById(R.id.txtPaypalSaveAs);

        databaseHelper=new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        btnAddPaypal = view.findViewById(R.id.btnAddPaypal);
        btnSkipAddPaypal = view.findViewById(R.id.btnSkipAddPaypal);
        btnAddPaypal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!validatePayPalAccount() | !validatePayPalPassword() | !validatePayPalSaveAs()) {
                    return;
                }
                String paypalAccount = textPaypalAccount.getEditText().getText().toString().trim();
                String paypalPassowrd =textPaypalPassword.getEditText().getText().toString().trim();
                 String alias = textPaypalSaveAs.getEditText().getText().toString().trim();
                MainActivity.chkBoxPaypal.setChecked(false);
                boolean insertData = databaseHelper.addPaymentMethod("Paypal", null,alias,null,null,null,null,null,null,paypalAccount,paypalPassowrd);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage(" ")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(MainActivity.chkBoxVenmo.isChecked()){
                                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddVenmo()).addToBackStack(null).commit();
                                    // do something
                                }
                                else{
                                    if(MainActivity.chkAddOrganization) {
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddOrganizations()).addToBackStack(null).commit();
                                    }
                                    else if(MainActivity.chkListPaymentMethod){
                                        MainActivity.chkListPaymentMethod = false;
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new PaymentMethodList()).addToBackStack(null).commit();
                                    }
                                    else{
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                                    }

                                }
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle("Payapal Account Sucessfully Added!");
                alert.show();




            }
        });

        btnSkipAddPaypal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkBoxPaypal.setChecked(false);
                if(MainActivity.chkBoxVenmo.isChecked()){
                     MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddVenmo()).addToBackStack(null).commit();
                    // do something
                }
                else{
                    if(MainActivity.chkAddOrganization) {
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddOrganizations()).addToBackStack(null).commit();
                    }
                    else if(MainActivity.chkListPaymentMethod){
                        MainActivity.chkListPaymentMethod = false;
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

  //  private boolean validatePayPal(){
   //     String payPalInput = textPaypal.getEditText().getText().toString().trim();
    //    if (payPalInput.isEmpty()) {
     //       textPaypal.setError("Field can't be empty");
      //      return false;
       // } else {
        //    textPaypal.setError(null);
         //   return true;
        //}
    //}

    private boolean validatePayPalAccount(){
        String payPalAccountInput = textPaypalAccount.getEditText().getText().toString().trim();
        if (payPalAccountInput.isEmpty()) {
            textPaypalAccount.setError("Field can't be empty");
            return false;
        } else {
            textPaypalAccount.setError(null);
            return true;
        }
    }

    private boolean validatePayPalPassword(){
        String payPalPasswordInput = textPaypalPassword.getEditText().getText().toString().trim();
        if (payPalPasswordInput.isEmpty()) {
            textPaypalPassword.setError("Field can't be empty");
            return false;
        } else {
            textPaypalPassword.setError(null);
            return true;
        }
    }

    private boolean validatePayPalSaveAs(){
        String payPalSaveAsInput = textPaypalSaveAs.getEditText().getText().toString().trim();
        if (payPalSaveAsInput.isEmpty()) {
            textPaypalSaveAs.setError("Field can't be empty");
            return false;
        } else {
            textPaypalSaveAs.setError(null);
            return true;
        }
    }

    public void addPayPalInfo(View v){
        if (!validatePayPalAccount() | !validatePayPalPassword() | !validatePayPalSaveAs()) {
            return;
        }
        Toast toast1 = new Toast(getContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0, 450);

        TextView tv = new TextView(getContext());
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setPadding(10,10,10,10);

        tv.setText("PayPal account registered!");
        toast1.setView(tv);
        toast1.show();
    }

}

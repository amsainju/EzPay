package com.example.arpan.ezpay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddVenmo extends Fragment {

    private TextInputLayout textVenmo;
    private TextInputLayout textVenmoAccount;
    private TextInputLayout textVenmoPassword;
    private TextInputLayout textVenmoSaveAs;
    private Button btnAddVenmo, btnSkipAddVenmo;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    public AddVenmo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Add Venmo");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_venmo, container, false);



        textVenmo = view.findViewById(R.id.txtVenmo);
        textVenmoAccount = view.findViewById(R.id.txtVenmoAccount);
        textVenmoPassword = view.findViewById(R.id.txtVenmoPassword);
        textVenmoSaveAs = view.findViewById(R.id.txtVenmoSaveAs);
        databaseHelper=new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        btnAddVenmo = view.findViewById(R.id.btnAddVenmo);
        btnSkipAddVenmo = view.findViewById(R.id.btnSkipAddVenmo);
        btnAddVenmo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!validateVenmoAccount() | !validateVenmoPassword() | !validateVenmoSaveAs()) {
                    return;
                }
                MainActivity.chkBoxVenmo.setChecked(false);
                String account = textVenmoAccount.getEditText().getText().toString().trim();
                String passowrd =textVenmoPassword.getEditText().getText().toString().trim();
                String alias = textVenmoSaveAs.getEditText().getText().toString().trim();
                boolean insertData = databaseHelper.addPaymentMethod("Venmo", null,alias,null,null,null,null,null,null,account,passowrd);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage(" ")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle("Venmo Account Sucessfully Added!");
                alert.show();




            }
        });

        btnSkipAddVenmo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    MainActivity.chkBoxVenmo.setChecked(false);
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
        });
        return view;
    }

    private boolean validateVenmo(){
        String venmoInput = textVenmo.getEditText().getText().toString().trim();
        if (venmoInput.isEmpty()) {
            textVenmo.setError("Field can't be empty");
            return false;
        } else {
            textVenmo.setError(null);
            return true;
        }
    }

    private boolean validateVenmoAccount(){
        String venmoAccountInput = textVenmoAccount.getEditText().getText().toString().trim();
        if (venmoAccountInput.isEmpty()) {
            textVenmoAccount.setError("Field can't be empty");
            return false;
        } else {
            textVenmoAccount.setError(null);
            return true;
        }
    }

    private boolean validateVenmoPassword(){
        String venmoPasswordInput = textVenmoPassword.getEditText().getText().toString().trim();
        if (venmoPasswordInput.isEmpty()) {
            textVenmoPassword.setError("Field can't be empty");
            return false;
        } else {
            textVenmoPassword.setError(null);
            return true;
        }
    }

    private boolean validateVenmoSaveAs(){
        String venmoSaveAsInput = textVenmoSaveAs.getEditText().getText().toString().trim();
        if (venmoSaveAsInput.isEmpty()) {
            textVenmoSaveAs.setError("Field can't be empty");
            return false;
        } else {
            textVenmoSaveAs.setError(null);
            return true;
        }
    }

    public void addVenmoInfo(View v){
        if (!validateVenmo() | !validateVenmoAccount() | !validateVenmoPassword() | !validateVenmoSaveAs()) {
            return;
        }
        Toast toast1 = new Toast(getContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0, 450);

        TextView tv = new TextView(getContext());
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setPadding(10,10,10,10);

        tv.setText("Venmo account registered!");
        toast1.setView(tv);
        toast1.show();
    }
}

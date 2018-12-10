package com.example.arpan.ezpay;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddCreditCard extends Fragment {

    private static final String TAG = "AddCreditCard";
    private TextInputEditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private TextInputLayout textCreditCardNumber,textNameOnCreditCard;
    private TextInputLayout textCreditCardSecurityCode;
    private TextInputLayout textCreditCardExpirationDate;
    private TextInputLayout textCreditCardSaveAs;
    private Button  btnAddCreditCard, btnSkipAddCreditCard;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    public AddCreditCard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Add Credit Card");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_credit_card, container, false);

;
        textNameOnCreditCard = view.findViewById(R.id.txtNameOnCreditCard);
        textCreditCardNumber = view.findViewById(R.id.txtCreditCardNumber);
        textCreditCardSecurityCode = view.findViewById(R.id.txtCreditCardSecurityCode);
        textCreditCardExpirationDate = view.findViewById(R.id.txtCreditCardExpirationDate);
        textCreditCardSaveAs = view.findViewById(R.id.txtCreditCardSaveAs);

        mDisplayDate = (TextInputEditText) view.findViewById(R.id.txtInputCreditCardExpirationDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        view.getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/yyyy: " + month + "/" + year);
                String date = + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };
        databaseHelper=new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        btnAddCreditCard = view.findViewById(R.id.btnAddCreditCard);
        btnSkipAddCreditCard = view.findViewById(R.id.btnSkipAddCreditCard);
        btnAddCreditCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!validateNameOnCreditCard() | !validateCreditCardNumber() | !validateCreditCardSecurityCode() | !validateCreditCardExpirationDate() | !validateCreditCardSaveAs()) {
                    return;
                }
                String nameOnCreditCard = textNameOnCreditCard.getEditText().getText().toString().trim();
                String cardNumber =textCreditCardNumber.getEditText().getText().toString().trim();
                String  secirityCode= textCreditCardSecurityCode.getEditText().getText().toString().trim();
                String expirtDate = textCreditCardExpirationDate.getEditText().getText().toString().trim();
                String alias = textCreditCardSaveAs.getEditText().getText().toString().trim();
                MainActivity.chkBoxCreditCard.setChecked(false);
                boolean insertData = databaseHelper.addPaymentMethod("CreditCard", null,alias,null,null,nameOnCreditCard,cardNumber,expirtDate,secirityCode,null,null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage("Do you want to add Another Credit Card Account?")
                        .setCancelable(false)
                        .setPositiveButton("Add Another", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddCreditCard()).addToBackStack(null).commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(MainActivity.chkBoxPaypal.isChecked()){
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
                                        MainActivity.chkBoxBank.setChecked(false);
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new PaymentMethodList()).addToBackStack(null).commit();
                                    }
                                    else{
                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                                    }
                                }
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle(alias + " Sucessfully Added!");
                alert.show();




            }
        });

        btnSkipAddCreditCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkBoxCreditCard.setChecked(false);
                 if(MainActivity.chkBoxPaypal.isChecked()){
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

    private boolean validateNameOnCreditCard(){
        String bankNameInput = textNameOnCreditCard.getEditText().getText().toString().trim();
        if (bankNameInput.isEmpty()) {
            textNameOnCreditCard.setError("Field can't be empty");
            return false;
        } else {
            textNameOnCreditCard.setError(null);
            return true;
        }
    }
    private boolean validateCreditCardNumber(){
        String creditCardNumberInput = textCreditCardNumber.getEditText().getText().toString().trim();
        if (creditCardNumberInput.isEmpty()) {
            textCreditCardNumber.setError("Field can't be empty");
            return false;
        } else if (creditCardNumberInput.length()> 16) {
            textCreditCardNumber.setError("Credit card number may be incorrect");
            return false;
        } else {
            textCreditCardNumber.setError(null);
            return true;
        }
    }

    private boolean validateCreditCardSecurityCode(){
        String creditCardSecurityCodeInput = textCreditCardSecurityCode.getEditText().getText().toString().trim();
        if (creditCardSecurityCodeInput.isEmpty()) {
            textCreditCardSecurityCode.setError("Field can't be empty");
            return false;
        } else if (creditCardSecurityCodeInput.length()>4) {
            textCreditCardSecurityCode.setError("Security ID or CVV too long");
            return false;
        } else {
            textCreditCardSecurityCode.setError(null);
            return true;
        }
    }

    private boolean validateCreditCardExpirationDate(){
        String creditCardExpirationDateInput = textCreditCardExpirationDate.getEditText().getText().toString().trim();
        if (creditCardExpirationDateInput.isEmpty()) {
            textCreditCardExpirationDate.setError("Field can't be empty");
            return false;
        } else {
            textCreditCardExpirationDate.setError(null);
            return true;
        }
    }

    private boolean validateCreditCardSaveAs(){
        String creditCardSaveAsInput = textCreditCardSaveAs.getEditText().getText().toString().trim();
        if (creditCardSaveAsInput.isEmpty()) {
            textCreditCardSaveAs.setError("Field can't be empty");
            return false;
        } else {
            textCreditCardSaveAs.setError(null);
            return true;
        }
    }

    public void addCreditCardInfo(View v){
        if (!validateCreditCardNumber() | !validateCreditCardSecurityCode() | !validateCreditCardExpirationDate() | !validateCreditCardSaveAs()) {
            return;
        }
        Toast toast1 = new Toast(getContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0, 450);

        TextView tv = new TextView(getContext());
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setPadding(10,10,10,10);

        tv.setText("Credit card registered!");
        toast1.setView(tv);
        toast1.show();
    }

}
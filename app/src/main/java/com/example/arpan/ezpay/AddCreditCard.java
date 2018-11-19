package com.example.arpan.ezpay;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddCreditCard extends AppCompatActivity {

    private static final String TAG = "AddCreditCard";
    private TextInputEditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private TextInputLayout textCreditCardNumber;
    private TextInputLayout textCreditCardSecurityCode;
    private TextInputLayout textCreditCardExpirationDate;
    private TextInputLayout textCreditCardSaveAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);

        textCreditCardNumber = findViewById(R.id.txtCreditCardNumber);
        textCreditCardSecurityCode = findViewById(R.id.txtCreditCardSecurityCode);
        textCreditCardExpirationDate = findViewById(R.id.txtCreditCardExpirationDate);
        textCreditCardSaveAs = findViewById(R.id.txtCreditCardSaveAs);

        mDisplayDate = (TextInputEditText) findViewById(R.id.txtInputCreditCardExpirationDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddCreditCard.this,
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
        Toast toast1 = new Toast(getApplicationContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0, 450);

        TextView tv = new TextView(AddCreditCard.this);
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setPadding(10,10,10,10);

        tv.setText("Credit card registered!");
        toast1.setView(tv);
        toast1.show();
    }

}
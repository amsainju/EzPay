package com.example.arpan.ezpay;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddAccountDetails extends AppCompatActivity {

    private TextInputLayout textBankName;
    private TextInputLayout textBankAccountNumber;
    private TextInputLayout textBankRoutingNumber;
    private TextInputLayout textBankSaveAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_details);

        textBankName = findViewById(R.id.txtBankName);
        textBankAccountNumber = findViewById(R.id.txtBankAccountNumber);
        textBankRoutingNumber = findViewById(R.id.txtBankRoutingNumber);
        textBankSaveAs = findViewById(R.id.txtBankSaveAs);

    }

    private boolean validateBankName(){
        String bankNameInput = textBankName.getEditText().getText().toString().trim();
        if (bankNameInput.isEmpty()) {
            textBankName.setError("Field can't be empty");
            return false;
        } else {
            textBankName.setError(null);
            return true;
        }
    }

    private boolean validateBankAccountNumber(){
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

    private boolean validateBankRoutingNumber(){
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

    private boolean validateBankSaveAs(){
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
        Toast toast1 = new Toast(getApplicationContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0, 450);

        TextView tv = new TextView(AddAccountDetails.this);
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setPadding(10,10,10,10);

        tv.setText("Banck account registered!");
        toast1.setView(tv);
        toast1.show();
    }

}

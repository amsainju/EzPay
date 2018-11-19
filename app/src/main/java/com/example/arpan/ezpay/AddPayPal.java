package com.example.arpan.ezpay;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddPayPal extends AppCompatActivity {

    private TextInputLayout textPaypal;
    private TextInputLayout textPaypalAccount;
    private TextInputLayout textPaypalPassword;
    private TextInputLayout textPaypalSaveAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pay_pal);

        textPaypal = findViewById(R.id.txtPaypal);
        textPaypalAccount = findViewById(R.id.txtPaypalAccount);
        textPaypalPassword = findViewById(R.id.txtPaypalPassword);
        textPaypalSaveAs = findViewById(R.id.txtPaypalSaveAs);
    }

    private boolean validatePayPal(){
        String payPalInput = textPaypal.getEditText().getText().toString().trim();
        if (payPalInput.isEmpty()) {
            textPaypal.setError("Field can't be empty");
            return false;
        } else {
            textPaypal.setError(null);
            return true;
        }
    }

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
        if (!validatePayPal() | !validatePayPalAccount() | !validatePayPalPassword() | !validatePayPalSaveAs()) {
            return;
        }
        Toast toast1 = new Toast(getApplicationContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0, 450);

        TextView tv = new TextView(AddPayPal.this);
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setPadding(10,10,10,10);

        tv.setText("PayPal account registered!");
        toast1.setView(tv);
        toast1.show();
    }

}

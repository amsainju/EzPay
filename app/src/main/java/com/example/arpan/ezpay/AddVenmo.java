package com.example.arpan.ezpay;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddVenmo extends AppCompatActivity {

    private TextInputLayout textVenmo;
    private TextInputLayout textVenmoAccount;
    private TextInputLayout textVenmoPassword;
    private TextInputLayout textVenmoSaveAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venmo);

        textVenmo = findViewById(R.id.txtVenmo);
        textVenmoAccount = findViewById(R.id.txtVenmoAccount);
        textVenmoPassword = findViewById(R.id.txtVenmoPassword);
        textVenmoSaveAs = findViewById(R.id.txtVenmoSaveAs);
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
        Toast toast1 = new Toast(getApplicationContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0, 450);

        TextView tv = new TextView(AddVenmo.this);
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setPadding(10,10,10,10);

        tv.setText("Venmo account registered!");
        toast1.setView(tv);
        toast1.show();
    }
}

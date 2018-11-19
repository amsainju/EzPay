package com.example.arpan.ezpay;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup_page extends AppCompatActivity {
    //SQLiteOpenHelper openHelper;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Button _btnRegister, _btnCancel;
    EditText _txtFname, _txtLname, _txtEmail, _txtPassword;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        //openHelper = new DatabaseHelper(this);
        databaseHelper = new DatabaseHelper(this);
        _txtFname = (EditText)findViewById(R.id.txtFirstName);
        _txtLname = (EditText)findViewById(R.id.txtLastName);
        _txtEmail = (EditText)findViewById(R.id.txtEmail);
        _txtPassword = (EditText)findViewById(R.id.txtPassword);
        _btnRegister=(Button)findViewById(R.id.btnRegisterUser);
        _btnCancel=(Button)findViewById(R.id.btnCancelRegister);

        _btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db=openHelper.getWritableDatabase();
                String fname=_txtFname.getText().toString();
                String lname=_txtLname.getText().toString();
                String email=_txtEmail.getText().toString();
                String pass=_txtPassword.getText().toString();

                if (validateData(fname, lname, email, pass)) {
                    AddData(fname, lname, email, pass);
                    //_txtFname.setText("");
                } else {
                    toastMessage("Please fill the missing field!!");
                }
                //insertdata(fname, lname,email,pass, v);
            }


/*            public void insertdata(String fname, String lname, String email, String pass, View v){
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.FirstName, fname);
                contentValues.put(DatabaseHelper.LastName, lname);
                contentValues.put(DatabaseHelper.Email, email);
                contentValues.put(DatabaseHelper.Password, pass);
                long id = db.insert(DatabaseHelper.TBLUser, null, contentValues);
            if(id==-1)
            {
                Toast.makeText(v.getContext(), "User Registration Failed",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(v.getContext(), "User registered Successfully",Toast.LENGTH_LONG).show();
                //To Do check if inserted successfully
                Intent intent = new Intent(v.getContext(),com.example.arpan.ezpay.login_page.class);
                finish();
                startActivityForResult(intent,0);
            }

            }*/

        });


        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),login_page.class);
                finish();
                startActivityForResult(intent,0);
            }
        });

    }

    public void AddData(String fname, String lname, String email, String pass) {
        boolean insertData = databaseHelper.addData(fname, lname, email, pass);

        if (insertData) {
            toastMessage("User Successfully Registered!");
            Intent intent = new Intent(getApplicationContext(),login_page.class);
            finish();
            startActivityForResult(intent,0);
        } else {
            toastMessage("User Registration Failed!!");
        }
    }

    public boolean validateData(String fname, String lname, String email, String pass) {
        if (fname.length() == 0) {
            return false;
        }
        else if (lname.length() == 0) {
            return false;
        }
        else if (email.length() == 0){
            return false;
        }
        else if (pass.length() == 0){
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}

package com.example.arpan.ezpay;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class login_page extends AppCompatActivity {
    //SQLiteOpenHelper openHelper;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String tblName, strSql;
    Button _btnLogin, _btnSignUp;
    CheckBox _chkRememberMe;
    EditText _txtEmail, _txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //openHelper=new DatabaseHelper(this);
        databaseHelper=new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();
        _txtEmail=(EditText)findViewById(R.id.txtEmail);
        _txtPass=(EditText)findViewById(R.id.txtPassword);
        _btnLogin=(Button)findViewById(R.id.btnLogin); // Need to check
        _btnSignUp=(Button)findViewById(R.id.btnSignUp); // Need to check
        _chkRememberMe =(CheckBox)findViewById(R.id.chkRememberMe);

        cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TBLUser + " WHERE " + DatabaseHelper.RememberMe + "=?" , new String[]{"Yes"});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String email = cursor.getString(cursor.getColumnIndex("Email"));
            cursor.moveToFirst();
            String pass  = cursor.getString(cursor.getColumnIndex("Password"));
            _txtEmail.setText(email);
            _txtPass.setText(pass);
            _chkRememberMe.setChecked(true);
            //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            //finish();
            //startActivityForResult(intent,0);
        }
        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = _txtEmail.getText().toString();
                String pass = _txtPass.getText().toString();
                if(!email.isEmpty()&& !pass.isEmpty()) {
                    //tblName = db.rawQuery("SELECT NAME FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", DatabaseHelper.TBLUser}).toString();
                    cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", DatabaseHelper.TBLUser});
                    if (!cursor.moveToFirst())
                    {
                        cursor.close();
                        Toast.makeText(v.getContext(), "User not registered!! Please click sign up button to register.", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TBLUser + " WHERE " + DatabaseHelper.Email + "=? AND " + DatabaseHelper.Password + "=?", new String[]{email, pass});
                        if (cursor != null) {
                            if (cursor.getCount() > 0) {
                                if(_chkRememberMe.isChecked()) {
                                    databaseHelper.updateRememberMe(email, pass,"Yes");
                                }
                                else
                                {
                                    databaseHelper.updateRememberMe(email, pass,"No");
                                }


                                cursor.moveToNext();
                                Toast.makeText(v.getContext(), "Login Success", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(v.getContext(),MainActivity.class);
                                finish();
                                startActivityForResult(intent,0);

                            } else {
                                Toast.makeText(v.getContext(), "Login error: UserEmail or Password Mismatch!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(v.getContext(), "Login error: UserEmail or Password Mismatch!!", Toast.LENGTH_LONG).show();
                }

            }
        });

        _btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),signup_page.class);
                finish();
                startActivityForResult(intent,0);
            }
        });



    }

}

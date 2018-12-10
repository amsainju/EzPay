package com.example.arpan.ezpay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    //region Class Variables

    private Context context;
    private String createTable;
    public static final String DATABASE_NAME = "dbEzPay.db";
    public static final String TAG = "DatabaseHelper";

    //region Tables
    public static final String TBLUser = "tblUser";
    public static final String TBLPaymentMethod = "tblPaymentMethod";
    public static final String TBLOrganization = "tblOrganization";
    public static final String TBLCurrentBills = "tblCurrentBills";
    //endregion Tables

    //region UserTable Columns
    public static final String ID = "ID";
    public static final String FirstName = "FirstName";
    public static final String LastName = "LastName";
    public static final String Email = "Email";
    public static final String Password = "Password";
    public static final String RememberMe = "RememberMe";
    //endregion UserTable Columns

    //region PaymentMethodTable Columns
    public static final String PMID = "ID";
    public static final String BankName = "BankName";
    public static final String PMType = "PaymentType";
    public static final String PMSaveAsName = "SaveAsName";
    //----For Back Accounts----//
    public static final String PMRoutingNum = "RoutingNumber";
    public static final String PMAccNum = "AccountNumber";

    //----For Credit Cards----//
    public static final String PMNameOnCard = "NameOnCard";
    public static final String PMCardNum = "CardNumber";
    public static final String PMExpiryDate = "ExpiryDate";
    public static final String PMSecNum = "SecurityNumber";

    //------For Venmo/Paypal------//
    public static final String PMUserName = "UserName";
    public static final String PMPass = "Password";

    //endregion PaymentMethodTable Columns

    //region OrganizationTable Columns
    public static final String OrgID = "ID";
    public static final String OrgType = "OrganizationType";
    public static final String OrgName = "OrganizationName";
    public static final String OrgAutoPayment = "IsAutoPayment";
    public static final String OrgPreferredPaymentMethod = "PreferredPaymentMethod";
    //---For Water and Electricity---//
    public static final String OrgAccNum = "AccountNumber";
    public static final String OrgCusNum= "CustomerNumber";
    //-----For Internet-----//
    public static final String OrgUserEmail = "UserEmail";
    public static final String OrgPass = "Password";
    //endregion OrganizationTable Columns

    // region CurrentBillsTable Columns
    public static final String CBID = "ID";
    public static final String CB_OrgType = "OrgType";
    //----PMID, Amount, PaymentDate, Status to show Paid Bills-----//
    public static final String CB_PMSaveAsName = "PMSaveAsName";   //corrosponds PMSaveAsName
    public static final String CB_Amt = "Amount";
    public static final String CB_DueDate = "DueDate";
    public static final String CB_PMDate = "PaymentDate";
    public static final String CB_LastReading = "LastReading";
    public static final String CB_CurReading = "CurrentReading";
    public static final String CB_IsPaid = "IsPaid";
    //endregion CurrentBillsTable Columns

    //endregion Class Variables

    //region Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }
    //endregion Constructor

    //region Events
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE IF NOT EXISTS " + TBLUser + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FirstName TEXT, LastName TEXT, Email TEXT, Password TEXT)");
        /*String createTable = "CREATE TABLE " + TBLUser + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ FirstName +" TEXT, "+ LastName +" TEXT, "+ Email +" TEXT, "+ Password +" TEXT, "+ RememberMe +" TEXT)";
        db.execSQL(createTable);*/
        createUserTable(db);
        createPaymentMethodTable(db);
        createOrganizationTable(db);
        createCurrentBillsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TBLUser); //Drop older table if exists
        onCreate(db);
    }

    //endregion Events

    //region CreateTables
    public void createUserTable(SQLiteDatabase db) {
        createTable = "CREATE TABLE " + TBLUser + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ FirstName +" TEXT, "+ LastName +" TEXT, "+ Email +" TEXT, "
                + Password +" TEXT, "+ RememberMe +" TEXT)";
        db.execSQL(createTable);
    }
    public void createPaymentMethodTable(SQLiteDatabase db) {
        createTable = "CREATE TABLE " + TBLPaymentMethod + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ PMType +" TEXT, "+ BankName +" TEXT, "+ PMSaveAsName +" TEXT, "+ PMRoutingNum +" TEXT, "+ PMAccNum +" TEXT, "
                + PMNameOnCard +" TEXT, "+ PMCardNum +" TEXT, "+ PMExpiryDate +" TEXT, "+ PMSecNum +" TEXT, "+ PMUserName +" TEXT, "+ PMPass +" TEXT)";
        db.execSQL(createTable);
    }
    public void createOrganizationTable(SQLiteDatabase db) {
        createTable = "CREATE TABLE " + TBLOrganization + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + OrgType + " TEXT, " + OrgName + " TEXT, " + OrgAutoPayment + " TEXT, "
               + OrgPreferredPaymentMethod + " TEXT, "+ OrgAccNum + " TEXT, " + OrgCusNum + " TEXT, " + OrgUserEmail + " TEXT, " + OrgPass + " TEXT)";
        db.execSQL(createTable);
    }

    public void createCurrentBillsTable(SQLiteDatabase db) {
        createTable = "CREATE TABLE " + TBLCurrentBills + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ CB_OrgType +" INTEGER, "+ CB_PMSaveAsName +" INTEGER, "+ CB_Amt +" TEXT, "
                + CB_DueDate +" TEXT, "+ CB_PMDate +" TEXT, "+ CB_LastReading +" TEXT, "+ CB_CurReading +" TEXT, "+ CB_IsPaid +" TEXT)";
        db.execSQL(createTable);
    }
    //endregion CreateTables

    //region UserData
    //add Bank Account
    public boolean addData(String fname, String lname, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FirstName, fname);
        contentValues.put(LastName, lname);
        contentValues.put(Email, email);
        contentValues.put(Password, pass);
        contentValues.put(RememberMe, "No");

        Log.d(TAG, "addData: Adding " + fname +", "+ lname+","+ email+" and"+pass+ " to " + TBLUser);

        long result = db.insert(TBLUser, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateRememberMe(String email, String pass, String rememberMe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(FirstName, fname);
        //contentValues.put(LastName, lname);
        //contentValues.put(Email, email);
        //contentValues.put(Password, pass);
        contentValues.put(RememberMe, rememberMe);

        Log.d(TAG, "updateData: Updating "+rememberMe+ " to " + TBLUser);

        long result = db.update(TBLUser,contentValues,DatabaseHelper.Email + "=? AND " + DatabaseHelper.Password + "=?", new String[]{email, pass});

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getUserData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TBLUser;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //endregion UserData

    //region PaymentMethodData

    public boolean addPaymentMethod(String pmType, String bName, String pmSaveAsName, String pmRoutingNum, String pmAccNum, String pmNameOnCard, String pmCardNum, String pmExpiryDate, String pmSecNum, String pmUserName, String pmPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PMType, pmType);
        contentValues.put(BankName,bName);
        contentValues.put(PMSaveAsName, pmSaveAsName);
        contentValues.put(PMRoutingNum, pmRoutingNum);
        contentValues.put(PMAccNum, pmAccNum);
        contentValues.put(PMNameOnCard, pmNameOnCard);
        contentValues.put(PMCardNum, pmCardNum);
        contentValues.put(PMExpiryDate, pmExpiryDate);
        contentValues.put(PMSecNum, pmSecNum);
        contentValues.put(PMUserName, pmUserName);
        contentValues.put(PMPass, pmPass);
        Log.d(TAG, "addData: Adding " + pmType +","+ BankName +","+ pmSaveAsName+","+ pmRoutingNum+", "+ pmAccNum+","+ pmNameOnCard+", "+ pmCardNum+","+ pmExpiryDate
                +", "+ pmSecNum+","+ pmUserName+" and"+pmPass+ " to " + TBLPaymentMethod);

        long result = db.insert(TBLPaymentMethod, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updatePaymentMethod(String pmId,String pmType, String pmSaveAsName, String pmRoutingNum, String pmAccNum, String pmNameOnCard, String pmCardNum, String pmExpiryDate, String pmSecNum, String pmUserName, String pmPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PMType, pmType);
        contentValues.put(PMSaveAsName, pmSaveAsName);
        contentValues.put(PMRoutingNum, pmRoutingNum);
        contentValues.put(PMAccNum, pmAccNum);
        contentValues.put(PMNameOnCard, pmNameOnCard);
        contentValues.put(PMCardNum, pmCardNum);
        contentValues.put(PMExpiryDate, pmExpiryDate);
        contentValues.put(PMSecNum, pmSecNum);
        contentValues.put(PMUserName, pmUserName);
        contentValues.put(PMPass, pmPass);
        Log.d(TAG, "updateData: Updating " + pmType +", "+ pmSaveAsName+","+ pmRoutingNum+", "+ pmAccNum+","+ pmNameOnCard+", "+ pmCardNum+","+ pmExpiryDate
                +", "+ pmSecNum+","+ pmUserName+" and"+pmPass+ " to " + TBLPaymentMethod);

        long result = db.update(TBLPaymentMethod, contentValues,DatabaseHelper.PMID + "=? ", new String[]{pmId});

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deletePaymentMethod(String pmId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deleteData: Deleting from " + TBLPaymentMethod + " by Payment ID");
        //String.valueOf(contact.getID())
        long result = db.delete(TBLPaymentMethod,DatabaseHelper.PMID + "=? ", new String[]{pmId});

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getPaymentMethodList(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TBLPaymentMethod + " ORDER BY ID ASC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPaymentMethodDetails(String pmId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TBLPaymentMethod + " WHERE "+ DatabaseHelper.PMID+ " = ?";
        Cursor data = db.rawQuery(query, new String[]{pmId});
        return data;
    }

    //endregion PaymentMethodData

    //region OrganizationData

    public boolean addOrganization(String orgType, String orgName, String orgAutoPayment, String orgPPayment, String orgAccNum, String orgCusNum, String orgUserEmail, String orgPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrgType, orgType);
        contentValues.put(OrgName, orgName);
        contentValues.put(OrgAutoPayment, orgAutoPayment);
        contentValues.put(OrgAccNum, orgAccNum);
        contentValues.put(OrgCusNum, orgCusNum);
        contentValues.put(OrgUserEmail, orgUserEmail);
        contentValues.put(OrgPass, orgPass);
        contentValues.put(OrgPreferredPaymentMethod, orgPPayment);
        Log.d(TAG, "addData: Adding " + orgType +", "+ orgName+","+ orgAutoPayment+","+","+ orgPPayment+","+ orgAccNum+","+ orgCusNum+","+ orgUserEmail+" and "+orgPass+ " to " + TBLOrganization);

        long result = db.insert(TBLOrganization, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateOrganization(String orgID, String orgType, String orgName, String orgAutoPayment, String orgAccNum, String orgCusNum, String orgUserEmail, String orgPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrgType, orgType);
        contentValues.put(OrgName, orgName);
        contentValues.put(OrgAutoPayment, orgAutoPayment);
        contentValues.put(OrgAccNum, orgAccNum);
        contentValues.put(OrgCusNum, orgCusNum);
        contentValues.put(OrgUserEmail, orgUserEmail);
        contentValues.put(OrgPass, orgPass);
        Log.d(TAG, "updateData: Updating "+ orgType +", "+ orgName+","+ orgAutoPayment+","+ orgAccNum+","+ orgCusNum+","+ orgUserEmail+" and "+orgPass+ " to " + TBLOrganization);

        long result = db.update(TBLOrganization,contentValues,DatabaseHelper.OrgID + "=?", new String[]{orgID});

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteOrganization(String orgID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deleteData: Deleting from " + TBLOrganization + " by Organization ID");
        //String.valueOf(contact.getID())
        long result = db.delete(TBLOrganization,DatabaseHelper.OrgID + "=? ", new String[]{orgID});

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor getOrganizationList(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TBLOrganization;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getOrganizationDetails(String orgID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TBLOrganization+ " WHERE "+ DatabaseHelper.OrgID+ " = ?";
        Cursor data = db.rawQuery(query, new String[]{orgID});
        return data;
    }
    //endregion OrganizationData

    //region BillsData
    public Cursor getCurrentBills(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TBLCurrentBills+ " WHERE "+ DatabaseHelper.CB_IsPaid+ " = 'No' ORDER BY "+DatabaseHelper.CB_DueDate+" DESC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getBillDetailsByID(String cbID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TBLCurrentBills+ " WHERE "+ DatabaseHelper.CBID+ " = ?";
        Cursor data = db.rawQuery(query,  new String[]{cbID});
        return data;
    }
    public Cursor getPaidBills(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TBLCurrentBills+ " WHERE "+ DatabaseHelper.CB_IsPaid+ " = 'Yes' ORDER BY "+DatabaseHelper.CB_PMDate+" ASC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean addCurrentBills(String cbOrgType, String cbPMSaveAsName, String cbAmt, String cbDueDate, String cbPMDate, String cbLastReading, String cbCurrReading, String cbIsPaid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CB_OrgType, cbOrgType);
        contentValues.put(CB_PMSaveAsName,cbPMSaveAsName);
        contentValues.put(CB_Amt, cbAmt);
        contentValues.put(CB_DueDate, cbDueDate);
        contentValues.put(CB_PMDate, cbPMDate);
        contentValues.put(CB_LastReading, cbLastReading);
        contentValues.put(CB_CurReading, cbCurrReading);
        contentValues.put(CB_IsPaid, cbIsPaid);
        Log.d(TAG, "addData: Adding " + cbOrgType +","+ cbPMSaveAsName+","+ cbAmt+", "+ cbDueDate+","+ cbPMDate+", "+ cbLastReading+","+ cbCurrReading
                +", "+ cbIsPaid);

        long result = db.insert(TBLCurrentBills, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean updateCurrentBilsById(String cbId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c);

        //contentValues.put(FirstName, fname);
        //contentValues.put(LastName, lname);
        //contentValues.put(Email, email);
        //contentValues.put(Password, pass);
        contentValues.put(CB_IsPaid, "Yes");
        contentValues.put(CB_PMDate, formattedDate.toString());

        Log.d(TAG, "updateData: Updating "+CB_IsPaid+ " to " + TBLCurrentBills);

        long result = db.update(TBLCurrentBills,contentValues,DatabaseHelper.CBID + "=?" , new String[]{cbId});

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean updateCurrentBils() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(FirstName, fname);
        //contentValues.put(LastName, lname);
        //contentValues.put(Email, email);
        //contentValues.put(Password, pass);
        contentValues.put(CB_IsPaid, "Yes");

        Log.d(TAG, "updateData: Updating "+CB_IsPaid+ " to " + TBLCurrentBills);

        long result = db.update(TBLCurrentBills,contentValues,DatabaseHelper.CB_IsPaid + "='No'" ,null);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void clearTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TBLPaymentMethod);
        db.execSQL("delete from "+ TBLOrganization);
        db.execSQL("delete from "+ TBLCurrentBills);
       // db.execSQL("delete from "+ TBLUser);
        //TBLUser
    }

    //updateCurrentBilsForAutoPay

    //endregion BillsData


}
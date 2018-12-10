package com.example.arpan.ezpay;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.content.Context;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NotificationHelper helper;
    DatabaseHelper databaseHelper;
    public static FragmentManager fragmentManager;
    public static CheckBox chkBoxBank, chkBoxCreditCard, chkBoxVenmo, chkBoxPaypal, chkBoxWater, chkBoxElectricity,chkBoxInternet;
    public static boolean chkPaymentMethod, chkAddOrganization, chkListPaymentMethod, chkListAddOrganization;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        helper = new NotificationHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState== null){
            //databaseHelper.clearTable();
            //Cursor data = databaseHelper.getOrganizationList();

           // if (data.getCount()==0) {
                //setTitle("Quick Setup");
               // addMockData();
           //     QuickSetup qs = new QuickSetup();
          //      fragmentManager = getSupportFragmentManager();
          //      fragmentManager.beginTransaction().replace(R.id.fragment_container,qs).addToBackStack(null).commit();
          //      navigationView.setCheckedItem(R.id.nav_quick_setup);
          //  }
            //else{
                CurrentBillsList ms = new CurrentBillsList();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,ms).commit();
                navigationView.setCheckedItem(R.id.nav_home);
           // }

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle("EzPay");
            CurrentBillsList ms = new CurrentBillsList();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,ms).addToBackStack(null).commit();
        } else if (id == R.id.nav_quick_setup) {
            setTitle("Quick Setup");
            addMockData();
            QuickSetup qs = new QuickSetup();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,qs).addToBackStack(null).commit();
        } else if (id == R.id.nav_organization) {
            setTitle("Manage Organization");
            OrganizationList qs = new OrganizationList();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,qs).addToBackStack(null).commit();
        } else if (id == R.id.nav_payment) {
            setTitle("Manage Payment");
            PaymentMethodList qs = new PaymentMethodList();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,qs).addToBackStack(null).commit();


        } else if (id == R.id.nav_paymentHistroy) {
            setTitle("Payment History");
            PaidBillsList qs = new PaidBillsList();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,qs).addToBackStack(null).commit();

        }else if (id == R.id.nav_signout) {
                    addNotification();
                    Intent intent = new Intent(this,login_page.class);
            finish();
            startActivityForResult(intent,0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void addNotification() {
        String title = "Tuscaloosa Water and Sewer December Bill Available";//edtTitle.getText().toString();
        String content = "Amount :$50 Deadline: 12/17/2018";//edtContent.getText().toString();
        Notification.Builder builder = helper.getTestChannelNotification(title,content);
        //helper.getManager().notify(new Random().nextInt(),builder.build());

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    private void addMockData() {
        databaseHelper.addCurrentBills("Water","MYWellsFargo","$50.60","12/18/18",null,"1337","1354","No");
        databaseHelper.addCurrentBills("Electricity","MyAmericanExpress","$98.45","12/25/2018",null,"553","665","No");
        databaseHelper.addCurrentBills("Internet","MyVenmo","$123.20","12/23/2018",null,null,null,"No");

    }
}

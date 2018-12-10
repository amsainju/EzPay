package com.example.arpan.ezpay;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentBillsList extends Fragment {

    private static final String TAG = "CurrentBillsListFragment";
    DatabaseHelper databaseHelper;

    private boolean isPaidAll;
   // private boolean isAutoPay;

    private ListView listView;
    private Button btnPayAll;
    private TextView txtMessage;
   // private Button btnEnrollAutoPay;
    CurrentBills cb=null;
   /* public CurrentBillsList() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("EzPay");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_bills_list, container, false);

        listView = (ListView) view.findViewById(R.id.lvCurrentBills);
        btnPayAll = (Button) view.findViewById(R.id.btnPayAll);
        txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        //btnEnrollAutoPay = (Button) view.findViewById(R.id.btnEnrollAutoPay);
        //DB
        databaseHelper = new DatabaseHelper(view.getContext());

        final ArrayList<CurrentBills> cbList = new ArrayList<CurrentBills>();

        populateListView(cbList);

        final CurrentBillsListAdaptor cbAdapter =new CurrentBillsListAdaptor(this.getContext(),R.layout.adptor_view_cbl_layout, cbList);
        listView.setAdapter(cbAdapter);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Cursor cursor = (Cursor)listView.getItemAtPosition(position);
//                String str = cursor.getString((Integer) view.getTag());
//                Toast.makeText(view.getContext(), "List Item clicked at "+ position,Toast.LENGTH_SHORT).show();
//            }
//        });

        btnPayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage(" ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isPaidAll=PayAllCurrentBills();

                                if(isPaidAll)
                                {
                                    cbList.clear();

                                    cbAdapter.notifyDataSetChanged();

                                    toastMessage("All bills paid successfully!!");
                                }
                                else
                                {
                                    toastMessage("Can not complete the action. Please try again later");
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle("Are you sure you want to pay?");
                alert.show();
            }
        });


        //region Autopay
   /*     btnEnrollAutoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage(" ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isAutoPay=EnrollAutoPay();

                                if(isAutoPay){
                                    toastMessage("All bills paid successfully!!");
                                }
                                else
                                {
                                    toastMessage("Can not complete the action. Please try again later");
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle("Are you sure you want to enroll in Auto-Payement?");
                alert.show();
            }
        });*/
//endregion AutoPay


        return view;
    }



    private void addMockData() {
        databaseHelper.addCurrentBills("Water","MYWellsFargo","$50.60","12/18/18",null,"1337","1354","No");
        databaseHelper.addCurrentBills("Electricity","MyAmericanExpress","$98.45","12/25/2018",null,"553","665","No");
        databaseHelper.addCurrentBills("Internet","MyVenmo","$123.20","12/23/2018",null,null,null,"No");

    }

    private void populateListView(ArrayList<CurrentBills> cbList) {

        Log.d(TAG, "populateGridView: Displaying Current Bills List in the ListView.");

        //get the data and append to a gridview
        Cursor data = databaseHelper.getCurrentBills();

        if (data.getCount()>0) {
            btnPayAll.setEnabled(true);
            txtMessage.setVisibility(View.GONE);
            btnPayAll.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            while (data.moveToNext()) {
                //get the value from the database in column 1
                //then add it to the ArrayList

                String id = data.getString(data.getColumnIndex("ID"));
                String orgType = data.getString(data.getColumnIndex("OrgType"));
                String saveAsName = data.getString(data.getColumnIndex("PMSaveAsName"));
                String dueDate = data.getString(data.getColumnIndex("DueDate"));
                String amount = data.getString(data.getColumnIndex("Amount"));
                cb = new CurrentBills(id,orgType, saveAsName, dueDate, amount);
                cbList.add(cb);
            }
        }
        else
        {
            //TODO: Reset function
            btnPayAll.setEnabled(false);
            btnPayAll.setBackgroundColor(Color.parseColor("#A0A0A0"));
            txtMessage.setVisibility(View.VISIBLE);
            //ToDO: Show message//***********DO NOT FORGET TO DELETE IT LATER*****************//
            //addMockData();
            //populateListView(cbList);

        }
    }


    public boolean PayAllCurrentBills()
    {
        return databaseHelper.updateCurrentBils();
    }
/*
    public boolean EnrollAutoPay()
    {
        return databaseHelper.updateCurrentBilsForAutoPay();
    }*/
    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

}

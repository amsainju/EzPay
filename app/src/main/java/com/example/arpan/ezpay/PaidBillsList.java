package com.example.arpan.ezpay;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaidBillsList extends Fragment {

    private static final String TAG = "PaidBillsListFragment";
    DatabaseHelper databaseHelper;

    private boolean isPaidAll;
   // private boolean isAutoPay;

    private ListView listView;
    private TextView txtMessage;
   // private Button btnEnrollAutoPay;
   PaidBills pb=null;
   /* public CurrentBillsList() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getActivity().setTitle("EzPay");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paid_bills_list, container, false);

        listView = (ListView) view.findViewById(R.id.lvPaidBills);
        txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        //btnEnrollAutoPay = (Button) view.findViewById(R.id.btnEnrollAutoPay);
        //DB
        databaseHelper = new DatabaseHelper(view.getContext());

        final ArrayList<PaidBills> pbList = new ArrayList<PaidBills>();

        populateListView(pbList);

        final PaidBillsListAdaptor pbAdapter =new PaidBillsListAdaptor(this.getContext(),R.layout.adptor_view_pbl_layout, pbList);
        listView.setAdapter(pbAdapter);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Cursor cursor = (Cursor)listView.getItemAtPosition(position);
//                String str = cursor.getString((Integer) view.getTag());
//                Toast.makeText(view.getContext(), "List Item clicked at "+ position,Toast.LENGTH_LONG).show();
//            }
//        });

        return view;
    }


    private void populateListView(ArrayList<PaidBills> pbList) {

        Log.d(TAG, "populateGridView: Displaying Current Bills List in the ListView.");

        //get the data and append to a gridview
        Cursor data = databaseHelper.getPaidBills();

        if (data.getCount()>0) {
            //btnPayAll.setEnabled(true);
            txtMessage.setVisibility(View.GONE);
           // btnPayAll.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            while (data.moveToNext()) {
                //get the value from the database in column 1
                //then add it to the ArrayList

                String id = data.getString(data.getColumnIndex("ID"));
                String orgType = data.getString(data.getColumnIndex("OrgType"));
                String saveAsName = data.getString(data.getColumnIndex("PMSaveAsName"));
                String paymentDate = data.getString(data.getColumnIndex("PaymentDate"));
                paymentDate="Paid on: "+paymentDate;
                String amount = data.getString(data.getColumnIndex("Amount"));
                amount = "Amount Paid: "+amount;
                pb = new PaidBills(id,orgType, saveAsName, paymentDate, amount);
                pbList.add(pb);
            }
        }
        else
        {
            //TODO: Reset function
            //btnPayAll.setEnabled(false);
           // btnPayAll.setBackgroundColor(Color.parseColor("#A0A0A0"));
            txtMessage.setVisibility(View.VISIBLE);
            //ToDO: Show message//***********DO NOT FORGET TO DELETE IT LATER*****************//
            //addMockData();
            //populateListView(pbList);

        }
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_LONG).show();
    }

}

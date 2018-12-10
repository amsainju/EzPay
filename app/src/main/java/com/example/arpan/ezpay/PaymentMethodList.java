package com.example.arpan.ezpay;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentMethodList extends Fragment {

    private static final String TAG = "PaymentMethodListFragment";
    DatabaseHelper databaseHelper;

    private ListView listView;
    private Button btnAdNewPaymentMethod;
    Payment pmt=null;
   /* public PaymentMethodList() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Manage Payment Methods");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_payment_method_list, container, false);

        //DB
        listView = (ListView) view.findViewById(R.id.lvPayment);
        databaseHelper = new DatabaseHelper(view.getContext());
        btnAdNewPaymentMethod = view.findViewById(R.id.btnAdNewPaymentMethod);
        ArrayList<Payment> pmtList = new ArrayList<Payment>();
        //addMockData();
        populateListView(pmtList);

        PaymentListAdapter pmAdapter =new PaymentListAdapter(this.getContext(),R.layout.adptor_view_pml_layout, pmtList);
        listView.setAdapter(pmAdapter);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Cursor cursor = (Cursor)listView.getItemAtPosition(position);
//                String str = cursor.getString((Integer) view.getTag());
//                Toast.makeText(view.getContext(), "List Item clicked at "+ position,Toast.LENGTH_LONG).show();
//            }
//        });

        btnAdNewPaymentMethod.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkListPaymentMethod = true;
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddPaymentMethod()).addToBackStack(null).commit();

            }
        });

        return view;
    }

    private void addMockData() {
        databaseHelper.addPaymentMethod("Paypal",null,"MyPayPal",null,null,null,null,null,null,"test","test123");
        databaseHelper.addPaymentMethod("Venmo",null,"MyVenmo",null,null,null,null,null,null,"ABCD","testtest");
        databaseHelper.addPaymentMethod("Bank","Wells Fargo","MyBank","123456789","123456789123",null,null,null,null,null,null);
        databaseHelper.addPaymentMethod("CreditCard",null,"MyCreditCard",null,null,"Sweta Mahaju","4567891234567891","10-10-2026","4561",null,null);

    }


    private void populateListView(ArrayList<Payment> pmtList) {

        Log.d(TAG, "populateGridView: Displaying Payment Method List in the GridView.");

        //get the data and append to a gridview
        Cursor data = databaseHelper.getPaymentMethodList();

        if (data.getCount()>0) {
            while (data.moveToNext()) {
                //get the value from the database in column 1
                //then add it to the ArrayList

                String id = data.getString(data.getColumnIndex("ID"));
                String savAsName = data.getString(data.getColumnIndex("SaveAsName"));
                pmt = new Payment(id, savAsName);
                //gvData.add(data.getString(1));
                pmtList.add(pmt);
            }
        }
        else
        {
            //ToDO: Show message//***********DO NOT FORGET TO DELETE IT LATER*****************//
            //addMockData();
            //populateListView(pmtList);

        }
    }
    public void resetGraph(View view)
    {

        view.invalidate();

    }



    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_LONG).show();
    }

}

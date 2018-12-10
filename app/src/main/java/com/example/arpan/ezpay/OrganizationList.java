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
public class OrganizationList extends Fragment {

    private static final String TAG = "OrganizationListFragment";
    DatabaseHelper databaseHelper;

    private ListView listView;
    private Button btnAdNewOrganization;
    Organization org=null;
   /* public OrganizationList() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Manage Organization");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_organization_list, container, false);

        //DB
        listView = (ListView) view.findViewById(R.id.lvOrganization);
        databaseHelper = new DatabaseHelper(view.getContext());
        btnAdNewOrganization = view.findViewById(R.id.btnAdNewOrganization);
        ArrayList<Organization> orgtList = new ArrayList<Organization>();
        //addMockData();
        populateListView(orgtList);

        OrganizationListAdapter orgAdapter =new OrganizationListAdapter(this.getContext(),R.layout.adptor_view_org_layout, orgtList);
        listView.setAdapter(orgAdapter);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Cursor cursor = (Cursor)listView.getItemAtPosition(position);
//                String str = cursor.getString((Integer) view.getTag());
//                Toast.makeText(view.getContext(), "List Item clicked at "+ position,Toast.LENGTH_LONG).show();
//            }
//        });

        btnAdNewOrganization.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkListAddOrganization = true;
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddOrganizations()).addToBackStack(null).commit();

            }
        });

        return view;
    }

    private void addMockData() {
        databaseHelper.addOrganization("Water","Tuscaloosa Water","Yes","MYBank","12345","4456433",null,null);
        databaseHelper.addOrganization("Electricity","Alabama Power","Yes","MYBank","12345",null,null,null);
        databaseHelper.addOrganization("Internet","Xfinity","Yes","MYBank","12345",null,null,null);

    }
    private void addMockData2() {
        databaseHelper.addCurrentBills("Water","MYWellsFargo","$50.60","12/18/18",null,"1337","1354","No");
        databaseHelper.addOrganization("Electricity","MyAmericanExpress","$98.45","12/25/2018",null,"553","665","No");
        databaseHelper.addOrganization("Internet","MyVenmo","$123.20","12/23/2018",null,null,null,"No");

    }

    private void populateListView(ArrayList<Organization> orgtList) {

        Log.d(TAG, "populateGridView: Displaying Organization List in the GridView.");

        //get the data and append to a gridview
        Cursor data = databaseHelper.getOrganizationList();

        if (data.getCount()>0) {
            while (data.moveToNext()) {
                //get the value from the database in column 1
                //then add it to the ArrayList

                String id = data.getString(data.getColumnIndex("ID"));
                String savAsName = data.getString(data.getColumnIndex("OrganizationType"));
                org = new Organization(id, savAsName);
                //gvData.add(data.getString(1));
                orgtList.add(org);
            }
        }
        else
        {
            //ToDO: Show message//***********DO NOT FORGET TO DELETE IT LATER*****************//
            //addMockData();
            //populateListView(orgtList);

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
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

}

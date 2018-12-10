package com.example.arpan.ezpay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrganizations extends Fragment {

    //public CheckBox chkBoxBank, chkBoxCreditCard, chkBoxVenmo, chkBoxPaypal;
    public Button btnNextAddOrganizations, btnSkipAddOrganizations;
    public AddOrganizations() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Add Billing Organizations");
        View view= inflater.inflate(R.layout.fragment_add_organizations, container, false);

        MainActivity.chkBoxWater = (CheckBox)view.findViewById(R.id.chkWater);
        MainActivity.chkBoxElectricity = (CheckBox)view.findViewById(R.id.chkElectricity);
        MainActivity.chkBoxInternet = (CheckBox)view.findViewById(R.id.chkInternet);
         btnNextAddOrganizations = (Button)view.findViewById(R.id.btnNextAddOrganizations);
        btnSkipAddOrganizations = (Button)view.findViewById(R.id.btnSkipAddOrganizations);

        btnNextAddOrganizations.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MainActivity.chkBoxWater.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddWater()).addToBackStack(null).commit();
                    // do something
                }
                else if(MainActivity.chkBoxElectricity.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddElectricity()).addToBackStack(null).commit();
                    // do something
                }
                else if(MainActivity.chkBoxInternet.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddInternet()).addToBackStack(null).commit();
                    // do something
                }
                else{
                     if(MainActivity.chkListAddOrganization){
                         MainActivity.chkListAddOrganization = false;
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new OrganizationList()).addToBackStack(null).commit();
                    }
                    else{
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                    }

                }

            }
        });

        btnSkipAddOrganizations.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkBoxWater.setChecked(false);
                MainActivity.chkBoxElectricity.setChecked(false);
                MainActivity.chkBoxInternet.setChecked(false);
                if(MainActivity.chkListAddOrganization){
                    MainActivity.chkListAddOrganization = false;
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new OrganizationList()).addToBackStack(null).commit();
                }
                else{
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                }                // do something
            }
        });
        return view;
    }




}

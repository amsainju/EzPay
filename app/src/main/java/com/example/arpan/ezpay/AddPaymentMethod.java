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
public class AddPaymentMethod extends Fragment {

    //public CheckBox chkBoxBank, chkBoxCreditCard, chkBoxVenmo, chkBoxPaypal;
    public Button btnNextPaymentMethod, btnSkipPaymentMethod;
    public AddPaymentMethod() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Payment Methods");
        View view= inflater.inflate(R.layout.fragment_add_payment_method, container, false);

        MainActivity.chkBoxBank = (CheckBox)view.findViewById(R.id.chkBankAccount);
        MainActivity.chkBoxCreditCard = (CheckBox)view.findViewById(R.id.chkCreditCard);
        MainActivity.chkBoxVenmo = (CheckBox)view.findViewById(R.id.chkVenmo);
        MainActivity.chkBoxPaypal = (CheckBox)view.findViewById(R.id.chkPaypal);
        btnNextPaymentMethod = (Button)view.findViewById(R.id.btnNextPaymentMethod);
        btnSkipPaymentMethod = (Button)view.findViewById(R.id.btnSkipPaymentMethod);

        btnNextPaymentMethod.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MainActivity.chkBoxBank.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddAccountDetails()).addToBackStack(null).commit();
                    // do something
                }
                else if(MainActivity.chkBoxCreditCard.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddCreditCard()).addToBackStack(null).commit();
                // do something
                }
                else if(MainActivity.chkBoxPaypal.isChecked()){
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddPayPal()).addToBackStack(null).commit();
                // do something
                }
                else if(MainActivity.chkBoxVenmo.isChecked()){
                     MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddVenmo()).addToBackStack(null).commit();
                // do something
                }
                else{
                    //MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new AddOrganizations()).addToBackStack(null).commit();

                 }

            }
        });

        btnSkipPaymentMethod.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.chkBoxBank.setChecked(false);
                MainActivity.chkBoxCreditCard.setChecked(false);
                MainActivity.chkBoxPaypal.setChecked(false);
                MainActivity.chkBoxVenmo.setChecked(false);
                if(MainActivity.chkAddOrganization) {
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddOrganizations()).addToBackStack(null).commit();
                }
                else if(MainActivity.chkListPaymentMethod){
                    MainActivity.chkListPaymentMethod =false;
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new PaymentMethodList()).addToBackStack(null).commit();
                }
                else{
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new CurrentBillsList()).addToBackStack(null).commit();

                }
             }
        });
        return view;
    }




}

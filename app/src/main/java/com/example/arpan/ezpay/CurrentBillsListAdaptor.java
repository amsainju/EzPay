package com.example.arpan.ezpay;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class CurrentBillsListAdaptor extends ArrayAdapter<CurrentBills>{

    private static final String TAG ="CurrentBillsListAdapter";
    private Context cbContext;
    CurrentBillsList cbList;
    int cbResource;
    private int lastPosition = -1;

    boolean isUpdated=false;
    DatabaseHelper databaseHelper;
    public ArrayList<CurrentBills> myList;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView id;
        TextView orgType;
        TextView dueDate;
        TextView amt;
        Button btnPayNow;
        Button btnPayAll;
    }


    public CurrentBillsListAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<CurrentBills> objects) {
        super(context, resource, objects);
        this.myList=objects;
        this.cbContext = context;
        this.cbResource=resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        cbList=new CurrentBillsList();

        String id = getItem(position).getCbId();
        String orgType = getItem(position).getOrgType();
        String saveAsName = getItem(position).getOrgSaveAsName();
        String dueDate=getItem(position).getDueDate();
        String amt= getItem(position).getAmount();

        //Create the Payment object with the information
        CurrentBills cb = new CurrentBills(id,orgType,saveAsName,dueDate,amt);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        final ViewHolder holder;

        LayoutInflater inflater = LayoutInflater.from(cbContext);

        if(convertView == null){
            convertView = inflater.inflate(cbResource, parent, false);
            holder= new ViewHolder();
            holder.id =(TextView)convertView.findViewById(R.id.txtCbId);
            holder.orgType = (TextView) convertView.findViewById(R.id.txtOrgType);
            holder.dueDate = (TextView) convertView.findViewById(R.id.txtCbDueDate);
            holder.amt = (TextView) convertView.findViewById(R.id.txtCbAmount);
            holder.btnPayNow= (Button) convertView.findViewById(R.id.btnPayNow);

            result = convertView;

        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        convertView.setTag(holder);

        databaseHelper=new DatabaseHelper(convertView.getContext());

        Animation animation = AnimationUtils.loadAnimation(cbContext,
                (position > lastPosition) ? R.anim.load_down_anime: R.anim.load_up_anime);
        result.startAnimation(animation);
        lastPosition = position;

        holder.id.setText(cb.getCbId());
        holder.orgType.setText(cb.getOrgType());
        holder.dueDate.setText(cb.getDueDate());
        holder.amt.setText(cb.getAmount());
        holder.orgType.setText(cb.getOrgType());

        holder.btnPayNow.setTag(position);

        holder.btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage(" ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Integer index = (Integer) holder.btnPayNow.getTag();
                                String itemToUpdateById = (String)holder.id.getText();
                                isUpdated=UpdateCurrentBillByID(itemToUpdateById);

                                if(isUpdated)
                                {

                                    myList.remove(index.intValue());
                                    //v.invalidate();
                                    toastMessage("Bill Paid Successfully!!");
                                }
                                else
                                {
                                    toastMessage("The bill could not be paid. Please try again later");
                                }
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle("Are you sure you want to make the payment?");
                alert.show();
            }
        });



        //For Now on delete functionality
        //ImageButton pmImgBtn = (ImageButton) result.findViewById(R.id.imgbtnDel);

        // inflate other items here :

     /*   holder.imgbtn.setTag(position);

        holder.imgbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer index = (Integer) v.getTag();
                        //items.remove(index.intValue());
                        String val = (String)((TextView)v.findViewById(R.id.txtId)).getText();

                        notifyDataSetChanged();
                    }
                });*/

        return convertView;

    }

    public boolean UpdateCurrentBillByID(String id)
    {
        return  databaseHelper.updateCurrentBilsById(id);
    }

    public boolean UpdateAllCurrentBill()
    {
        return  databaseHelper.updateCurrentBils();
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }




}

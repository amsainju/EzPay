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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PaymentListAdapter extends ArrayAdapter<Payment> {
    private static final String TAG ="PaymentListAdapter";
    private Context pmContext;
    PaymentMethodList pmMethodList;
    int pmResource;
    private int lastPosition = -1;

    boolean isDeleted=false;
    DatabaseHelper databaseHelper;
    public ArrayList<Payment> myList;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView id;
        TextView pmName;
        ImageButton imgbtn;
    }


    public PaymentListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Payment> objects) {
        super(context, resource, objects);
        this.myList=objects;
        this.pmContext = context;
        this.pmResource=resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        pmMethodList=new PaymentMethodList();
        //get the Payment information
        String id = getItem(position).getId();
        String pmName = getItem(position).getPaymentMethodName();

        //Create the Payment object with the information
        Payment pmt = new Payment(id,pmName);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        final ViewHolder holder;

        LayoutInflater inflater = LayoutInflater.from(pmContext);

        if(convertView == null){
            convertView = inflater.inflate(pmResource, parent, false);
            holder= new ViewHolder();
            holder.id =(TextView)convertView.findViewById(R.id.txtId);
            holder.pmName = (TextView) convertView.findViewById(R.id.txtPmList);
            holder.imgbtn= (ImageButton) convertView.findViewById(R.id.imgbtnDel);

            result = convertView;

        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        convertView.setTag(holder);

        databaseHelper=new DatabaseHelper(convertView.getContext());

        Animation animation = AnimationUtils.loadAnimation(pmContext,
                (position > lastPosition) ? R.anim.load_down_anime: R.anim.load_up_anime);
        result.startAnimation(animation);
        lastPosition = position;

        holder.id.setText(pmt.getId());
        holder.pmName.setText(pmt.getPaymentMethodName());


        holder.imgbtn.setTag(position);

        holder.imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage(" ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Integer index = (Integer) holder.imgbtn.getTag();
                                String itemToDeleteId = (String)holder.id.getText();
                                isDeleted=DeletePaymentMethodByID(itemToDeleteId);

                                if(isDeleted)
                                {

                                    myList.remove(index.intValue());
                                    //v.invalidate();
                                    toastMessage("Payment Method deleted successfully!!");
                                }
                                else
                                {
                                    toastMessage("Payment Method Couldnot be deleted. Please try again later");
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
                alert.setTitle("Are you sure you want to delete?");
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

    public boolean DeletePaymentMethodByID(String id)
    {
        return  databaseHelper.deletePaymentMethod(id);
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }
}

package com.example.arpan.ezpay;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PaidBillsListAdaptor extends ArrayAdapter<PaidBills>{

    private static final String TAG ="PaidBillsListAdapter";
    private Context pbContext;
    PaidBillsList pbList;
    int pbResource;
    private int lastPosition = -1;

    boolean isUpdated=false;
    DatabaseHelper databaseHelper;
    public ArrayList<PaidBills> myList;

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


    public PaidBillsListAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<PaidBills> objects) {
        super(context, resource, objects);
        this.myList=objects;
        this.pbContext = context;
        this.pbResource=resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        pbList=new PaidBillsList();

        String id = getItem(position).getPbId();
        String orgType = getItem(position).getOrgType();
        String saveAsName = getItem(position).getOrgSaveAsName();
        String dueDate=getItem(position).getDueDate();
        String amt= getItem(position).getAmount();

        //Create the Payment object with the information
        PaidBills pb = new PaidBills(id,orgType,saveAsName,dueDate,amt);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        final ViewHolder holder;

        LayoutInflater inflater = LayoutInflater.from(pbContext);

        if(convertView == null){
            convertView = inflater.inflate(pbResource, parent, false);
            holder= new ViewHolder();
            holder.id =(TextView)convertView.findViewById(R.id.txtPbId);
            holder.orgType = (TextView) convertView.findViewById(R.id.txtOrgType);
            holder.dueDate = (TextView) convertView.findViewById(R.id.txtPaidDate);
            holder.amt = (TextView) convertView.findViewById(R.id.txtPaidAmount);
            //holder.btnPayNow= (Button) convertView.findViewById(R.id.btnPayNow);

            result = convertView;

        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        convertView.setTag(holder);

        databaseHelper=new DatabaseHelper(convertView.getContext());

        Animation animation = AnimationUtils.loadAnimation(pbContext,
                (position > lastPosition) ? R.anim.load_down_anime: R.anim.load_up_anime);
        result.startAnimation(animation);
        lastPosition = position;

        holder.id.setText(pb.getPbId());
        holder.orgType.setText(pb.getOrgType());
        holder.dueDate.setText(pb.getDueDate());
        holder.amt.setText(pb.getAmount());
        holder.orgType.setText(pb.getOrgType());

        return convertView;

    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

}

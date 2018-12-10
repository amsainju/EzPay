package com.example.arpan.ezpay;


import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainTest extends Fragment {

    LinearLayout mChartLayout;
    TableLayout mTableLayout;

    public MainTest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main_test, container, false);
        mChartLayout = (LinearLayout)view.findViewById(R.id.chart_layout);
         mTableLayout = new TableLayout(getContext());

        displayChartTable();
        return view;
    }

    public void displayChartTable(){
        mTableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT
        ));
        TableRow row = new TableRow(getContext());
        row.setLayoutParams((new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT)));
        TextView txtOrgHeader = new TextView(getContext());
        //txtOrgHeader.getLayoutParams().height = 50;
        txtOrgHeader.setText(" Agency   ");
        txtOrgHeader.setTextSize(getResources().getDimension(R.dimen.header_text_size));
        txtOrgHeader.setPadding(0,20,0,20);
        txtOrgHeader.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        txtOrgHeader.setTypeface(null, Typeface.BOLD);
        txtOrgHeader.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,220
        ));
        row.addView(txtOrgHeader);

        TextView txtAmount = new TextView(getContext());
        //txtAmount.getLayoutParams().height = 50;
        txtAmount.setText("Amount   ");
        txtAmount.setTextSize(getResources().getDimension(R.dimen.header_text_size));
        txtAmount.setPadding(0,20,0,20);
        txtAmount.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        txtAmount.setTypeface(null, Typeface.BOLD);
        txtAmount.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,150
        ));
        row.addView(txtAmount);

        TextView txtDue = new TextView(getContext());
        //txtDue.getLayoutParams().height = 50;
        txtDue.setText("Due  ");
        txtDue.setTextSize(getResources().getDimension(R.dimen.header_text_size));
        txtDue.setPadding(0,20,0,20);
        txtDue.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        txtDue.setTypeface(null, Typeface.BOLD);
        txtDue.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,150
        ));
        row.addView(txtDue);
        mTableLayout.addView(row);
        View mview = new View(getContext());
        mview.setLayoutParams((new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                5)));
        mview.setBackgroundColor(Color.parseColor("#A0A0A0"));
        mTableLayout.addView(mview);
        Button btn;
        for (int count=0; count <3;count++){
            row = new TableRow(getContext());
            row.setLayoutParams((new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    80)));
            txtOrgHeader = new TextView(getContext());
           // txtOrgHeader.getLayoutParams().height = 50;
            txtOrgHeader.setText(" Electricity");
            txtOrgHeader.setTextSize(getResources().getDimension(R.dimen.child_text_size));
            txtOrgHeader.setPadding(0,20,0,20);
            txtOrgHeader.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            //txtOrgHeader.setTypeface(null, Typeface.BOLD);
            txtOrgHeader.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,200
            ));
            row.addView(txtOrgHeader);

            txtAmount = new TextView(getContext());
            //txtAmount.getLayoutParams().height = 50;
            txtAmount.setText("$109.90");
            txtAmount.setTextSize(getResources().getDimension(R.dimen.child_text_size));
            txtAmount.setPadding(0,20,0,20);
            txtAmount.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
           // txtAmount.setTypeface(null, Typeface.BOLD);
            txtAmount.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,200
            ));
            row.addView(txtAmount);

            txtDue = new TextView(getContext());
           // txtDue.getLayoutParams().height = 50;
            txtDue.setText("11/12");
            txtDue.setTextSize(getResources().getDimension(R.dimen.child_text_size));
            txtDue.setPadding(0,20,0,20);
            txtDue.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            //txtDue.setTypeface(null, Typeface.BOLD);
            txtDue.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,200
            ));

            row.addView(txtDue);

            //buttons
            btn = new Button(getContext());
            btn.setText("Pay Now");
            btn.setId(count);
            btn.setOnClickListener(btnclick);
            btn.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            btn.setTypeface(null, Typeface.BOLD);
            btn.setTextColor(Color.parseColor("#ffffff"));
            btn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(btn);
            mTableLayout.addView(row);
            mview = new View(getContext());
            mview.setLayoutParams((new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    5)));
            mview.setBackgroundColor(Color.parseColor("#A0A0A0"));
            mTableLayout.addView(mview);

        }
        mChartLayout.addView(mTableLayout);

    }
    View.OnClickListener btnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case 1:
                    //first button click
                    break;
                //Second button click
                case 2:
                    break;
                case 3:
                    //third button click
                    break;
                case 4:
                    //fourth button click
                    break;
                default:
                    break;
            }
        }
    };
}

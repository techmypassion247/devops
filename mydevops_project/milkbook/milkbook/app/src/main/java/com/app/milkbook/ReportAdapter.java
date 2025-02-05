package com.app.milkbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReportAdapter extends ArrayAdapter<ReportModel> {

    public ReportAdapter(Context context, ArrayList<ReportModel> reports) {
        super(context, 0, reports);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.report_list_item, parent, false);
        }

        ReportModel report = getItem(position);

        TextView tvCustomerName = convertView.findViewById(R.id.tv_customer_name);
        TextView tvFat = convertView.findViewById(R.id.tv_fat);
        TextView tvWeight = convertView.findViewById(R.id.tv_weight);
        TextView tvPerLitre = convertView.findViewById(R.id.tv_per_litre);
        TextView tvResult = convertView.findViewById(R.id.tv_result);
        TextView tvEntryDate = convertView.findViewById(R.id.tv_entry_date);

        tvCustomerName.setText("Customer Name: " + report.getCustomerName());
        tvFat.setText("FAT: " + report.getFat());
        tvWeight.setText("Weight: " + report.getWeight());
        tvPerLitre.setText("Per Litre: " + report.getPerLitre());
        tvResult.setText("Result: " + report.getResult());
        tvEntryDate.setText("Date: " + report.getEntryDate());

        return convertView;
    }
}


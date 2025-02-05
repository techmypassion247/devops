package com.app.milkbook;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    private EditText edtCustomerId, edtCustomerName, edtDate;
    private Button btnFilter;
    private ListView listViewReport;
    private DBHelper dbHelper;
    private ReportAdapter reportAdapter;
    private Button btnPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        edtCustomerId = findViewById(R.id.edt_customer_id);
        edtCustomerName = findViewById(R.id.edt_customer_name);
        edtDate = findViewById(R.id.edt_date);
        btnFilter = findViewById(R.id.btn_filter);
        listViewReport = findViewById(R.id.list_view_report);

        dbHelper = new DBHelper(this);

        btnFilter.setOnClickListener(v -> {
            String customerId = edtCustomerId.getText().toString();
            String customerName = edtCustomerName.getText().toString();
            String date = edtDate.getText().toString();

            // Fetch filtered data
            ArrayList<ReportModel> reportData = fetchFilteredData(customerId, customerName, date);

            // Update list view with report data
            reportAdapter = new ReportAdapter(ReportActivity.this, reportData);
            listViewReport.setAdapter(reportAdapter);
        });
    }

    //private ArrayList<ReportModel> fetchFilteredData(String customerId, String customerName, String date) {
    //    ArrayList<ReportModel> reportData = new ArrayList<>();
    //    SQLiteDatabase db = dbHelper.getReadableDatabase();

    // Construct query with optional filters
    //    String query = "SELECT * FROM Data_Entry WHERE 1=1";
    //   if (!customerId.isEmpty()) query += " AND customer_id = " + customerId;
    // if (!customerName.isEmpty()) query += " AND customer_id IN (SELECT id FROM user_info WHERE name LIKE '%" + customerName + "%')";
    //    if (!date.isEmpty()) query += " AND entry_date LIKE '%" + date + "%'";
//
    //      Cursor cursor = db.rawQuery(query, null);

    //    if (cursor.moveToFirst()) {
    //      do {
    //        //@SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("customer_id"));
    //      @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndexOrThrow("customer_name"));
    //    @SuppressLint("Range") double fat = cursor.getDouble(cursor.getColumnIndex("FAT"));
    //  @SuppressLint("Range") double weight = cursor.getDouble(cursor.getColumnIndex("Weight"));
    //  @SuppressLint("Range") double perLitre = cursor.getDouble(cursor.getColumnIndex("perLitre"));
    // @SuppressLint("Range") double result = cursor.getDouble(cursor.getColumnIndex("result"));
    //@SuppressLint("Range") String entryDate = cursor.getString(cursor.getColumnIndex("entry_date"));

    // reportData.add(new ReportModel(name, fat, weight, perLitre, result, entryDate));
    //} while (cursor.moveToNext());
    //}
    //cursor.close();
    //return reportData;
    //}
//}
    //fetch customer name also in reports
    private ArrayList<ReportModel> fetchFilteredData(String customerId, String customerName, String date) {
        ArrayList<ReportModel> reportData = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Construct query with JOIN between Data_Entry and user_info to fetch customer_name
        String query = "SELECT Data_Entry.customer_id, user_info.name AS customer_name, Data_Entry.FAT, Data_Entry.Weight, " +
                "Data_Entry.perLitre, Data_Entry.result, Data_Entry.entry_date " +
                "FROM Data_Entry " +
                "JOIN user_info ON Data_Entry.customer_id = user_info.id WHERE 1=1";

        // Apply filters dynamically
        if (!customerId.isEmpty()) query += " AND Data_Entry.customer_id = " + customerId;
        if (!customerName.isEmpty()) query += " AND user_info.name LIKE '%" + customerName + "%'";
        if (!date.isEmpty()) query += " AND Data_Entry.entry_date LIKE '%" + date + "%'";

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Fetch and process the result set
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("customer_name"));
                @SuppressLint("Range") double fat = cursor.getDouble(cursor.getColumnIndex("FAT"));
                @SuppressLint("Range") double weight = cursor.getDouble(cursor.getColumnIndex("Weight"));
                @SuppressLint("Range") double perLitre = cursor.getDouble(cursor.getColumnIndex("perLitre"));
                @SuppressLint("Range") double result = cursor.getDouble(cursor.getColumnIndex("result"));
                @SuppressLint("Range") String entryDate = cursor.getString(cursor.getColumnIndex("entry_date"));

                // Add to report data list
                reportData.add(new ReportModel(name, fat, weight, perLitre, result, entryDate));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reportData;
    }
}
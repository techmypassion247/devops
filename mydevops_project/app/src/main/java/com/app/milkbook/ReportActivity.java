package com.app.milkbook;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ReportActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothHelper bluetoothHelper;
    private Button printButton;
    private String selectedPrinterAddress;
    private ArrayList<String> pairedDeviceNames;
    private ArrayList<BluetoothDevice> pairedDevicesList;

    private EditText edtCustomerId, edtCustomerName, edtDate;
    private Button btnFilter;
    private ListView listViewReport;
    private DBHelper dbHelper;
    private ReportAdapter reportAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Set up toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Customer Report");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Handle back button press
        handleOnBackPressed();

        // Initialize Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDeviceNames = new ArrayList<>();
        pairedDevicesList = new ArrayList<>();

        printButton = findViewById(R.id.printButton);
        printButton.setOnClickListener(v -> showPairedDevicesDialog());

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

    // Show paired devices in a dialog for selection
    private void showPairedDevicesDialog() {
        Set<BluetoothDevice> pairedDevices = BluetoothHelper.getPairedDevices();
        String[] deviceNames = new String[pairedDevices.size()];
        String[] deviceAddresses = new String[pairedDevices.size()];

        int index = 0;
        for (BluetoothDevice device : pairedDevices) {
            deviceNames[index] = device.getName();
            deviceAddresses[index] = device.getAddress();
            index++;
        }

        // Alert dialog to show paired devices
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Printer");
        builder.setItems(deviceNames, (dialog, which) -> {
            // Get the selected printer address and connect to it
            String printerAddress = deviceAddresses[which];
            connectToPrinter(printerAddress);
        });
        builder.show();
    }

    // Connect to selected printer and print report
    private void connectToPrinter(String printerAddress) {
        if (bluetoothHelper == null) {
            bluetoothHelper = new BluetoothHelper(printerAddress, ReportActivity.this); // Ensure valid context
        }

        try {
            bluetoothHelper.connect();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the connection error here
        }
    }

    private String generateReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Customer Report\n\n");

        for (int i = 0; i < reportAdapter.getCount(); i++) {
            ReportModel report = reportAdapter.getItem(i);

            reportBuilder.append("Customer Name: ").append(report.getCustomerName()).append("\n");
            reportBuilder.append("FAT: ").append(report.getFat()).append("\n");
            reportBuilder.append("Weight: ").append(report.getWeight()).append("\n");
            reportBuilder.append("Per Litre: ").append(report.getPerLitre()).append("\n");
            reportBuilder.append("Result: ").append(report.getResult()).append("\n");
            reportBuilder.append("Date: ").append(report.getEntryDate()).append("\n");
            reportBuilder.append("----------------------------\n");
        }

        return reportBuilder.toString();
    }

    private void handleOnBackPressed() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private ArrayList<ReportModel> fetchFilteredData(String customerId, String customerName, String date) {
        ArrayList<ReportModel> reportData = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT Data_Entry.customer_id, user_info.name AS customer_name, Data_Entry.FAT, " +
                "Data_Entry.Weight, Data_Entry.perLitre, Data_Entry.result, Data_Entry.entry_date " +
                "FROM Data_Entry " +
                "JOIN user_info ON Data_Entry.customer_id = user_info.id WHERE 1=1";

        if (!customerId.isEmpty()) query += " AND Data_Entry.customer_id = " + customerId;
        if (!customerName.isEmpty()) query += " AND user_info.name LIKE '%" + customerName + "%'";
        if (!date.isEmpty()) query += " AND Data_Entry.entry_date LIKE '%" + date + "%'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("customer_name"));
                @SuppressLint("Range") double fat = cursor.getDouble(cursor.getColumnIndex("FAT"));
                @SuppressLint("Range") double weight = cursor.getDouble(cursor.getColumnIndex("Weight"));
                @SuppressLint("Range") double perLitre = cursor.getDouble(cursor.getColumnIndex("perLitre"));
                @SuppressLint("Range") double result = cursor.getDouble(cursor.getColumnIndex("result"));
                @SuppressLint("Range") String entryDate = cursor.getString(cursor.getColumnIndex("entry_date"));

                reportData.add(new ReportModel(name, fat, weight, perLitre, result, entryDate));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reportData;
    }
}

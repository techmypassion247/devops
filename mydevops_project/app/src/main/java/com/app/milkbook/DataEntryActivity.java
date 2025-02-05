package com.app.milkbook;

import android.widget.AdapterView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class DataEntryActivity extends AppCompatActivity {

    private Spinner spinnerCustomerId;
    private TextView tvCustomerName;
    private EditText edtFat, edtWeight;  // Use EditText for input
    private TextView tvSnf, tvFatRate, tvSnfRate, tvPerLitre, tvResult;
    private Button btnCalculate, btnSaveData;
    private DBHelper dbHelper;
    private Toolbar toolbar;
    private double fatRate = 0.0, snfRate = 0.0, snf = 0.0, fat = 0.0, weight = 0.0, perLitre = 0.0, result = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Rate Setup");
        }

        // Enable the back button on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle back press for the toolbar
        handleOnBackPressed();


        // Initialize UI elements
        spinnerCustomerId = findViewById(R.id.spinner_customer_id);
        tvCustomerName = findViewById(R.id.tv_customer_name);
        edtFat = findViewById(R.id.tv_fat);
        edtWeight = findViewById(R.id.tv_weight);
        tvSnf = findViewById(R.id.tv_snf);
        tvFatRate = findViewById(R.id.tv_fat_rate);
        tvSnfRate = findViewById(R.id.tv_snf_rate);
        tvPerLitre = findViewById(R.id.tv_per_litre);
        tvResult = findViewById(R.id.tv_result);
        btnCalculate = findViewById(R.id.btn_calculate);
        btnSaveData = findViewById(R.id.btn_save_data);

        dbHelper = new DBHelper(this);

        loadCustomerIds();  // Load Customer IDs

        // Calculate per litre and result when the button is clicked
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStoredDataAndCalculate();
            }
        });

        // Save the data when the button is clicked
        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataEntry();
            }
        });
    }
    private void loadCustomerIds() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, name FROM user_info", null);  // Fetch both ID and name
        ArrayList<String> customerIds = new ArrayList<>();
        final ArrayList<String> customerNames = new ArrayList<>();  // To store customer names

        if (cursor.moveToFirst()) {
            do {
                customerIds.add(cursor.getString(0));      // Add customer ID
                customerNames.add(cursor.getString(1));    // Add customer name
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customerIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCustomerId.setAdapter(adapter);

        // Set listener for spinner item selection to show customer name
        spinnerCustomerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When a customer ID is selected, display the corresponding customer name
                tvCustomerName.setText("Customer Name: " + customerNames.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: What happens if no selection is made
                tvCustomerName.setText("Customer Name: ");
            }
        });
    }
    // Handle the back button on the toolbar using OnBackPressedDispatcher
    private void handleOnBackPressed() {
        // Use the OnBackPressedDispatcher to handle back press events
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Custom logic for back button press
                finish();  // Close the current activity
            }
        });
    }

    private void loadStoredDataAndCalculate() {
        // Get input values for FAT and Weight
        try {
            fat = Double.parseDouble(edtFat.getText().toString());
            weight = Double.parseDouble(edtWeight.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid FAT and Weight", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch FAT_RATE, SNF, and SNF_RATE from Rate_setup table
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor rateCursor = db.rawQuery("SELECT FAT_RATE, SNF, SNF_RATE FROM Rate_setup ORDER BY id DESC LIMIT 1", null);

        if (rateCursor.moveToFirst()) {
            fatRate = rateCursor.getDouble(0); // Fetch FAT_RATE
            snf = rateCursor.getDouble(1);     // Fetch SNF
            snfRate = rateCursor.getDouble(2); // Fetch SNF_RATE

            // Update UI with fetched values
            tvFatRate.setText("FAT Rate: " + fatRate);
            tvSnf.setText("SNF: " + snf);
            tvSnfRate.setText("SNF Rate: " + snfRate);

            // Calculate per_litre = (fat / 100) * fatRate + (snf / 100) * snfRate
            perLitre = (fat / 100) * fatRate + (snf / 100) * snfRate;
            tvPerLitre.setText("Per Litre: " + String.format("%.2f", perLitre));

            // Calculate result = per_litre * weight
            result = perLitre * weight;
            tvResult.setText("Result: " + String.format("%.2f", result));
        } else {
            Toast.makeText(this, "No rate data found!", Toast.LENGTH_SHORT).show();
        }
        rateCursor.close();
    }

    private void saveDataEntry() {
        // Get selected customer ID
        String customerId = spinnerCustomerId.getSelectedItem().toString();

        // Save data to Data_Entry table
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO Data_Entry (customer_id, FAT, Weight, perLitre, result) VALUES (?, ?, ?, ?, ?)",
                new Object[]{customerId, fat, weight, perLitre, result});

        Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}

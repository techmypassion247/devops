package com.app.milkbook;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCustomerActivity extends AppCompatActivity {

    private EditText nameField, mobileField, addressField;
    private Button addButton;
    private DBHelper dbHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        // Initialize the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Customer");
        }

        // Enable the back button on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle back press for the toolbar
        handleOnBackPressed();

        // Initialize UI elements
        nameField = findViewById(R.id.nameField);
        mobileField = findViewById(R.id.mobileField);
        addressField = findViewById(R.id.addressField);
        addButton = findViewById(R.id.addButton);

        // Initialize database helper
        dbHelper = new DBHelper(this);

        // Set onClick listener for the add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                String mobile = mobileField.getText().toString();
                String address = addressField.getText().toString();

                // Insert data into database
                boolean isInserted = dbHelper.insertUserInfo(name, mobile, address);
                if (isInserted) {
                    Toast.makeText(AddCustomerActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to the main screen
                } else {
                    Toast.makeText(AddCustomerActivity.this, "Data Insertion Failed", Toast.LENGTH_SHORT).show();
                }
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

    // Handle the back button in the toolbar (up button)
    @Override
    public boolean onSupportNavigateUp() {
        // Use OnBackPressedDispatcher instead of the deprecated onBackPressed
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}

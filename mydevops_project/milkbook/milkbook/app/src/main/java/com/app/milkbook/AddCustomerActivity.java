package com.app.milkbook;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCustomerActivity extends AppCompatActivity {

    private EditText nameField, mobileField, addressField;
    private Button addButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

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
}

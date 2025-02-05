package com.app.milkbook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Button goToDataEntryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Initialize the button
        goToDataEntryButton = findViewById(R.id.goToDataEntryButton);

        // Set onClick listener for the button
        goToDataEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AddCustomerActivity
                Intent intent = new Intent(MainActivity.this, DataEntryActivity.class);
                startActivity(intent);
            }
        });
        //report button
        Button btnReport = findViewById(R.id.btn_report);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_customer) {
            Intent intent = new Intent(this, AddCustomerActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_rate) {
            // Handle Rate action
            Intent intent = new Intent(this, RateSetupActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_printer) {
            // Handle Printer action
            return true;
        } else if (id == R.id.action_settings) {
            // Handle Settings action
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

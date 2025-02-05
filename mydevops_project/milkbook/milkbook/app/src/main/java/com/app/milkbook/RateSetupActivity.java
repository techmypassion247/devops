package com.app.milkbook;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class RateSetupActivity extends AppCompatActivity {

    private EditText edtFatRate, edtSNF, edtSNFRate;
    private Button btnSaveRate;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_setup);

        edtFatRate = findViewById(R.id.edt_fat_rate);
        edtSNF = findViewById(R.id.edt_snf);
        edtSNFRate = findViewById(R.id.edt_snf_rate);
        btnSaveRate = findViewById(R.id.btn_save_rate);
        dbHelper = new DBHelper(this);

        btnSaveRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRateSetup();
            }
        });
    }

    private void saveRateSetup() {
        String fatRate = edtFatRate.getText().toString();
        String snf = edtSNF.getText().toString();
        String snfRate = edtSNFRate.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FAT_RATE", fatRate);
        values.put("SNF", snf);
        values.put("SNF_RATE", snfRate);

        db.insert("Rate_setup", null, values);
        db.close();

        finish();  // Close activity after saving
    }
}

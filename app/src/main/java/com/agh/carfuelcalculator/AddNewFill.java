package com.agh.carfuelcalculator;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.agh.carfuelcalculator.database.ConsumptionsDB;
import com.agh.carfuelcalculator.models.Consumption;

public class AddNewFill extends AppCompatActivity {

    EditText fillVolume;
    EditText mileage;

    Button mAddButton;
    Button mCancelButton;

    String carName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_fill);

        //pobiera z intencji informacje wejsciowa, nazwe samochodu
        carName = getIntent().getStringExtra("carName");

        // deklaruje obiekty
        fillVolume = (EditText) findViewById(R.id.fill_vol_edit);
        mileage = (EditText) findViewById(R.id.mileage_edit);

        mAddButton = (Button) findViewById(R.id.add_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);

        //dodaje nowy obiekt do bazy danych z klasy produkt
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = true;
                if (fillVolume.getText().toString().isEmpty()) {
                    fillVolume.setError("Wypełnij to pole!");
                    ok = false;
                }
                if (mileage.getText().toString().isEmpty()) {
                    mileage.setError("Wypełnij to pole!");
                    ok = false;
                }
                if (ok) {
                    ConsumptionsDB consumptionsDB = new ConsumptionsDB(getBaseContext(), carName);
                    consumptionsDB.storeNewRecord(new Consumption(fillVolume.getText().toString(),
                            mileage.getText().toString()));

                    finish();
                }
            }
        });

        // nie dodaje nowego obiektu do mojej bazy danych
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

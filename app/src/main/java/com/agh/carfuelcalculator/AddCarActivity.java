package com.agh.carfuelcalculator;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.agh.carfuelcalculator.database.MyCarsDB;
import com.agh.carfuelcalculator.models.Car;

public class AddCarActivity extends AppCompatActivity {

    private final int SELECT_PICTURE = 89;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompleteTextView carName = (AutoCompleteTextView)
                        findViewById(R.id.new_car_name);

                AutoCompleteTextView carYear = (AutoCompleteTextView)
                        findViewById(R.id.new_car_year);

                EditText carDetails = (EditText) findViewById(R.id.new_car_details);

                String getCarName = carName.getText().toString();
                String getCarYear = carYear.getText().toString();
                String getCarDetails = carDetails.getText().toString();

                //error pojawia sie gdy ktoras z kolumn jest wypelniona nieprawidlowo
                boolean error = false;

                if (getCarName.length() < 1) {
                    carName.setError("Nazwa jest zbyt krótka");
                    error = true;
                }
                if (getCarYear.length() == 3) {
                    carYear.setError("Wpisz rocznik składający się z 4 cyfr");
                    error = true;
                }
                if (getCarDetails.isEmpty()) {
                    getCarDetails = "brak";
                }
                if (!error) {
                    Snackbar.make(view, "Zapisano nowe autko...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    // zapisuje auto do bazy danych
                    MyCarsDB carsDB = new MyCarsDB(getApplicationContext());

                    carsDB.storeNewCar(new Car(getCarName, getCarYear, getCarDetails));

                    finish();
                }
            }
        });
    }

    public void addPhot(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();
            }
        }
    }
}

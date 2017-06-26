package com.agh.carfuelcalculator;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.agh.carfuelcalculator.adapters.ConsumptionAdapter;
import com.agh.carfuelcalculator.database.ConsumptionsDB;
import com.agh.carfuelcalculator.models.Car;

public class CarDetailActivity extends AppCompatActivity {

    static Car car;
    ConsumptionAdapter consumptionAdapter;
    ListView fillList;
    TextView details, year, consumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.app_bar1);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = getIntent();
        car = MainActivity.myCars.get(intent.getIntExtra("car", 0));

        getSupportActionBar().setTitle(car.getName());


        Button addNewFill = (Button) findViewById(R.id.add_new_fill);
        addNewFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddNewFill.class);
                intent.putExtra("carName", car.getName());

                startActivity(intent);
            }
        });

        fillList = (ListView) findViewById(R.id.fill_list);
        fillList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                car.consumptions.remove(position);
                ConsumptionsDB db = new ConsumptionsDB(getApplicationContext(), car.getName());
                db.putArray(car.consumptions);
                consumptionAdapter.notifyDataSetChanged();

                consumption.setText(car.getConsumption() + " l/100km");
                return false;
            }
        });

        details = (TextView) findViewById(R.id.details);
        year = (TextView) findViewById(R.id.year);
        consumption = (TextView) findViewById(R.id.consumption);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        car.consumptions = (new ConsumptionsDB(getBaseContext(), car.getName())).getRecords();

        consumptionAdapter = new ConsumptionAdapter(getApplicationContext(), R.layout.consumption_row,
                car.consumptions);

        details.setText(car.getDetails());
        year.setText(car.getYear());
        consumption.setText(car.getConsumption() + " l/100km");

        fillList.setAdapter(consumptionAdapter);

    }
}

package com.agh.carfuelcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.agh.carfuelcalculator.database.MyCarsDB;
import com.agh.carfuelcalculator.models.Car;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Car> myCars;
    ArrayAdapter<String> adapter;
    ListView listView;
    ArrayList<String> myCarsNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddCarActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.car_list);
        myCars = new MyCarsDB(getApplicationContext()).getCars();
        myCarsNames = new ArrayList<>();

        for (Car myCar : myCars) {
            myCarsNames.add(myCar.getName());
        }

        adapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1, myCarsNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), CarDetailActivity.class);
                intent.putExtra("car", position);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        myCars = new MyCarsDB(getApplicationContext()).getCars();
        myCarsNames = new ArrayList<>();

        for (Car myCar : myCars) {
            myCarsNames.add(myCar.getName());
        }
        adapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1, myCarsNames);
        listView.setAdapter(adapter);
    }
}

package com.agh.carfuelcalculator.database;


import android.content.Context;
import android.content.SharedPreferences;

import com.agh.carfuelcalculator.models.Car;
import com.agh.carfuelcalculator.models.Consumption;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class MyCarsDB {

    SharedPreferences carsDataBase;
    ConsumptionsDB carFuelDatabase;
    Context context;
    private static final String DB_NAME = "cars";

    public MyCarsDB(Context context){
        this.context = context;
        carsDataBase = context.getSharedPreferences(DB_NAME, 0);
    }

    public void storeNewCar(Car car){

        Set<String> cars = new HashSet<>(carsDataBase.getStringSet("cars", new HashSet<String>()));

        Calendar calendar = Calendar.getInstance();
        final String dat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault())
                .format(calendar.getTime());

        int id = cars.size();
        cars.add(id + "\n" + car.getName() + "\n" + car.getYear() + "\n" + car.getDetails() + "\n"
                + dat + "\n" + car.getImagePath());

        SharedPreferences.Editor spEditor = carsDataBase.edit();
        spEditor.putStringSet("cars", cars);
        spEditor.apply();
    }

    public ArrayList<Car> getCars(){

        ArrayList<String> carsInfo = new ArrayList<>(carsDataBase.getStringSet("cars",
                new HashSet<String>()));
        ArrayList<Car> carsTemp = new ArrayList<>();

        for (int i = 0; i < carsInfo.size(); i++){

            String[] carInfo = carsInfo.get(i).split("\n");
//cars.add(id + "\n" + car.getName() + "\n" + car.getYear() + "\n" + car.getDetails() + "\n" + dat);

            carFuelDatabase = new ConsumptionsDB(context, carInfo[1]);
            ArrayList<Consumption> consumptions = new ArrayList<>(carFuelDatabase.getRecords());

            carsTemp.add(new Car(carInfo[0], carInfo[1], carInfo[2], carInfo[3], carInfo[5],
                    consumptions));
        }
        ArrayList<Car> cars = new ArrayList<>(carsTemp);


        //układam liste po kolei wg ID
        for (Car car : carsTemp) {
            cars.set(Integer.valueOf(car.getId()), car);
        }

        return cars;
    }

    public void clearDatabase(){
        SharedPreferences.Editor spEditor = carsDataBase.edit();
        spEditor.clear();
        spEditor.apply();
    }
}

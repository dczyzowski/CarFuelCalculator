package com.agh.carfuelcalculator.database;

import android.content.Context;
import android.content.SharedPreferences;


import com.agh.carfuelcalculator.models.Consumption;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ConsumptionsDB {
    SharedPreferences userLocalDatabase;

    public ConsumptionsDB(Context context, String carId){
        userLocalDatabase = context.getSharedPreferences(carId, 0);
    }

    public void storeNewRecord(Consumption consumption){
        Set<String> consumptions = new HashSet<>(userLocalDatabase.getStringSet("consumptions", new HashSet<String>()));

        Calendar calendar = Calendar.getInstance();
        final String dat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.getDefault())
                .format(calendar.getTime());

        int id = consumptions.size();

        consumptions.add(dat + "\n" + consumption.getFill() + "\n" + consumption.getMileage() + "\n"
                + id);

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putStringSet("consumptions", consumptions);
        spEditor.apply();
    }

    public ArrayList<Consumption> getRecords(){

        ArrayList<String> recordsTemp = new ArrayList<>(userLocalDatabase.getStringSet("consumptions", new HashSet<String>()));
        ArrayList<Consumption> records = new ArrayList<>();

        for (String s : recordsTemp) {
            String[] temp = s.split("\n");
            try {
                records.add(new Consumption(Integer.valueOf(temp[3]), temp[2], temp[1], temp[0]));
            }
            catch (Exception e){

            }
        }

        ArrayList<Consumption> recordsSorted = new ArrayList<>();

        //układam liste po kolei wg ID
        for (int i = 0; i < records.size() ; i++) {
            int mileage = Integer.valueOf(records.get(i).getMileage());
            boolean notFound = true; // sprawdza czy znaleziono mniejsza liczbe

            if(!recordsSorted.isEmpty()){
                int j = 0; //index jaki bedie miala nasz liczba
                while (notFound){
                    try {
                        if(Integer.valueOf(recordsSorted.get(j).getMileage()) < mileage) {
                            notFound = false;
                            recordsSorted.add(j, records.get(i));
                        }
                    }
                    catch (IndexOutOfBoundsException e){
                        notFound = false;
                        recordsSorted.add(j, records.get(i));
                    }


                    j++;
                }
            }
            else
                recordsSorted.add(i, records.get(i));
        }

        return recordsSorted;
    }

    public void putArray(ArrayList<Consumption> array){

        ArrayList<String> stringArray = new ArrayList<>();
        for (Consumption consumption : array) {
            stringArray.add(consumption.getDate() + "\n" + consumption.getFill() + "\n" +
                    consumption.getMileage() + "\n" + consumption.getId());
        }

        Set<String> set = new HashSet<>(stringArray);

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putStringSet("consumptions", set);
        spEditor.apply();
    }

    public void clearDatabase(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }
}

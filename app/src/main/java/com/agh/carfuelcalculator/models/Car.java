package com.agh.carfuelcalculator.models;


import com.agh.carfuelcalculator.R;

import java.util.ArrayList;

public class Car {

    private String id;
    private String name;
    private String year;
    private String details;
    public ArrayList<Consumption> consumptions;
    private String imagePath;

    public Car(String id, String name, String year, String details, String imagePath,
               ArrayList<Consumption> consumptions) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.details = details;
        this.consumptions = consumptions;
        this.imagePath = imagePath;
    }

    public Car(String name, String year, String details) {
        this.name = name;
        this.year = year;
        this.details = details;
    }

    public Car(String name, String year, String details, String imagePath) {
        this.name = name;
        this.year = year;
        this.details = details;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public String getYear() {
        return year;
    }

    public String getImagePath() {
        if (imagePath == null)
            return "drawable://" + R.drawable.ic_car;
        else
            return imagePath;
    }

    public String getConsumption(){
        if (consumptions != null && !consumptions.isEmpty()){

            int distance = 0;
            int minimumDistance = Integer.valueOf(consumptions.get(0).getMileage());
            int maximumDistance = Integer.valueOf(consumptions.get(0).getMileage());
            float drained = 0;
            for (Consumption consumption : consumptions) {
                drained += Float.valueOf(consumption.getFill());
                if (Integer.valueOf(consumption.getMileage()) < minimumDistance) {
                    minimumDistance = Integer.valueOf(consumption.getMileage());
                }
                if (Integer.valueOf(consumption.getMileage()) > maximumDistance) {
                    maximumDistance = Integer.valueOf(consumption.getMileage());
                }
            }

            for (Consumption consumption : consumptions) {
                if(maximumDistance == Integer.valueOf(consumption.getMileage()))
                    drained -= Float.valueOf(consumption.getFill());
            }


            float consumption = (drained/(maximumDistance-minimumDistance))*100;

            return String.format("%.2f", consumption);
        }

        return "brak informacji";
    }
}

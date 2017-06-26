package com.agh.carfuelcalculator.models;

/**
 * Created by Damian on 2017-06-21.
 */


public class Consumption {

    private final String mileage;
    private final String fill;
    private String date;
    private int id;

    public Consumption(int id, String mileage, String fill, String date) {
        this.mileage = mileage;
        this.fill = fill;
        this.date = date;
        this.id = id;
    }

    public Consumption(String mileage, String fill) {
        this.mileage = mileage;
        this.fill = fill;
    }

    public String getFill() {
        return fill;
    }
    public String getMileage() {
        return mileage;
    }
    public String getDate() {
        return date;
    }
    public int getId() { return id; }
}
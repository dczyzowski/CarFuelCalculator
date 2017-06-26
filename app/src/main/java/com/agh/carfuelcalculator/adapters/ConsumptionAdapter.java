package com.agh.carfuelcalculator.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.agh.carfuelcalculator.R;
import com.agh.carfuelcalculator.models.Consumption;

import java.util.ArrayList;


public class ConsumptionAdapter extends ArrayAdapter<Consumption> {

    private ArrayList<Consumption> consumptionList;
    private LayoutInflater mInflater;

    public ConsumptionAdapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull ArrayList<Consumption> objects) {
        super(context, resource, objects);
        consumptionList = objects;
        mInflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        TextView filled;
        TextView mileage;
        TextView date;
        int position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.consumption_row, null);

            holder = new ViewHolder();
            holder.filled = (TextView) convertView.findViewById(R.id.filled);
            holder.mileage = (TextView) convertView.findViewById(R.id.mileage);
            holder.date = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.filled.setText(consumptionList.get(position).getFill() + " l");
        holder.date.setText(consumptionList.get(position).getDate());
        holder.mileage.setText(consumptionList.get(position).getMileage() + " km");
        holder.position = position;

        return convertView;
    }
}

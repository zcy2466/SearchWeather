package com.example.searchweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ProvinceAdapter extends ArrayAdapter<MyCity> {
    private int resourceId;
    TextView CityText;
    MyCity citys;
    View view;
    public ProvinceAdapter(@NonNull Context context, int resource, @NonNull List<MyCity> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        citys = getItem(position);

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;
        }
        CityText = (TextView) view.findViewById(R.id.city_title);
        CityText.setText(citys.getProvince() +"   ID:"+ citys.getcityId()+"");
        return view;
    }
}

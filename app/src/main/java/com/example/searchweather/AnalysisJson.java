package com.example.searchweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnalysisJson {
    public static MyCity getWeather(String json){
        MyCity city = new MyCity();
        String province = null,cityID = null,cityName = null,weather = null,refreshTime = null,temperature = null,humidity = null;
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("lives");
            for(int i=0;i<array.length();i++){
                province = array.getJSONObject(i).getString("province");
                cityID = array.getJSONObject(i).getString("adcode");
                cityName = array.getJSONObject(i).getString("city");
                weather = array.getJSONObject(i).getString("weather");
                refreshTime = array.getJSONObject(i).getString("reporttime");
                temperature = array.getJSONObject(i).getString("temperature");
                humidity = array.getJSONObject(i).getString("humidity");
            }
            if(cityID.equals("")){
                return null;
            }else{
                city.setCityname(cityName);
                city.setId(cityID);
                city.setWeather(weather);
                city.setTemperature(temperature);
                city.sethumidity(humidity);
                city.setUpdatetime(refreshTime);
                city.setProvince(province);
                return city;
            }
        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static List<MyCity> getProvince(String json) throws JSONException {
        List<MyCity> cityList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray array = jsonObject.getJSONArray("districts");
        for(int i=0;i<1;i++){
            array = array.getJSONObject(i).getJSONArray("districts");
        }
        for(int i=0;i<array.length();i++){
            String province = array.getJSONObject(i).getString("name");
            String cityID = array.getJSONObject(i).getString("adcode");
            MyCity city = new MyCity();
            city.setProvince(province);
            city.setId(cityID);
            cityList.add(city);
        }
        return cityList;
    }

    public static List<MyCity> getcitys(String json) throws JSONException {
        List<MyCity> cityList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray array = jsonObject.getJSONArray("districts");
        for(int i=0;i<1;i++){
            array = array.getJSONObject(i).getJSONArray("districts");
        }
        for(int i=0;i<array.length();i++){
            String cityName = array.getJSONObject(i).getString("name");
            String cityID = array.getJSONObject(i).getString("adcode");
            MyCity city = new MyCity();
            city.setCityname(cityName);
            city.setId(cityID);
            cityList.add(city);
        }
        return cityList;
    }
}

package com.example.searchweather;

import android.os.Parcel;
import android.os.Parcelable;

public class MyCity implements Parcelable {
    private String cityid;
    private String cityname;
    private String updatetime;
    private String temperature;
    private String humidity;
    private  String province;
    private String weather;
    public MyCity(){}

    public MyCity(String cityid,String cityname,String updatetime,String temperature,String humidity,String province,String weather){
        this.cityid = cityid;
        this.cityname = cityname;
        this.updatetime = updatetime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.province = province;
        this.weather = weather;
    }

    //将对象属性反序列化然后读取出来，注意属性的顺序必须按照序列化属性的顺序
    protected MyCity(Parcel in) {
        cityid = in.readString();
        cityname = in.readString();
        updatetime = in.readString();
        temperature = in.readString();
        humidity = in.readString();
        province = in.readString();
        weather = in.readString();
    }

    public static final Creator<MyCity> CREATOR = new Creator<MyCity>() {
        @Override
        public MyCity createFromParcel(Parcel in) {
            return new MyCity(in);
        }

        @Override
        public MyCity[] newArray(int size) {
            return new MyCity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //将对象属性进行序列化
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(cityid);
        dest.writeString(cityname);
        dest.writeString(updatetime);
        dest.writeString(temperature);
        dest.writeString(humidity);
        dest.writeString(province);
        dest.writeString(weather);

    }
    @Override
    public String toString() {
        return "MyCity{" +
                "cityid=" + cityid +
                ", cityname='" + cityname + '\'' +
                ", updatetime=" + updatetime +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", province=" + province +
                ", weather=" + weather +
                '}';
    }

    public String getcityId() {
        return cityid;
    }

    public void setId(String cityid) {
        this.cityid = cityid;
    }


    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String gethumidity() {
        return humidity;
    }

    public void sethumidity(String dampness) {
        this.humidity = dampness;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}

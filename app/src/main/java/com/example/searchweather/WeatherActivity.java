package com.example.searchweather;

import static com.example.searchweather.DatabaseHelper.TABLE_NAME;
import static com.example.searchweather.MainActivity.dbHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener {
    ImageView img_back;
    TextView city_ID,city_name,city_time,city_Temper,city_Humidity,city_province,city_weather;
    Button btn_collect,btn_refreash;
    public SQLiteDatabase db = dbHelper.getWritableDatabase();
    public MyCity getMyCity;
    public MyCity maingetMyCity;
    public static final int UPDATE_TEXT = 1;
        @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT: //主线程中，在这⾥可以进⾏UI操作
                    maingetMyCity = (MyCity) msg.obj;
                    city_ID.setText(maingetMyCity.getcityId());
                    city_name.setText(maingetMyCity.getCityname());
                    city_time.setText(maingetMyCity.getUpdatetime());
                    city_Temper.setText(maingetMyCity.getTemperature());
                    city_Humidity.setText(maingetMyCity.gethumidity());
                    city_province.setText(maingetMyCity.getProvince());
                    city_weather.setText(maingetMyCity.getWeather());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initial();
        //获取Intent传输的数据
        Intent intent=getIntent();
        String ID=intent.getStringExtra("CityId");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    String json = GetJson.getJson("https://restapi.amap.com/v3/weather/weatherInfo?city="+ ID +"&key=bce82cb405b6d89d48d43ce6c4889c18&extensions=base");
                    getMyCity = AnalysisJson.getWeather(json);
                    message.what = UPDATE_TEXT;
                    message.obj = getMyCity;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view == img_back){
            finish();
        }
        return true;
    }

    public void initial(){
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnTouchListener(this);
        city_ID = (TextView) findViewById(R.id.city_ID);
        city_name = (TextView) findViewById(R.id.city_name);
        city_time = (TextView) findViewById(R.id.city_time);
        city_Temper = (TextView) findViewById(R.id.city_Temper);
        city_Humidity = (TextView) findViewById(R.id.city_Humidity);
        btn_collect = (Button) findViewById(R.id.btn_collect);
        btn_collect.setOnClickListener(this);
        btn_refreash = (Button) findViewById(R.id.btn_refreash);
        btn_refreash.setOnClickListener(this);
        city_province = (TextView) findViewById(R.id.city_province);
        city_weather = (TextView) findViewById(R.id.city_weather);
    }

    @Override
    public void onClick(View view) {
        if(view == btn_collect){
            ContentValues values = new ContentValues();
            values.put("CityId", city_ID.getText().toString());
            values.put("CityName", city_name.getText().toString());
            long insert = db.insert(TABLE_NAME, null, values);
            if(insert == -1){
                Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "关注失败", Toast.LENGTH_SHORT).show();
            }
            values.clear();
        }
        if(view == btn_refreash){
            //获取Intent传输的数据
            Intent intent=getIntent();
            String ID=intent.getStringExtra("CityId");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    try {
                        String json = GetJson.getJson("https://restapi.amap.com/v3/weather/weatherInfo?city="+ ID +"&key=bce82cb405b6d89d48d43ce6c4889c18&extensions=base");
                        getMyCity = AnalysisJson.getWeather(json);
                        message.what = UPDATE_TEXT;
                        message.obj = getMyCity;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }).start();
            Toast.makeText(this,"刷新成功！",Toast.LENGTH_LONG).show();
        }
    }
}
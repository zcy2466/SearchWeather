package com.example.searchweather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProvinceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnTouchListener {

    List<MyCity> cityList = new ArrayList<>();
    List<MyCity> getcityList = new ArrayList<>();
    ImageView image_back;
    ProvinceAdapter adapter;
    ListView listView;
    public static final int UPDATE_TEXT = 1;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT: //主线程中，在这⾥可以进⾏UI操作
                    cityList = (List<MyCity>) msg.obj;
                    adapter=new ProvinceAdapter(ProvinceActivity.this,R.layout.cityitem,cityList);

                    listView.setAdapter(adapter);

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);

        listView =  findViewById(R.id.list_view);
        image_back = findViewById(R.id.image_back);
        image_back.setOnTouchListener(this);
        Intent intent = getIntent();
        String CityId = intent.getStringExtra("CityId");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    String json = GetJson.getJson("https://restapi.amap.com/v3/config/district?keywords=中国&subdistrict=1&key=c8a1c6e2adef7cf260226f4af82b467f");
                    getcityList = AnalysisJson.getProvince(json);
                    message.what = UPDATE_TEXT;
                    message.obj = getcityList;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
        adapter=new ProvinceAdapter(ProvinceActivity.this,R.layout.cityitem,cityList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        MyCity mycity = cityList.get(position);
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("CityId", mycity.getcityId());
        intent.putExtra("CityName", mycity.getCityname());
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view == image_back){
            finish();
        }
        return true;
    }
}
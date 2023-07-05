package com.example.searchweather;

import static com.example.searchweather.DatabaseHelper.DB_NAME;
import static com.example.searchweather.DatabaseHelper.TABLE_NAME;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    EditText et_cityID;
    Button btn_search,btn_searchbyprovince;
    ListView listView;
    List<MyCity> cityList = new ArrayList<>();
    CityAdapter adapter;


    public static DatabaseHelper dbHelper;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initial();

    }


    //初始化
    public void Initial(){
        et_cityID = findViewById(R.id.et_cityID);
        listView = findViewById(R.id.list_view);
        btn_search = findViewById(R.id.btn_search);
        btn_searchbyprovince = findViewById(R.id.btn_searchbyprovince);
        adapter=new CityAdapter(MainActivity.this,R.layout.cityitem,cityList);
        listView=findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        dbHelper = new DatabaseHelper(this,DB_NAME,null,1);
        db =dbHelper.getWritableDatabase();
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityname = et_cityID.getText().toString();
                if(cityname.equals("")){
                    Toast.makeText(MainActivity.this,"搜索不能为空",Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
                    intent.putExtra("CityId",cityname);
                    startActivityForResult(intent,0);
                }
            }
        });
        btn_searchbyprovince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityname = et_cityID.getText().toString();
                Intent intent = new Intent(MainActivity.this,ProvinceActivity.class);
                intent.putExtra("CityId",cityname);
                startActivityForResult(intent,0);
            }
        });
        RefreshListview();

    }


    //刷新列表项
    @SuppressLint("Range")
    public void RefreshListview(){
        db = dbHelper.getWritableDatabase();
        cityList.clear();

        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                MyCity city_item = new MyCity();
                city_item.setCityname(cursor.getString(cursor.getColumnIndex("CityName")));
                city_item.setId(cursor.getString(cursor.getColumnIndex("CityId")));
                cityList.add(city_item);
            }while(cursor.moveToNext());
        }else{
            Toast.makeText(this,"数据库为空",Toast.LENGTH_LONG).show();
        }
        cursor.close();

        listView.setAdapter(adapter);
    }


    //点击项目事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        MyCity mycity = cityList.get(position);
        Intent intent = new Intent(this,WeatherActivity.class);
        intent.putExtra("CityId",mycity.getcityId());
        intent.putExtra("CityName",mycity.getCityname());
        startActivityForResult(intent,1);
    }

    //回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent  data) {
        super.onActivityResult(requestCode, resultCode, data);
        RefreshListview();
    }


    //长按取消关注
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        MyCity mycity = cityList.get(position);
        AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                .setTitle(" ")
                .setMessage("是否取消关注？")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete(TABLE_NAME, "CityId=?", new String[]{String.valueOf(mycity.getcityId())});
                        Toast.makeText(MainActivity.this, "取消关注", Toast.LENGTH_SHORT).show();
                        RefreshListview();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "取消！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }).create();
        alertDialog2.show();
        return true;
    }


}
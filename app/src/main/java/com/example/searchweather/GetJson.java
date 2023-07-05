package com.example.searchweather;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetJson {
    public static String getJson(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setConnectTimeout(5000);
        connection.setRequestMethod("GET");//设置请求方式
        InputStream inStream = connection.getInputStream();//通过输入流获取html数据
        byte[] data = readInputStream(inStream);//得到html的二进制数据
        String result = new String(data, "UTF-8");
        return result;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len=inStream.read(buffer)) != -1 ){
            baos.write(buffer, 0, len);
        }
        inStream.close();
        return baos.toByteArray();
    }
}

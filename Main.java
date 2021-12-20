package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    private static HttpURLConnection connection;

    public static void main(String[] args) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL url = new URL("http://api.scb.se/OV0104/v1/doris/sv/ssd/START/BE/BE0101/BE0101H/FruktsamhetSum");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status);

            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);

                }
                reader.close();
            }
            //System.out.println(responseContent.toString());
            parse(responseContent.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

    }

    public static String parse(String responseBody){
        JSONArray kommun = new JSONArray(responseBody);
        for(int i = 0; i<kommun.length(); i++){
            JSONObject kommuns = kommun.getJSONObject(i);
            int region = kommuns.getInt("Region");
            int kon = kommuns.getInt("Kon");
            int tid = kommuns.getInt("Tid");
            String sumbirth = kommuns.getString("Summerad fruktsamhet");
            System.out.println(region + kon + tid);
        }
        return null;
    }
}

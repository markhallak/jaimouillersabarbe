package com.example.jaimouillersabarbe.models;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Map;

public class Report {
    long type;
    String area;
    String city;
    String date;
    long amount;
    String title;
    String description;
//    boolean shareable;
//    User owner;

    public static final long PAIDBRIBE = 0;
    public static final long BRIBEFIGHTER = 1;
    public static final long METHONESTOFFICER = 2;

    public Report(long type, String area, String city, String date, long amount, String title, String description) {
        this.type = type;
        this.area = area;
        this.city = city;
        this.date = date;
        this.amount = amount;
        this.title = title;
        this.description = description;
    }

    public long getType() {
        return type;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public long getAmount() {
        return amount;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @SuppressLint("NewApi")
    public static Report fromHashMap(Map map) {
        if(map == null || map.size() != 7){
            return null;
        }
        return new Report((Long) map.get("type"), (String) map.get("area"), (String) map.get("city"), (String) map.get("date"), (Long) map.get("amount"), (String) map.get("title"), (String) map.get("description"));
    }
}

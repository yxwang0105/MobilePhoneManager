package com.example.mobilephonemanager;

import org.litepal.crud.LitePalSupport;

public class TakeOutFood extends LitePalSupport {
    private int id;
    private String store;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}

package com.test.question.bean;


import java.io.Serializable;

public class CursorTableView implements Serializable{
    private String name;
    private String address;
    private int first ;
    private int end;
    private int day;


    public CursorTableView(String name, String address, String first, String end, String day) {
        this.name = name;
        this.address = address;
        this.first = Integer.parseInt(first);
        this.end = Integer.parseInt(end);
        this.day = Integer.parseInt(day);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}

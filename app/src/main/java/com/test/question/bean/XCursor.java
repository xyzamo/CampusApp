package com.test.question.bean;


import java.util.List;

import cn.bmob.v3.BmobObject;

public class XCursor extends BmobObject{
    String jdf;
    String makeup;
    String tag;
    String gpa;
    String no;
    String test;
    String total;
    Cursor Cno;

    List<String> Time;
    String address;

    public List<String> getTime() {
        return Time;
    }

    public void setTime(List<String> time) {
        Time = time;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Cursor getCno() {
        return Cno;
    }

    public void setCno(Cursor cno) {
        Cno = cno;
    }

    public String getJdf() {
        return jdf;
    }

    public void setJdf(String jdf) {
        this.jdf = jdf;
    }

    public String getMakeup() {
        return makeup;
    }

    public void setMakeup(String makeup) {
        this.makeup = makeup;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}

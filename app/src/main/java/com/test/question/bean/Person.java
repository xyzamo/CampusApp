package com.test.question.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


public class Person extends BmobUser {
    private BmobFile icon ;
    private String sno;
    private List ss;

    public List getSs() {
        return ss;
    }

    public void setSs(List ss) {
        this.ss = ss;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public BmobFile getIcon() {
        return icon;
    }
    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }


}

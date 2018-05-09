package com.test.question.bean;


import cn.bmob.v3.BmobObject;

public class Cursor extends BmobObject{
    private String ctype;
    private String Cno;
    private String Cname;
    private String Ccredit;


    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getCno() {
        return Cno;
    }

    public void setCno(String cno) {
        Cno = cno;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String caame) {
        Cname = caame;
    }

    public String getCredit() {
        return Ccredit;
    }

    public void setCredit(String credit) {
        Ccredit = credit;
    }
}

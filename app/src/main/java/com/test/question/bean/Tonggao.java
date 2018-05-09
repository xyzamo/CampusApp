package com.test.question.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Tonggao extends BmobObject{
    String title;
    String Content;

    public BmobFile getTongfile() {
        return tongfile;
    }

    public void setTongfile(BmobFile tongfile) {
        this.tongfile = tongfile;
    }

    private BmobFile tongfile;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}

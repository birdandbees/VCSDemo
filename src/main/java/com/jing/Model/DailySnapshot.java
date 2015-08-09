package com.jing.Model;

import java.io.Serializable;

/**
 * Created by jingjing on 8/7/15.
 */
public class DailySnapshot implements Serializable {

    String date;
    String account;
    String col1;
    String col2;
    String col3;
    String col4;

    public String getDate() { return date;}
    public String getAccount() { return account;}
    public String getCol1() { return col1;}
    public String getCol2() { return col2;}
    public String getCol3() { return col3;}
    public String getCol4() { return col4;}

    public void setDate(String date)
    {
        this.date = date;
    }
    public void setAccount(String account)
    {
        this.account = account;
    }
    public void setCol1(String value) { this.col1 = value;};
    public void setCol2(String value) { this.col2 = value;}
    public void setCol3(String value) { this.col3 = value;}
    public void setCol4(String value) { this.col4 = value;}
}

package com.jing.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingjing on 8/2/15.
 */
public class Person implements Serializable {

    private String name;
    private List<String> addresses;
    private String uid;

    public String getName() {
        return name;
    }

    ;

    public List<String> getAddresses() {
        return addresses;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    ;

    public void setAddresses(List<String> addresses) {
        this.addresses = new ArrayList<String>();
        this.addresses = addresses;
    }
}

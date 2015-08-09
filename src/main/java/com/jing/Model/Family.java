package com.jing.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingjing on 8/7/15.
 */
public class Family implements Serializable {

    private List<Person> family;

    public List<Person> getFamily() {
        return family;
    }
}

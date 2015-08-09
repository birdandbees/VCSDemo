package com.jing.Model;

import java.io.Serializable;

/**
 * Created by jingjing on 8/7/15.
 */
public class ChangeSet implements Serializable {
    String version;
    String delta;
    boolean baseRecord;
    public String getVersion() { return version;}
    public String getDelta() { return delta;}
    public boolean getBaseRecord() { return baseRecord;}
    public void setVersion(String version)
    {
        this.version = version;
    }
    public void setDelta(String delta)
    {
        this.delta = delta;
    }

    public void setBaseRecord(boolean isBase)
    {
        this.baseRecord = isBase;
    }

    public ChangeSet()
    {
        this.baseRecord = false;
    }
}

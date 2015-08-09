package com.jing.Service;

import com.jing.Model.DailySnapshot;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jingjing on 8/7/15.
 */
public class Parser {

    public static String getVersionFromDate(String date)
    {
        return date;
    }

    public static String serializeRecord(DailySnapshot dailySnapshot)
    {
        return  "col1," + dailySnapshot.getCol1() + ":" +
                "col2," + dailySnapshot.getCol2() + ":" +
                "col3," + dailySnapshot.getCol3() + ":" +
                "col4," + dailySnapshot.getCol4() ;
    }

    public static HashMap<String, String> parseDelta(String delta)
    {
        HashMap<String, String> result = new HashMap<>();
        String[] split= delta.split(":");
        for (String colChange: split)
        {
            int index = colChange.indexOf(',');
            result.put(colChange.substring(0, index), colChange.substring(index+1));
        }
        return result;
    }
}

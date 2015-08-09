package com.jing.Service;

/**
 * Created by jingjing on 8/2/15.
 */
public class KeyHelper {


    public static String getDeltaKey(String account, String version) {
        return "d:" + account + ':' + version;
    }

    public static String getChangeHistoryKey(String account) {
        return "ch:" + account;
    }
}

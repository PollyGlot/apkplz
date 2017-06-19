package com.example.pollyglot.apkplz.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Developer {


    public String developer;
    public String latestUpdate;
    public int appsNumber;


    public Developer() {
    }

    public Developer(String developer, String latestUpdate, int appsNumber) {
        this.developer = developer;
        this.latestUpdate = latestUpdate;
        this.appsNumber = appsNumber;

    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(String latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public int getAppsNumber() {
        return appsNumber;
    }

    public void setAppsNumber(int appsNumber) {
        this.appsNumber = appsNumber;
    }


//    @Exclude
//    public Map<String, Object> devMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("dev_name", developer);
//        result.put("latest_update", latestUpdate);
//        return result;
//    }
}

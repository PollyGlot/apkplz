package com.example.pollyglot.apkplz.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Developer {


    public String developer;
    public String information;
    public String latestApp;
    public String appsNumber;

    public Developer() {
    }

    public Developer(String developer, String information, String latestApp, String appsNumber) {
        this.developer = developer;
        this.information = information;
        this.latestApp = latestApp;
        this.appsNumber = appsNumber;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getLatestApp() {
        return latestApp;
    }

    public void setLatestApp(String latestApp) {
        this.latestApp = latestApp;
    }

    public String getAppsNumber() {
        return appsNumber;
    }

    public void setAppsNumber(String appsNumber) {
        this.appsNumber = appsNumber;
    }

    @Exclude
    public Map<String, Object> devMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dev_name", developer);
        result.put("information", information);
        result.put("latest_app", latestApp);
        result.put("apps_number", appsNumber);

        return result;
    }
}

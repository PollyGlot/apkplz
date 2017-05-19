package com.example.pollyglot.apkplz.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Apk {

//    public String uid;
    public String developer;
    public String title;
    public String version;
    public String minAndroidVersion;
    public String maxAndroidVersion;
    public String dpi;
    public String icon;
    public String description;

    public Apk() {
    }

    public Apk(String uid, String developer, String title, String version,
               String minAndroidVersion, String maxAndroidVersion, String dpi,
               String icon, String description) {
//        this.uid = uid;
        this.developer = developer;
        this.title = title;
        this.version = version;
        this.minAndroidVersion= minAndroidVersion;
        this.maxAndroidVersion = maxAndroidVersion;
        this.dpi = dpi;
        this.icon = icon;
        this.description = description;
    }


//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMinAndroidVersion() {
        return minAndroidVersion;
    }

    public void setMinAndroidVersion(String minAndroidVersion) {
        this.minAndroidVersion = minAndroidVersion;
    }

    public String getMaxAndroidVersion() {
        return maxAndroidVersion;
    }

    public void setMaxAndroidVersion(String maxAndroidVersion) {
        this.maxAndroidVersion = maxAndroidVersion;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getIcon(){
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("uid", uid);
//        result.put("developer", developer);
//        result.put("title", title);
//        result.put("version", version);
//        result.put("minAndroid", minAndroid);
//        result.put("maxAndroid", maxAndroid);
//        result.put("dpi", dpi);
//        result.put("description", description);
//
//        return result;
//    }
}

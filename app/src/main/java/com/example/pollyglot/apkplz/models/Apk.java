package com.example.pollyglot.apkplz.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class Apk {

    public String developer;
    public String title;
    public String version;
    public String minAndroidVersion;
    public String maxAndroidVersion;
    public String dpi;
    public String icon;
    public String description;
    public String imageUrl;
    public String fileUrl;

    HashMap<String, Object> timestampCreated;

    public Apk() {
    }

    public Apk(String developer, String title, String version,
               String minAndroidVersion, String maxAndroidVersion, String dpi,
               String description, String imageUrl, String fileUrl) {
        this.developer = developer;
        this.title = title;
        this.version = version;
        this.minAndroidVersion= minAndroidVersion;
        this.maxAndroidVersion = maxAndroidVersion;
        this.dpi = dpi;
        this.description = description;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;

        HashMap<String, Object> timestampNow = new HashMap<>();
        timestampNow.put("timestamp", ServerValue.TIMESTAMP);
        this.timestampCreated = timestampNow;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

//    public String getNumApp() {
//        return numApp;
//    }
//
//    public void setNumApp(String numApp) {
//        this.numApp = numApp;
//    }

    public HashMap<String, Object> getTimestampCreated(){
        return timestampCreated;
    }

    @Exclude
    public long getTimestampCreatedLong(){
        return (long)timestampCreated.get("");
    }

    @Exclude
    public Map<String, Object> apkMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("developer", developer);
        result.put("title", title);
        result.put("version", version);
        result.put("minAndroid", minAndroidVersion);
        result.put("maxAndroid", maxAndroidVersion);
        result.put("dpi", dpi);
        result.put("description", description);
        result.put("imageUrl", imageUrl);
        result.put("fileUrl", fileUrl);
        result.put("time", timestampCreated);
        return result;
    }
}

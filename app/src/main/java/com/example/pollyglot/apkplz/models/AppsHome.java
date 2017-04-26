package com.example.pollyglot.apkplz.models;

public class AppsHome {
    String name;
    String developer;
    String version;
    String imageUrl;

    public AppsHome() {}

    public AppsHome(String name, String developer, String version, String imageUrl){
        this.name = name;
        this.developer = developer;
        this.version = version;
        this.imageUrl = getLargeImageUrl(imageUrl);
    }

    public String getName(){ return name; }
    public String getDeveloper(){ return developer; }
    public String getVersion() { return version; }

    public String getLargeImageUrl(String imageUrl) {
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }


}



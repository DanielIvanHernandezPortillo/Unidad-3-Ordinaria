package com.example.danielarguello.orders.model;

/**
 * Created by DANIEL ARGUELLO on 23/02/2017.
 */

public class App {
    private String idApp;
    private String title;
    private String  description;

    public App (String idApp, String title, String description){
        this.idApp = idApp;
        this.title = title;
        this.description = description;
    }

    public App (){
        this("","","");
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "App{" +
                "idApp='" + idApp + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

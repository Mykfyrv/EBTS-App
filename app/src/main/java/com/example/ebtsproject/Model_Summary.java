package com.example.ebtsproject;

public class Model_Summary {
    private String imageUrl;
    private String confirm;
    private String name;
    private String pending;

    public Model_Summary(){

    }

    public Model_Summary(String confirm, String name, String imageUrl, String pending){
        this.confirm = confirm;
        this.name = name;
        this.imageUrl = imageUrl;
        this.pending = pending;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }
}

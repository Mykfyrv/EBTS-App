package com.example.ebtsproject;

public class Model_Book {
    private String name;
    private String rate;
    private String location;
    private String guest;
    private String dateIn;
    private String dateOut;
    private String availability;
    private String amenities;
    private String spaces;
    private String description;
    private String status;
    private String user_name;
    private String user_fName;
    private String user_email;
    private String user_phone;
    private String user_dateIn;
    private String user_dateOut;
    private String user_guest;
    private String user_total_pay;
    private String imageUrl;
    private String primaryKey;

    public Model_Book(){

    }

    public Model_Book(String name, String rate, String location, String guest, String dateIn, String dateOut, String availability, String amenities, String spaces,
                      String description, String status, String user_name, String user_fName, String user_email, String user_phone, String user_guest, String user_dateIn,
                      String user_dateOut, String user_total_pay, String imageUrl, String primaryKey){
        this.name       = name;
        this.rate       = rate;
        this.location   = location;
        this.guest      = guest;
        this.dateIn     = dateIn;
        this.dateOut    = dateOut;
        this.availability = availability;
        this.amenities    = amenities;
        this.spaces       = spaces;
        this.description  = description;
        this.status       = status;
        this.user_name    = user_name;
        this.user_fName   = user_fName;
        this.user_email   = user_email;
        this.user_phone   = user_phone;
        this.user_guest   = user_guest;
        this.user_dateIn  = user_dateIn;
        this.user_dateOut = user_dateOut;
        this.user_total_pay = user_total_pay;
        this.imageUrl       = imageUrl;
        this.primaryKey     = primaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getSpaces() {
        return spaces;
    }

    public void setSpaces(String spaces) {
        this.spaces = spaces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_fName() {
        return user_fName;
    }

    public void setUser_fName(String user_fName) {
        this.user_fName = user_fName;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_guest() {
        return user_guest;
    }

    public void setUser_guest(String user_guest) {
        this.user_guest = user_guest;
    }

    public String getUser_dateIn() {
        return user_dateIn;
    }

    public void setUser_dateIn(String user_dateIn) {
        this.user_dateIn = user_dateIn;
    }

    public String getUser_dateOut() {
        return user_dateOut;
    }

    public void setUser_dateOut(String user_dateOut) {
        this.user_dateOut = user_dateOut;
    }

    public String getUser_total_pay() {
        return user_total_pay;
    }

    public void setUser_total_pay(String user_total_pay) {
        this.user_total_pay = user_total_pay;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}

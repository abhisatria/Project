package com.example.project.Model;

public class Boarding {
    private int id;
    private String name;
    private String facility;
    private long price;
    private String description;
    private String longitude;
    private String latitude;
    String  images;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Boarding(int id, String name, String facility, long price, String description, String longitude, String latitude,String images) {
        this.id = id;
        this.name = name;
        this.facility = facility;
        this.price = price;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.images = images;
    }
}

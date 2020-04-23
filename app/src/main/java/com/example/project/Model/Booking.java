package com.example.project.Model;

public class Booking {
    private String bookingID;
    private String userID;
    private String kosName;
    private String kosPrice;
    private String kosFacility;
    private String bookingDate;
    private int gambarKos;

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getKosName() {
        return kosName;
    }

    public void setKosName(String kosName) {
        this.kosName = kosName;
    }

    public String getKosPrice() {
        return kosPrice;
    }

    public void setKosPrice(String kosPrice) {
        this.kosPrice = kosPrice;
    }

    public String getKosFacility() {
        return kosFacility;
    }

    public void setKosFacility(String kosFacility) {
        this.kosFacility = kosFacility;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getGambarKos() {
        return gambarKos;
    }

    public void setGambarKos(int gambarKos) {
        this.gambarKos = gambarKos;
    }

    public Booking(String bookingID, String userID, String kosName, String kosPrice, String kosFacility, String bookingDate, int gambarKos) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.kosName = kosName;
        this.kosPrice = kosPrice;
        this.kosFacility = kosFacility;
        this.bookingDate = bookingDate;
        this.gambarKos = gambarKos;
    }


}

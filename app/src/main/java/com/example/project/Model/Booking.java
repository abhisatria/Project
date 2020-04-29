package com.example.project.Model;

public class Booking {
    private String bookingID;
    private String userID;
    private String kosName;
    private String kosPrice;
    private String kosFacility;
    private String bookingDate;
    private String kosDescription;
    private String kosLatitude;
    private String kosLongitude;
    private String gambarKos;

    public Booking(String bookingID, String userID, String kosName, String kosPrice, String kosFacility, String bookingDate, String kosDescription, String kosLatitude, String kosLongitude, String gambarKos) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.kosName = kosName;
        this.kosPrice = kosPrice;
        this.kosFacility = kosFacility;
        this.bookingDate = bookingDate;
        this.kosDescription = kosDescription;
        this.kosLatitude = kosLatitude;
        this.kosLongitude = kosLongitude;
        this.gambarKos = gambarKos;
    }

    public Booking() {

    }


    public String getKosDescription() {
        return kosDescription;
    }

    public void setKosDescription(String kosDescription) {
        this.kosDescription = kosDescription;
    }

    public String getKosLatitude() {
        return kosLatitude;
    }

    public void setKosLatitude(String kosLatitude) {
        this.kosLatitude = kosLatitude;
    }

    public String getKosLongitude() {
        return kosLongitude;
    }

    public void setKosLongitude(String kosLongitude) {
        this.kosLongitude = kosLongitude;
    }



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

    public String getGambarKos() {
        return gambarKos;
    }

    public void setGambarKos(String gambarKos) {
        this.gambarKos = gambarKos;
    }

//    public Booking(String bookingID, String userID, String kosName, String kosPrice, String kosFacility, String bookingDate, int gambarKos) {
//        this.bookingID = bookingID;
//        this.userID = userID;
//        this.kosName = kosName;
//        this.kosPrice = kosPrice;
//        this.kosFacility = kosFacility;
//        this.bookingDate = bookingDate;
//        this.gambarKos = gambarKos;
//    }


}

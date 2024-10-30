package com.android.luxevista;

public class Booking {
    private int bookingId;
    private String bookingTitle;
    private String bookingType;
    private String checkInDate;
    private String checkOutDate;
    private String bookingDate;
    private String bookingStatus;
    private String specialRequests;
    private double totalPrice;
    private double taxes;
    private String guestName;
    private String guestEmail;
    private String guestPhone;

    public Booking(int bookingId, String bookingTitle, String bookingType, String checkInDate, String checkOutDate, String bookingDate, String bookingStatus, String specialRequests, double totalPrice, double taxes, String guestName, String guestEmail, String guestPhone) {
        this.bookingId = bookingId;
        this.bookingTitle = bookingTitle;
        this.bookingType = bookingType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.specialRequests = specialRequests;
        this.totalPrice = totalPrice;
        this.taxes = taxes;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
    }

    public Booking(String bookingTitle, String bookingType, String checkInDate, String checkOutDate, String bookingDate, String bookingStatus, String specialRequests, double totalPrice, double taxes, String guestName, String guestEmail, String guestPhone) {
        this.bookingTitle = bookingTitle;
        this.bookingType = bookingType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.specialRequests = specialRequests;
        this.totalPrice = totalPrice;
        this.taxes = taxes;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingTitle() {
        return bookingTitle;
    }

    public void setBookingTitle(String bookingTitle) {
        this.bookingTitle = bookingTitle;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }
}

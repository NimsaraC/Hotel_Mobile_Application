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
    private String userId;
    private String roomId;

    //Services
    private String bookingTime;
    private String serviceId;
    private String duration;
    private String numberOfGuests;
    private String capacity;
    private String dayType;

    //Explore
    private String exploreId;

    //getData
    public Booking(int bookingId, String bookingTitle, String bookingType, String checkInDate, String checkOutDate, String bookingDate, String bookingStatus, String specialRequests, double totalPrice, double taxes, String guestName, String guestEmail, String guestPhone, String userId, String roomId, String bookingTime, String serviceId, String duration, String numberOfGuests, String capacity, String dayType, String exploreId) {
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
        this.userId = userId;
        this.roomId = roomId;
        this.bookingTime = bookingTime;
        this.serviceId = serviceId;
        this.duration = duration;
        this.numberOfGuests = numberOfGuests;
        this.capacity = capacity;
        this.dayType = dayType;
        this.exploreId = exploreId;
    }

    //Room
    public Booking(String bookingTitle, String bookingType, String checkInDate, String checkOutDate, String bookingDate, String bookingStatus, String specialRequests, double totalPrice, double taxes, String guestName, String guestEmail, String guestPhone, String userId, String roomId) {
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
        this.userId = userId;
        this.roomId = roomId;
    }

    //SpaService
    public Booking(String bookingTitle, String bookingType, String bookingDate, String bookingStatus, String specialRequests, double totalPrice, double taxes, String guestName, String guestEmail, String guestPhone, String userId, String bookingTime, String serviceId, String duration) {
        this.bookingTitle = bookingTitle;
        this.bookingType = bookingType;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.specialRequests = specialRequests;
        this.totalPrice = totalPrice;
        this.taxes = taxes;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.userId = userId;
        this.bookingTime = bookingTime;
        this.serviceId = serviceId;
        this.duration = duration;
    }

    //Dining
    public Booking(String bookingTitle, String bookingType, String bookingDate, String bookingStatus, String specialRequests, double totalPrice, double taxes, String guestName, String guestEmail, String guestPhone, String userId, String serviceId, String numberOfGuests) {
        this.bookingTitle = bookingTitle;
        this.bookingType = bookingType;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.specialRequests = specialRequests;
        this.totalPrice = totalPrice;
        this.taxes = taxes;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.userId = userId;
        this.serviceId = serviceId;
        this.numberOfGuests = numberOfGuests;
    }

    //PoolService
    public Booking(String bookingTitle, String bookingType, String bookingDate, String bookingStatus, String specialRequests, double totalPrice, String guestName, String guestEmail, String guestPhone, String userId, String serviceId, String capacity, String dayType) {
        this.bookingTitle = bookingTitle;
        this.bookingType = bookingType;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.specialRequests = specialRequests;
        this.totalPrice = totalPrice;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.userId = userId;
        this.serviceId = serviceId;
        this.capacity = capacity;
        this.dayType = dayType;
    }

    //Explore
    public Booking(String bookingTitle, String bookingType, String bookingDate, String bookingStatus, String specialRequests, double totalPrice, String guestName, String guestEmail, String guestPhone, String userId, String exploreId, String numberOfGuests) {
        this.bookingTitle = bookingTitle;
        this.bookingType = bookingType;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.specialRequests = specialRequests;
        this.totalPrice = totalPrice;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.userId = userId;
        this.exploreId = exploreId;
        this.numberOfGuests = numberOfGuests;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(String numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getExploreId() {
        return exploreId;
    }

    public void setExploreId(String exploreId) {
        this.exploreId = exploreId;
    }
}

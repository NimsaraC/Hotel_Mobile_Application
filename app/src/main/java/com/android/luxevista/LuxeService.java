package com.android.luxevista;

import java.util.List;

public class LuxeService {
    private int id;
    private String serviceTitle;
    private String serviceType;
    private String serviceDescription;
    private String duration;
    private double price;
    private String cuisine;
    private String reservation;
    private String capacity;
    private String amenities;
    private String bookingInstruction;
    private String cancellationPolicy;
    private String coverImage;
    private List<String> additionalImages;

    public LuxeService(int id, String serviceTitle, String serviceType, String serviceDescription, String duration, double price, String cuisine, String reservation, String capacity, String amenities, String bookingInstruction, String cancellationPolicy, String coverImage, List<String> additionalImages) {
        this.id = id;
        this.serviceTitle = serviceTitle;
        this.serviceType = serviceType;
        this.serviceDescription = serviceDescription;
        this.duration = duration;
        this.price = price;
        this.cuisine = cuisine;
        this.reservation = reservation;
        this.capacity = capacity;
        this.amenities = amenities;
        this.bookingInstruction = bookingInstruction;
        this.cancellationPolicy = cancellationPolicy;
        this.coverImage = coverImage;
        this.additionalImages = additionalImages;
    }

    public LuxeService(String serviceTitle, String serviceType, String serviceDescription, String duration, double price, String coverImage, String cancellationPolicy, String bookingInstruction, List<String> additionalImages) {
        this.serviceTitle = serviceTitle;
        this.serviceType = serviceType;
        this.serviceDescription = serviceDescription;
        this.duration = duration;
        this.price = price;
        this.coverImage = coverImage;
        this.cancellationPolicy = cancellationPolicy;
        this.bookingInstruction = bookingInstruction;
        this.additionalImages = additionalImages;
    }

    public LuxeService(String serviceTitle, String serviceType, String serviceDescription, String cuisine, String reservation, List<String> additionalImages, String coverImage, String cancellationPolicy, String bookingInstruction) {
        this.serviceTitle = serviceTitle;
        this.serviceType = serviceType;
        this.serviceDescription = serviceDescription;
        this.cuisine = cuisine;
        this.reservation = reservation;
        this.additionalImages = additionalImages;
        this.coverImage = coverImage;
        this.cancellationPolicy = cancellationPolicy;
        this.bookingInstruction = bookingInstruction;
    }

    public LuxeService(String serviceTitle, String serviceType, String serviceDescription, double price, List<String> additionalImages, String coverImage, String cancellationPolicy, String bookingInstruction, String amenities, String capacity) {
        this.serviceTitle = serviceTitle;
        this.serviceType = serviceType;
        this.serviceDescription = serviceDescription;
        this.price = price;
        this.additionalImages = additionalImages;
        this.coverImage = coverImage;
        this.cancellationPolicy = cancellationPolicy;
        this.bookingInstruction = bookingInstruction;
        this.amenities = amenities;
        this.capacity = capacity;
    }

    public LuxeService() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getBookingInstruction() {
        return bookingInstruction;
    }

    public void setBookingInstruction(String bookingInstruction) {
        this.bookingInstruction = bookingInstruction;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<String> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(List<String> additionalImages) {
        this.additionalImages = additionalImages;
    }
}

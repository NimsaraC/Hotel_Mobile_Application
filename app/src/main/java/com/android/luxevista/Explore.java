package com.android.luxevista;

import java.util.List;

public class Explore {
    private int id;
    private String title;
    private String type;
    private String description;
    private String duration;
    private double price;
    private String bookingDetails;
    private String specialNote;
    private String coverImage;
    private List<String> additionalImages;

    public Explore(int id, String title, String type, String description, String duration, double price, String bookingDetails, String specialNote, String coverImage, List<String> additionalImages) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.bookingDetails = bookingDetails;
        this.specialNote = specialNote;
        this.coverImage = coverImage;
        this.additionalImages = additionalImages;
    }

    public Explore(String title, String type, String description, String duration, double price, String bookingDetails, String specialNote, String coverImage, List<String> additionalImages) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.bookingDetails = bookingDetails;
        this.specialNote = specialNote;
        this.coverImage = coverImage;
        this.additionalImages = additionalImages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(String bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
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

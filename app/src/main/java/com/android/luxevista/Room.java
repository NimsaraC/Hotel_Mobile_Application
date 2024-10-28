package com.android.luxevista;

import java.util.List;

public class Room {

    private String roomId;
    private String roomType;
    private String description;
    private String roomSize;
    private String bedType;
    private String view;
    private int occupancy;
    private String amenities;
    private String additionalServices;
    private String checkInTime;
    private String checkOutTime;
    private String cancellationPolicy;
    private String noSmokingPolicy;
    private double rate;
    private String coverImage;
    private List<String> additionalImages;

    public Room(String roomType, String description, String roomSize, String bedType, String view, int occupancy, String amenities, String additionalServices, String checkInTime, String checkOutTime, String cancellationPolicy, String noSmokingPolicy, double rate, String coverImage, List<String> additionalImages) {
        this.roomType = roomType;
        this.description = description;
        this.roomSize = roomSize;
        this.bedType = bedType;
        this.view = view;
        this.occupancy = occupancy;
        this.amenities = amenities;
        this.additionalServices = additionalServices;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.cancellationPolicy = cancellationPolicy;
        this.noSmokingPolicy = noSmokingPolicy;
        this.rate = rate;
        this.coverImage = coverImage;
        this.additionalImages = additionalImages;
    }

    public Room(String roomId, String roomType, String description, String roomSize, String bedType,
                String view, int occupancy, String amenities, String additionalServices, String checkInTime,
                String checkOutTime, String cancellationPolicy, String noSmokingPolicy, double rate,
                String coverImage, List<String> additionalImages) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.description = description;
        this.roomSize = roomSize;
        this.bedType = bedType;
        this.view = view;
        this.occupancy = occupancy;
        this.amenities = amenities;
        this.additionalServices = additionalServices;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.cancellationPolicy = cancellationPolicy;
        this.noSmokingPolicy = noSmokingPolicy;
        this.rate = rate;
        this.coverImage = coverImage;
        this.additionalImages = additionalImages;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(String additionalServices) {
        this.additionalServices = additionalServices;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getNoSmokingPolicy() {
        return noSmokingPolicy;
    }

    public void setNoSmokingPolicy(String noSmokingPolicy) {
        this.noSmokingPolicy = noSmokingPolicy;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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


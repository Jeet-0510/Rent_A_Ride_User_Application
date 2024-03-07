package com.example.vehicleapplication;

public class CarData {
    private String carName;
    private String carDesc;
    private String carLocation;
    private String carRupees;
    private String carImage;

    public String getCarKey() {
        return carKey;
    }

    public void setCarKey(String carKey) {
        this.carKey = carKey;
    }

    private String carKey;

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarDesc() {
        return carDesc;
    }

    public void setCarDesc(String carDesc) {
        this.carDesc = carDesc;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public String getCarRupees() {
        return carRupees;
    }

    public void setCarRupees(String carRupees) {
        this.carRupees = carRupees;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public CarData(){}

    public CarData(String carName, String carDesc, String carLocation, String carRupees, String carImage) {
        this.carName = carName;
        this.carDesc = carDesc;
        this.carLocation = carLocation;
        this.carRupees = carRupees;
        this.carImage = carImage;
    }
}

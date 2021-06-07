package com.jk.parkingproject.models;

import java.util.Date;

public class Parking {

    private String id;
    private String carNumber;
    private String buildingCode;
    private String hostSuiteNumber;
    private Date dateOfParking;
    private String noOfHours;


    public Parking(String buildingCode, String hostSuiteNumber, Date dateOfParking, String noOfHours) {
        this.buildingCode = buildingCode;
        this.hostSuiteNumber = hostSuiteNumber;
        this.dateOfParking = dateOfParking;
        this.noOfHours = noOfHours;
    }

    public Parking(){

    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public void setHostSuiteNumber(String hostSuiteNumber) {
        this.hostSuiteNumber = hostSuiteNumber;
    }

    public void setDateOfParking(Date dateOfParking) {
        this.dateOfParking = dateOfParking;
    }

    public void setNoOfHours(String noOfHours) {
        this.noOfHours = noOfHours;
    }

    public String getId() {
        return id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public String getHostSuiteNumber() {
        return hostSuiteNumber;
    }

    public Date getDateOfParking() {
        return dateOfParking;
    }

    public String getNoOfHours() {
        return noOfHours;
    }

    @Override
    public String toString() {
        return "Parking{" +
                "id='" + id + '\'' +
                ", buildingCode='" + buildingCode + '\'' +
                ", hostSuiteNumber='" + hostSuiteNumber + '\'' +
                ", dateOfParking=" + dateOfParking +
                ", noOfHours='" + noOfHours + '\'' +
                '}';
    }
}

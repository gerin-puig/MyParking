package com.jk.parkingproject.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Parking implements Serializable {

    private String id;
    private String email;
    private String carPlateNumber;
    private String buildingCode;
    private String hostSuiteNumber;
    private Date dateOfParking;
    private String timeOfParking;
    private String noOfHours;


    public Parking(String carPlateNumber, String buildingCode, String hostSuiteNumber, Date dateOfParking, String timeOfParking, String noOfHours) {
        this.carPlateNumber = carPlateNumber;
        this.buildingCode = buildingCode;
        this.hostSuiteNumber = hostSuiteNumber;
        this.dateOfParking = dateOfParking;
        this.timeOfParking = timeOfParking;
        this.noOfHours = noOfHours;
    }

    public Parking(){

    }


    public void setId(String id) {
        this.id = id;
    }

    public String getTimeOfParking() {
        return timeOfParking;
    }


    public void setTimeOfParking(String timeOfParking) {
        this.timeOfParking = timeOfParking;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
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

    public String getCarPlateNumber() {
        return carPlateNumber;
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

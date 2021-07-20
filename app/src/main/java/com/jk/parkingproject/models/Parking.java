package com.jk.parkingproject.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */

public class Parking implements Serializable {

    private String id;
    private String email;
    private String carPlateNumber;
    private String buildingCode;
    private String hostSuiteNumber;
    private Date dateOfParking;
    private String noOfHours;
    private double latitude;
    private double longitude;

    public Parking(String carPlateNumber, String buildingCode, String hostSuiteNumber, Date dateOfParking, String noOfHours) {
        this.carPlateNumber = carPlateNumber;
        this.buildingCode = buildingCode;
        this.hostSuiteNumber = hostSuiteNumber;
        this.dateOfParking = dateOfParking;
        this.noOfHours = noOfHours;
    }

    public Parking(){

    }

    public void setId(String id) {
        this.id = id;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

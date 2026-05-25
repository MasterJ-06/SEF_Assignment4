package sef.assignment4;

import java.util.ArrayList;

public class DriverRepository {
    private ArrayList<Driver> drivers;
    private int count;

    public DriverRepository() {
        this.count = 0;
        this.drivers = new ArrayList<>();
    }

    public void addDriver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {

    }

    public void updateDriver(String driverID, int experienceYears, String licenseType, String address, String birthdate) {

    }

    public void retrieveDriver(String driverID) {

    }

    public int countDrivers() {
        return this.count;
    }
}

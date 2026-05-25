package sef.assignment4;

import java.util.ArrayList;

public class DriverRepository {
    private ArrayList<Driver> drivers;
    private int count;
    private String filename;

    public DriverRepository(String filename) {
        this.count = 0;
        this.drivers = new ArrayList<>();
        this.filename = filename;
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

    public void saveData() {

    }

    public void loadData() {

    }
}

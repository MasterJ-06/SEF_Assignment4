package sef.assignment4;

import java.io.*;
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
        Driver driver = new Driver(driverID, name, experienceYears, licenseType, address, birthdate);
        this.drivers.add(driver);
        ++this.count;
    }

    public void updateDriver(String driverID, int experienceYears, String licenseType, String address, String birthdate) {

    }

    public void retrieveDriver(String driverID) {

    }

    public int countDrivers() {
        return this.count;
    }

    public void saveData() {
        try {
            new FileOutputStream(filename).close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            for (Driver driver : drivers) {
                writer.write(driver.getDriverID() + "|" + driver.getName() + "|" + driver.getExperienceYears() + "|" + driver.getLicenseType() + "|" + driver.getAddress() + "|" + driver.getBirthdate());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {

    }
}

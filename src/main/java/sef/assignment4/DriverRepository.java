package sef.assignment4;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DriverRepository {
    private ArrayList<Driver> drivers;
    private String filename;

    public DriverRepository(String filename) {
        this.drivers = new ArrayList<>();
        this.filename = filename;
    }

    public void addDriver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {
        Driver driver = new Driver(driverID, name, experienceYears, licenseType, address, birthdate);
        this.drivers.add(driver);
    }

    public void updateDriver(String driverID, int experienceYears, String licenseType, String address, String birthdate) {

    }

    public Driver retrieveDriver(String driverID) {
        Driver foundDriver = null;
        for (Driver driver : this.drivers) {
            if (driver.getDriverID().equals(driverID)) {
                foundDriver = driver;
                break;
            }
        }
        return foundDriver;
    }

    public int countDrivers() {
        return this.drivers.size();
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
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split("\\|");
                this.addDriver(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4], values[5]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

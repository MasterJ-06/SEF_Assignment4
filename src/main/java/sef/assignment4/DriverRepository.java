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

    public String getFilename() {
        return this.filename;
    }

    public void addDriver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {
        for (Driver driver : this.drivers) {
            if (driver.getDriverID().equals(driverID)) {
                throw new IllegalArgumentException("Driver ID already exists");
            }
        }
        if (driverID.length() != 10) {
            throw new IllegalArgumentException("Driver ID must be 10 characters long");
        }
        char char1 = driverID.charAt(0);
        char char2 = driverID.charAt(1);
        if (!(char1 >= '2' && char1 <= '9' && char2 >= '2' && char2 <= '9')) {
            throw new IllegalArgumentException("The first two characters of the driver ID must be a number between 2 and 9");
        }
        char char9 = driverID.charAt(8);
        char char10 = driverID.charAt(9);
        if (!(char9 >= 'A' && char9 <= 'Z' && char10 >= 'A' && char10 <= 'Z')) {
            throw new IllegalArgumentException("The last two characters of the driver ID must be a capital letter between A and Z");
        }
        int specialCharacterCount = 0;
        for (int i = 2; i < 8; ++i) {
            if (!Character.isLetterOrDigit(driverID.charAt(i)) && !Character.isWhitespace(driverID.charAt(i))) {
                ++specialCharacterCount;
            }
        }
        if (specialCharacterCount < 2) {
            throw new IllegalArgumentException("The driver ID must contain at least two special characters");
        }
        if (!driverAddressVaild(address)) {
            throw new IllegalArgumentException("The address is not in the correct format");
        }
        if (!driverBirthdateVaild(birthdate)) {
            throw new IllegalArgumentException("The birthdate is not in the correct format");
        }
        Driver driver = new Driver(driverID, name, experienceYears, licenseType, address, birthdate);
        this.drivers.add(driver);
    }

    public void updateDriver(String driverIDToUpdate, String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {
        Driver driverToUpdate = retrieveDriver(driverIDToUpdate);
        if (driverToUpdate != null) {
            if (experienceYears != -1) {
                driverToUpdate.setExperienceYears(experienceYears);
            }
            if (licenseType != null && experienceYears <= 10) {
                driverToUpdate.setLicenseType(licenseType);
            }
            if (address != null && !driverAddressVaild(address)) {
                throw new IllegalArgumentException("The address is not in the correct format");
            }
            if (birthdate != null && !driverBirthdateVaild(birthdate)) {
                throw new IllegalArgumentException("The birthdate is not in the correct format");
            }
            if (address != null) {
                driverToUpdate.setAddress(address);
            }
            if (birthdate != null) {
                driverToUpdate.setBirthdate(birthdate);
            }
        }
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
                writer.write(driver.getDriverID() + "," + driver.getName() + "," + driver.getExperienceYears() + "," + driver.getLicenseType() + "," + driver.getAddress() + "," + driver.getBirthdate());
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
                String[] values = line.split(",");
                this.addDriver(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4], values[5]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean driverAddressVaild(String address) {
        return address.matches("^[0-9]+\\|[^|]+\\|[^|]+\\|[^|]+\\|[^|]+$");
    }

    private boolean driverBirthdateVaild(String birthdate) {
        return birthdate.matches("^[0-9]{2}-[0-9]{2}-[0-9]{4}$");
    }
}

package sef.assignment4;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class BusRepository {
    private ArrayList<Bus> buses;
    private String filename;
    private String driversFilename;

    public BusRepository(String filename, String driversFilename) {
        this.buses = new ArrayList<>();
        this.filename = filename;
        this.driversFilename = driversFilename;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getDriversFilename() {
        return this.driversFilename;
    }

    public void addBus(String busID, int capacity, double fuelLevel, String fuelType, String driverID) {
        for (Bus bus : this.buses) {
            if (bus.getBusID().equals(busID)) {
                throw new IllegalArgumentException("Bus ID already exists");
            }
        }
        if (busID.length() != 8) {
            throw new IllegalArgumentException("Bus ID must be 8 characters long");
        }
        for (char character : busID.toCharArray()) {
            if (!Character.isDigit(character)) {
                throw new IllegalArgumentException("Bus ID must contain only digits");
            }
        }
        DriverRepository repository = new DriverRepository(this.driversFilename);
        repository.loadData();
        Driver driver = repository.retrieveDriver(driverID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate dob = LocalDate.parse(driver.getBirthdate(), formatter);
            LocalDate today = LocalDate.now();
            int years = Period.between(dob, today).getYears();
            if (capacity >= 50 && years > 50) {
                throw new IllegalArgumentException("Drivers older than 50 years cannot drive buses with a capacity of 50 or more");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("The birthdate is not in the correct format");
        }
        if (fuelType.equals("Electricity") && driver.getExperienceYears() < 5) {
            throw new IllegalArgumentException("Drivers with less than 5 years of experience cannot drive buses with electricity fuel");
        }
        if ((driver.getLicenseType().equals("Light") && fuelType.equals("Electricity")) || (driver.getLicenseType().equals("Light") && fuelType.equals("Hybrid")) ||
                (driver.getLicenseType().equals("Medium") && fuelType.equals("Electricity")) || driver.getLicenseType().equals("Medium") && fuelType.equals("Hybrid")) {
            throw new IllegalArgumentException("Only drivers holding a Heavy or Public Transport licence are permitted to operate electric and hybrid buses");
        }
        Bus bus = new Bus(busID, capacity, fuelLevel, fuelType, driverID);
        this.buses.add(bus);
    }

    public void updateBus(String busID, int capacityChange, double fuelLevel, String driverID) {
        Bus busToUpdate = retrieveBus(busID);
        if (busToUpdate != null) {
            if (capacityChange != 0) {
                if (capacityChange > 0) {
                    throw new IllegalArgumentException("Capacity cannot be increased");
                }
                busToUpdate.setCapacity(busToUpdate.getCapacity() + capacityChange);
            }
            Driver driver = null;
            if (driverID == null) {
                DriverRepository repository = new DriverRepository(this.driversFilename);
                repository.loadData();
                driver = repository.retrieveDriver(busToUpdate.getDriverID());
            } else {
                DriverRepository repository = new DriverRepository(this.driversFilename);
                repository.loadData();
                driver = repository.retrieveDriver(driverID);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                LocalDate dob = LocalDate.parse(driver.getBirthdate(), formatter);
                LocalDate today = LocalDate.now();
                int years = Period.between(dob, today).getYears();
                if (busToUpdate.getCapacity() >= 50 && years > 50) {
                    throw new IllegalArgumentException("Drivers older than 50 years cannot drive buses with a capacity of 50 or more");
                }
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("The birthdate is not in the correct format");
            }
            if (busToUpdate.getFuelType().equals("Electricity") && driver.getExperienceYears() < 5) {
                throw new IllegalArgumentException("Drivers with less than 5 years of experience cannot drive buses with electricity fuel");
            }
            if ((driver.getLicenseType().equals("Light") && busToUpdate.getFuelType().equals("Electricity")) || (driver.getLicenseType().equals("Light") && busToUpdate.getFuelType().equals("Hybrid")) ||
                    (driver.getLicenseType().equals("Medium") && busToUpdate.getFuelType().equals("Electricity")) || driver.getLicenseType().equals("Medium") && busToUpdate.getFuelType().equals("Hybrid")) {
                throw new IllegalArgumentException("Only drivers holding a Heavy or Public Transport licence are permitted to operate electric and hybrid buses");
            }
            if (driverID != null && !(driverID.equals(busToUpdate.getDriverID()))) {
                busToUpdate.setDriverID(driverID);
            }
            if (fuelLevel < 0) {
                throw new IllegalArgumentException("Fuel level cannot be negative");
            }
            if (fuelLevel >= 0) {
                busToUpdate.setFuelLevel(fuelLevel);
            }
        }
    }

    public Bus retrieveBus(String busID) {
        Bus foundBus = null;
        for (Bus bus : this.buses) {
            if (bus.getBusID().equals(busID)) {
                foundBus = bus;
                break;
            }
        }
        return foundBus;
    }

    public int countBuses() {
        return this.buses.size();
    }

    public void saveData() {
        try {
            new FileOutputStream(filename).close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            for (Bus bus : buses) {
                writer.write(bus.getBusID() + "," + bus.getCapacity() + "," + bus.getFuelLevel() + "," + bus.getFuelType() + "," + bus.getDriverID());
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
                this.addBus(values[0], Integer.parseInt(values[1]), Double.parseDouble(values[2]), values[3], values[4]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

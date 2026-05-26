package sef.assignment4;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BusRepository {
    private ArrayList<Bus> buses;
    private String filename;

    public BusRepository(String filename) {
        this.buses = new ArrayList<>();
        this.filename = filename;
    }

    public void addBus(String busID, int capacity, double fuelLevel, String fuelType, String driverID) {
        Bus bus = new Bus(busID, capacity, fuelLevel, fuelType, driverID);
        this.buses.add(bus);
    }

    public void updateBus(String busID, int capacity, double fuelLevel, String fuelType, String driverID) {

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
                writer.write(bus.getBusID() + "|" + bus.getCapacity() + "|" + bus.getFuelLevel() + "|" + bus.getFuelType() + "|" + bus.getDriverID());
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
                this.addBus(values[0], Integer.parseInt(values[1]), Double.parseDouble(values[2]), values[3], values[4]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

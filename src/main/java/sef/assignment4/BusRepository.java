package sef.assignment4;

import java.io.*;
import java.util.ArrayList;

public class BusRepository {
    private ArrayList<Bus> buses;
    private int count;
    private String filename;

    public BusRepository(String filename) {
        this.count = 0;
        this.buses = new ArrayList<>();
        this.filename = filename;
    }

    public void addBus(String busID, int capacity, double fuelLevel, String fuelType) {
        Bus bus = new Bus(busID, capacity, fuelLevel, fuelType);
        this.buses.add(bus);
        ++this.count;
    }

    public void updateBus(String busID, int capacity, double fuelLevel, String fuelType) {

    }

    public void retrieveBus(String busID) {

    }

    public int countBuses() {
        return this.count;
    }

    public void saveData() {
        try {
            new FileOutputStream(filename).close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            for (Bus bus : buses) {
                writer.write(bus.getBusID() + "|" + bus.getCapacity() + "|" + bus.getFuelLevel() + "|" + bus.getFuelType());
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

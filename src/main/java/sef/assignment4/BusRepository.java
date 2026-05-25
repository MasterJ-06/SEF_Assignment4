package sef.assignment4;

import java.util.ArrayList;

public class BusRepository {
    private ArrayList<Bus> buses;
    private int count;

    public BusRepository() {
        this.count = 0;
        this.buses = new ArrayList<>();
    }

    public void addBus(String busID, int capacity, double fuelLevel, String fuelType) {

    }

    public void updateBus(String busID, int capacity, double fuelLevel, String fuelType) {

    }

    public void retrieveBus(String busID) {

    }

    public int countBuses() {
        return this.count;
    }

    public void saveData() {

    }
}

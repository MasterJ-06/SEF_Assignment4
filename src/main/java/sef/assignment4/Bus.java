package sef.assignment4;

public class Bus {
    private String busID;
    private int capacity;
    private double fuelLevel;
    private String fuelType;

    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {
        this.busID = busID;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;
        this.fuelType = fuelType;
    }

    public String getBusID() {
        return this.busID;
    }

    public void setBusID(final String busID) {
        this.busID = busID;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    public double getFuelLevel() {
        return this.fuelLevel;
    }

    public void setFuelLevel(final double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public String getFuelType() {
        return this.fuelType;
    }

    public void setFuelType(final String fuelType) {
        this.fuelType = fuelType;
    }
}

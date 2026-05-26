package sef.assignment4;

public class Main {
    static void main() {
        System.out.println("Hello World!");
        DriverRepository driverRepository = new DriverRepository("drivers.txt");
        driverRepository.addDriver("123456789", "John Doe", 3, "A", "123 Main St", "1990-01-01");
        driverRepository.addDriver("987654321", "Jane Smith", 5, "B", "456 Elm St", "1985-05-15");
        driverRepository.saveData();

        BusRepository busRepository = new BusRepository("buses.txt");
        busRepository.addBus("123456789", 100, 10.0, "Diesel");
        busRepository.addBus("987654321", 200, 20.0, "Petrol");
        busRepository.saveData();
    }
}

package sef.assignment4;

public class Main {
    static void main() {
        DriverRepository driverRepository = new DriverRepository("drivers.txt");
        driverRepository.loadData();
        System.out.println(driverRepository.retrieveDriver("123456789").getName());
//        driverRepository.addDriver("123456789", "John Doe", 3, "A", "123 Main St", "1990-01-01");
//        driverRepository.addDriver("987654321", "Jane Smith", 5, "B", "456 Elm St", "1985-05-15");
//        driverRepository.saveData();
//
        BusRepository busRepository = new BusRepository("buses.txt");
        busRepository.loadData();
        System.out.println(busRepository.retrieveBus("123456789").getBusID());
//        busRepository.addBus("123456789", 100, 10.0, "Diesel", "123456789");
//        busRepository.addBus("987654321", 200, 20.0, "Petrol", "987654321");
//        busRepository.saveData();

        System.out.println("Hello World!");
    }
}

package sef.assignment4;

import java.io.File;
import java.io.IOException;

public class Main {
    static void main() {
        try {
            File busFile = new File("buses.txt");
            File driverFile = new File("drivers.txt");

            busFile.delete();
            driverFile.delete();
            busFile.createNewFile();
            driverFile.createNewFile();

            DriverRepository driverRepository = new DriverRepository(driverFile.getName());
            driverRepository.loadData();
            driverRepository.addDriver("67abc!@dXY", "John", 13, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
            driverRepository.addDriver("78abc!@dXY", "Jane", 5, "PublicTransport", "124|La Trobe St|Melbourne|VIC|Australia", "15-05-1985");
            driverRepository.saveData();
            System.out.println(driverRepository.retrieveDriver("78abc!@dXY").getName());

            BusRepository busRepository = new BusRepository(busFile.getName(), driverFile.getName());
            busRepository.loadData();
            busRepository.addBus("12345678", 100, 10.0, "Diesel", "67abc!@dXY");
            busRepository.addBus("98765432", 200, 20.0, "Diesel", "78abc!@dXY");
            busRepository.saveData();
            System.out.println(busRepository.retrieveBus("12345678").getBusID());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Hello World!");
    }
}

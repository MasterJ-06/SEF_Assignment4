package sef.assignment4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BusIntegrationTest {

    private BusRepository repository;
    private DriverRepository driverRepository;

    @BeforeEach
    void setup() throws IOException {

        File busFile = new File("buses_test.txt");
        File driverFile = new File("drivers_test.txt");

        busFile.delete();
        driverFile.delete();
        busFile.createNewFile();
        driverFile.createNewFile();

        driverRepository = new DriverRepository(driverFile.getName());

        driverRepository.addDriver(
                "57abc!@dAZ",
                "John",
                10,
                "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia",
                "12-05-1997"
        );
        driverRepository.addDriver(
                "67abc!@dXY",
                "Jane",
                10,
                "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia",
                "12-05-1997"
        );
        driverRepository.saveData();

        repository = new BusRepository(busFile.getName(), driverFile.getName());
    }

    @Test
    void testAddValidBus() {

        repository.addBus(
                "12345678",
                50,
                80.0,
                "Diesel",
                "57abc!@dAZ"
        );

        assertEquals(1, repository.countBuses());
    }

    @Test
    void testRetrieveBus() {

        repository.addBus(
                "12345678",
                50,
                80.0,
                "Diesel",
                "57abc!@dAZ"
        );

        Bus bus =
                repository.retrieveBus("12345678");

        assertNotNull(bus);

        assertEquals("12345678", bus.getBusID());
    }

    @Test
    void testBusCount() {

        repository.addBus(
                "12345678",
                50,
                80.0,
                "Diesel",
                "57abc!@dAZ"
        );

        repository.addBus(
                "87654321",
                40,
                60.0,
                "Hybrid",
                "67abc!@dXY"
        );

        assertEquals(2, repository.countBuses());
    }
    @Test

    void testSaveAndLoadBusData() {

        repository.addBus(
                "12345678",
                50,
                80.0,
                "Diesel",
                "57abc!@dAZ"
        );

        repository.saveData();

        BusRepository loadedRepository =
                new BusRepository(repository.getFilename(), driverRepository.getFilename());

        loadedRepository.loadData();

        assertEquals(1, loadedRepository.countBuses());

        Bus loadedBus =
                loadedRepository.retrieveBus("12345678");

        assertEquals("Diesel", loadedBus.getFuelType());
    }
}
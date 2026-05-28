package sef.assignment4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class BusIntegrationTest {

    private BusRepository repository;

    @BeforeEach
    void setup() {

        File file = new File("buses.txt");

        if (file.exists()) {
            file.delete();
        }

        repository = new BusRepository("buses.txt");
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
                new BusRepository("buses.txt");

        loadedRepository.loadData();

        assertEquals(1, loadedRepository.countBuses());

        Bus loadedBus =
                loadedRepository.retrieveBus("12345678");

        assertEquals("Diesel", loadedBus.getFuelType());
    }
}
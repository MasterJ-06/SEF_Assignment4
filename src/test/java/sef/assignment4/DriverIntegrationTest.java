package sef.assignment4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class DriverIntegrationTest {

    private DriverRepository repository;

    @BeforeEach
    void setup() {
        File file = new File("drivers_test.txt");

        if (file.exists()) {
            file.delete();
        }

        repository = new DriverRepository(file.getName());
    }
    @Test
    void testAddValidDriver() {

        repository.addDriver(
                "57abc!@dAZ",
                "Aayan",
                5,
                "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia",
                "13-05-1999"
        );
        Driver driver = repository.retrieveDriver("57abc!@dAZ"); //Store driver locally

        repository.saveData(); // clear repsotory cache and relaod
        repository.clearCacheDrivers();
        repository.loadData();
        Driver newDriver = repository.retrieveDriver("57abc!@dAZ");
        
        assertEquals(true, newDriver.equals(driver));

    }
    @Test
    void testRetrieveDriver() {

        repository.addDriver(
                "57abc!@dAZ",
                "Aayan",
                5,
                "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia",
                "13-05-1999"
        );

        Driver driver =
                repository.retrieveDriver("57abc!@dAZ");

        assertNotNull(driver);
        
        assertEquals("Aayan", driver.getName());
    }
    @Test
    void testDriverCount() {

        repository.addDriver(
                "57abc!@dAZ",
                "Aayan",
                5,
                "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia",
                "13-05-1999"
        );

        repository.addDriver(
                "67abc!@dXY",
                "Saud",
                8,
                "PublicTransport",
                "200|George St|Sydney|NSW|Australia",
                "10-10-1997"
        );
        repository.saveData();
        repository.clearCacheDrivers();
        repository.loadData();  
        
        assertEquals(2, repository.countDrivers());
    }
    @Test
    void testSaveAndLoadDriverData() {

        repository.addDriver(
                "57abc!@dAZ",
                "Aayan",
                5,
                "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia",
                "13-05-1999"
        );

        repository.saveData();

        DriverRepository loadedRepository =
                new DriverRepository(repository.getFilename());

        loadedRepository.loadData();

        assertEquals(1, loadedRepository.countDrivers());

        Driver loadedDriver =
                loadedRepository.retrieveDriver("57abc!@dAZ");

        assertEquals("Aayan", loadedDriver.getName());
    }
}
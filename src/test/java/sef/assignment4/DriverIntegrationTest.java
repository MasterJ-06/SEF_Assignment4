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
    void testAddValidDriverStore() {

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

        assertTrue(newDriver.equals(driver));

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
    void testDriverPersistence() {

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

        assertTrue(loadedRepository.equals(repository));


    }

    @Test
    void testAddInvlidDriverStoreRetrieve() {

        repository.addDriver(
                "57abc!@dAZ",
                "Aayan",
                5,
                "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia",
                "13-05-1999"
        );

        repository.clearCacheDrivers();
        repository.saveData(); // clear repsotory cache and relaod
        repository.loadData();

        assertEquals(0, repository.countDrivers()); // expect no saved drivers
        assertEquals(null, repository.retrieveDriver("invlidDriverStringLengthExpectingFail")); // expect no result when
    }
}
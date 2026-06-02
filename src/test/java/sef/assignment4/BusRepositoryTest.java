package sef.assignment4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Drivers needed:
 *   57abc!@dAZ – Heavy, 6 yrs exp, DOB 01-01-1990  (general-purpose)
 *   92abc!@dXY – Heavy, 6 yrs exp, DOB = today-49y  (age boundary: 49)
 *   82abc!@dYZ – Heavy, 6 yrs exp, DOB = today-50y  (age boundary: 50)
 *   32abc!@dXZ – Heavy, 6 yrs exp, DOB = today-51y  (age boundary: 51)
 *   23abc!@dXZ – Heavy, 4 yrs exp, DOB 01-01-1990   (exp boundary: 4)
 *   33abc!@dXZ – Heavy, 5 yrs exp, DOB 01-01-1990   (exp boundary: 5)
 *   22abc!@dXZ – Heavy, 6 yrs exp, DOB 01-01-1990   (exp boundary: 6)
 *   44abc!@dXZ – Light, 6 yrs exp, DOB 01-01-1990   (license: Light)
 *   55abc!@dXZ – Medium, 6 yrs exp, DOB 01-01-1990  (license: Medium)
 *   66abc!@dXZ – Heavy, 6 yrs exp, DOB 01-01-1990   (license: Heavy)
 *   77abc!@dXZ – Public Transport, 6 yrs exp, DOB 01-01-1990
 */
public class BusRepositoryTest {

    private BusRepository busRepository;
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

        // General-purpose driver
        driverRepository.addDriver("57abc!@dAZ", "John Smith", 6, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");

        // Age boundary drivers
        String dob49 = LocalDate.now().minusYears(49).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String dob50 = LocalDate.now().minusYears(50).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String dob51 = LocalDate.now().minusYears(51).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        driverRepository.addDriver("92abc!@dXY", "John Smith", 6, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", dob49);
        driverRepository.addDriver("82abc!@dYZ", "John Smith", 6, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", dob50);
        driverRepository.addDriver("32abc!@dXZ", "John Smith", 6, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", dob51);

        // Experience boundary drivers
        driverRepository.addDriver("23abc!@dXZ", "John Smith", 4, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        driverRepository.addDriver("33abc!@dXZ", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        driverRepository.addDriver("22abc!@dXZ", "John Smith", 6, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");

        // License type drivers
        driverRepository.addDriver("44abc!@dXZ", "John Smith", 6, "Light",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        driverRepository.addDriver("55abc!@dXZ", "John Smith", 6, "Medium",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        driverRepository.addDriver("66abc!@dXZ", "John Smith", 6, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        driverRepository.addDriver("77abc!@dXZ", "John Smith", 6, "Public Transport",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");

        driverRepository.saveData();

        busRepository = new BusRepository(busFile.getName(), driverFile.getName());
    }

    //  Bus ID

    @Test
    void validBusID_isAccepted() {
        assertDoesNotThrow(() ->
            busRepository.addBus("12345678", 30, 50.0, "Diesel", "57abc!@dAZ")
        );
        assertEquals(1, busRepository.countBuses());
    }

    @Test
    void busID_7chars_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("1234567", 30, 50.0, "Diesel", "57abc!@dAZ")
        );
    }

    @Test
    void busID_9chars_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("123456789", 30, 50.0, "Diesel", "57abc!@dAZ")
        );
    }

    @Test
    void duplicateBusID_isRejected() {
        busRepository.addBus("12345678", 30, 50.0, "Diesel", "57abc!@dAZ");
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("12345678", 30, 50.0, "Diesel", "57abc!@dAZ")
        );
    }

    @Test
    void busID_containsLetter_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("1234567A", 30, 50.0, "Diesel", "57abc!@dAZ")
        );
    }

    //  Bus Capacity

    @Test
    void increaseBusCapacity_byOne_isRejected() {
        busRepository.addBus("12345678", 30, 50.0, "Diesel", "57abc!@dAZ");
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.updateBus("12345678", 1, 50.0, "57abc!@dAZ")
        );
    }

    @Test
    void increaseBusCapacity_byZero_isAccepted() {
        busRepository.addBus("12345678", 30, 50.0, "Diesel", "57abc!@dAZ");
        busRepository.updateBus("12345678", 0, 50.0, "57abc!@dAZ");
        assertEquals(30, busRepository.retrieveBus("12345678").getCapacity());
    }

    @Test
    void decreaseBusCapacity_byOne_isAccepted() {
        busRepository.addBus("12345678", 30, 50.0, "Diesel", "57abc!@dAZ");
        busRepository.updateBus("12345678", -1, 50.0, "57abc!@dAZ");
        assertEquals(29, busRepository.retrieveBus("12345678").getCapacity());
    }

    //  Driver Age vs. Bus Capacity

    @Test
    void driver49YearsOld_capacity50_isAccepted() {
        assertDoesNotThrow(() ->
            busRepository.addBus("12345678", 50, 50.0, "Diesel", "92abc!@dXY")
        );
    }

    @Test
    void driver50YearsOld_capacity50_isAccepted() {
        assertDoesNotThrow(() ->
            busRepository.addBus("12345678", 50, 50.0, "Diesel", "82abc!@dYZ")
        );
    }

    @Test
    void driver51YearsOld_capacity50_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("12345678", 50, 50.0, "Diesel", "32abc!@dXZ")
        );
    }

    //  Electric Bus – Driver Experience Requirement

    @Test
    void driver4YearsExp_electricBus_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("12345678", 30, 50.0, "Electricity", "23abc!@dXZ")
        );
    }

    @Test
    void driver5YearsExp_electricBus_isAccepted() {
        assertDoesNotThrow(() ->
            busRepository.addBus("12345678", 30, 50.0, "Electricity", "33abc!@dXZ")
        );
    }

    @Test
    void driver6YearsExp_electricBus_isAccepted() {
        assertDoesNotThrow(() ->
            busRepository.addBus("12345678", 30, 50.0, "Electricity", "22abc!@dXZ")
        );
    }

    //  Electric Bus – Driver License Requirement

    @Test
    void lightLicense_electricBus_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("12345678", 30, 50.0, "Electricity", "44abc!@dXZ")
        );
    }

    @Test
    void mediumLicense_electricBus_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("12345678", 30, 50.0, "Electricity", "55abc!@dXZ")
        );
    }

    @Test
    void heavyLicense_electricBus_isAccepted() {
        assertDoesNotThrow(() ->
            busRepository.addBus("12345678", 30, 50.0, "Electricity", "66abc!@dXZ")
        );
    }

    @Test
    void publicTransportLicense_electricBus_isAccepted() {
        assertDoesNotThrow(() ->
            busRepository.addBus("12345678", 30, 50.0, "Electricity", "77abc!@dXZ")
        );
    }

    //  Hybrid Bus – Driver License Requirement

    @Test
    void lightLicense_hybridBus_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("12345678", 30, 50.0, "Hybrid", "44abc!@dXZ")
        );
    }

    @Test
    void mediumLicense_hybridBus_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            busRepository.addBus("12345678", 30, 50.0, "Hybrid", "55abc!@dXZ")
        );
    }

    @Test
    void heavyLicense_hybridBus_isAccepted() {
        assertDoesNotThrow(() ->
            busRepository.addBus("12345678", 30, 50.0, "Hybrid", "66abc!@dXZ")
        );
    }

    @Test
    void publicTransportLicense_hybridBus_isAccepted() {
        assertDoesNotThrow(() ->
            busRepository.addBus("12345678", 30, 50.0, "Hybrid", "77abc!@dXZ")
        );
    }
}

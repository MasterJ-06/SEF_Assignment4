package sef.assignment4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class DriverRepositoryTest {

    private DriverRepository driverRepository;

    @BeforeEach
    void setup() throws IOException {
        File driverFile = new File("drivers_test.txt");
        driverFile.delete();
        driverFile.createNewFile();

        driverRepository = new DriverRepository(driverFile.getName());
        // Add one valid driver for tests that need an existing driver
        driverRepository.addDriver(
                "57abc!@dAZ",
                "John Smith",
                10,
                "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia",
                "13-05-1999"
        );
        driverRepository.saveData();
    }

    //  Driver ID – Format Validation

    @Test
    void validDriverID_isAccepted() {
        assertDoesNotThrow(() ->
            driverRepository.addDriver("33abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void duplicateDriverID_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("57abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_9chars_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("57ab!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_11chars_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("57abc!@deAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_firstTwoCharsAreLetters_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("XYabc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_firstCharIsOneNotBetween2And9_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("17abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_secondCharIsZeroNotBetween2And9_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("50abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_noSpecialChars_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("57abcdefAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_onlyOneSpecialCharAtPosition3_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("57!bcdefAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_onlyOneSpecialCharAtPosition8_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("57abcde!AZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_ninthCharIsLowercase_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("57abc!@daZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void driverID_tenthCharIsLowercase_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("57abc!@dAz", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    //  Address Validation

    @Test
    void validAddress_isAccepted() {
        assertDoesNotThrow(() ->
            driverRepository.addDriver("33abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990")
        );
    }

    @Test
    void address_withoutPipeSeparators_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("33abc!@dAZ", "Jane Doe", 5, "Heavy", "124 La Trobe St Melbourne VIC Australia", "01-01-1990")
        );
    }

    @Test
    void address_withFourFields_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("33abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC", "01-01-1990")
        );
    }

    @Test
    void address_withSixFields_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("33abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia|Oceania", "01-01-1990")
        );
    }

    //  Birthdate Validation

    @Test
    void validBirthdate_isAccepted() {
        assertDoesNotThrow(() ->
            driverRepository.addDriver("33abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "13-05-1999")
        );
    }

    @Test
    void birthdate_withSlashSeparators_isRejected() {
        assertThrows(IllegalArgumentException.class, () ->
            driverRepository.addDriver("33abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "13/05/1999")
        );
    }

    @Test
    void birthdate_withInvalidDay_sourceCodeBug_actuallyAccepted() {
        assertDoesNotThrow(() ->
            driverRepository.addDriver("33abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "32-05-1999")
        );
    }

    @Test
    void birthdate_withInvalidMonth_sourceCodeBug_actuallyAccepted() {
        assertDoesNotThrow(() ->
            driverRepository.addDriver("33abc!@dAZ", "Jane Doe", 5, "Heavy", "124|La Trobe St|Melbourne|VIC|Australia", "13-13-1999")
        );
    }

    //  Update – License Type

    @Test
    void updateLicense_with9YearsExperience_isAccepted() {
        driverRepository.updateDriver("57abc!@dAZ", null, null, 9, "Medium", null, null);
        assertEquals("Medium", driverRepository.retrieveDriver("57abc!@dAZ").getLicenseType());
    }

    @Test
    void updateLicense_with10YearsExperience_isAccepted() {
        driverRepository.updateDriver("57abc!@dAZ", null, null, 10, "Medium", null, null);
        assertEquals("Medium", driverRepository.retrieveDriver("57abc!@dAZ").getLicenseType());
    }

    @Test
    void updateLicense_with11YearsExperience_isRejected() {
        driverRepository.updateDriver("57abc!@dAZ", null, null, 11, "Light", null, null);
        assertEquals("Heavy", driverRepository.retrieveDriver("57abc!@dAZ").getLicenseType());
    }

    //  Update – Fields that should be rejected / accepted

    @Test
    void updateDriverID_isRejected() {
        driverRepository.updateDriver("57abc!@dAZ", "34abc!@dAZ", null, -1, null, null, null);
        assertNotNull(driverRepository.retrieveDriver("57abc!@dAZ"));
        assertNull(driverRepository.retrieveDriver("34abc!@dAZ"));
    }

    @Test
    void updateName_isRejected() {
        driverRepository.updateDriver("57abc!@dAZ", null, "Harry", -1, null, null, null);
        assertEquals("John Smith", driverRepository.retrieveDriver("57abc!@dAZ").getName());
    }

    @Test
    void updateAddress_isAccepted() {
        driverRepository.updateDriver("57abc!@dAZ", null, null, -1, null, "122|La Trobe St|Melbourne|VIC|Australia", null);
        assertEquals("122|La Trobe St|Melbourne|VIC|Australia", driverRepository.retrieveDriver("57abc!@dAZ").getAddress());
    }
}

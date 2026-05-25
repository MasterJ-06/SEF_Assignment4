package sef.assignment4;

public class Driver {
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType;
    private String address;
    private String birthdate;

    public Driver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {
        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    public String getDriverID() {
        return this.driverID;
    }

    public void setDriverID(final String driverID) {
        this.driverID = driverID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getExperienceYears() {
        return this.experienceYears;
    }

    public void setExperienceYears(final int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getLicenseType() {
        return this.licenseType;
    }

    public void setLicenseType(final String licenseType) {
        this.licenseType = licenseType;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(final String birthdate) {
        this.birthdate = birthdate;
    }
}

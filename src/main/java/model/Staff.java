package model;

import java.util.Random;

public class Staff {

    private Integer id;
    private String firstName;
    private String lastName;
    private String personalAddress;
    private String jobAddress;
    private String role;
    private int supervisorId;
    private int badgeNum;
    private static int count = 0;

    public Staff(String firstName, String lastName, String personalAddress, String jobAddress, String role, int supervisor) {
        this.id = count++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalAddress = personalAddress;
        this.jobAddress = jobAddress;
        this.role = role;
        this.supervisorId = supervisor;
        Random random = new Random();
        this.badgeNum = random.nextInt(100000000)+100000000;
    }

    public Staff() {
    }

    @Override
    public String toString() {
        return id +
                "," + firstName +
                "," + lastName +
                "," + personalAddress +
                "," + jobAddress +
                "," + role +
                "," + supervisorId +
                "," + badgeNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalAddress() {
        return personalAddress;
    }

    public void setPersonalAddress(String personalAddress) {
        this.personalAddress = personalAddress;
    }

    public String getJobAddress() {
        return jobAddress;
    }

    public void setJobAddress(String jobAddress) {
        this.jobAddress = jobAddress;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public int getBadgeNum() {
        return badgeNum;
    }

    public void setBadgeNum(int badgeNum) {
        this.badgeNum = badgeNum;
    }
}

package models;

import java.sql.Timestamp;
public class User {
    private String clientId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String dateOfBirth;
    private String passport;
    private String passportValidTo;
    private String phone;

    public User(String clientId, String lastName, String firstName, String patronymic, String dateOfBirth,
                String passport, String passportValidTo, String phone) {
        this.clientId = clientId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.passport = passport;
        this.passportValidTo = passportValidTo;
        this.phone = phone;
    }

    public String getClientId() {
        return clientId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassport() {
        return passport;
    }

    public String getPassportValidTo() {
        return passportValidTo;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setPassportValidTo(String passportValidTo) {
        this.passportValidTo = passportValidTo;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

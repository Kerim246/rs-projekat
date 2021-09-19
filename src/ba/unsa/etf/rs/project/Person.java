package ba.unsa.etf.rs.project;

import javafx.beans.property.SimpleIntegerProperty;
import org.assertj.core.internal.bytebuddy.implementation.bind.MethodDelegationBinder;

public class Person {
    private int id,postalNumber;
    private String name,surname;
    public long phone_number;
    private int birthYear;


    public Person(){

    }

    public Person(int id, String name, String surname,int postalNumber,long phone_number) {
        this.id = id;
        this.postalNumber = postalNumber;
        this.name = name;
        this.surname = surname;
        this.phone_number = phone_number;
        this.birthYear = 2000;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(int postalNumber) {
        this.postalNumber = postalNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}

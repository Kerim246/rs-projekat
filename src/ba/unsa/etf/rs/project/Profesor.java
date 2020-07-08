package ba.unsa.etf.rs.project;

import java.time.LocalDate;

public class Profesor {
    private int id;
    private String name,surname;
    private LocalDate employment_date;

    public Profesor() {
    }

    public Profesor(int id, String name, String surname, LocalDate employment_date) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.employment_date = employment_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String ime) {
        this.name = ime;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getEmployment_date() {
        return employment_date;
    }

    public void setEmployment_date(LocalDate employee_date) {
        this.employment_date = employee_date;
    }
}

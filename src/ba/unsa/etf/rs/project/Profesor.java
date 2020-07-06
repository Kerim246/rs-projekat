package ba.unsa.etf.rs.project;

import java.time.LocalDate;

public class Profesor {
    private int id;
    private String name,surname;
    private LocalDate employee_date;
    private int subject;

    public Profesor() {
    }

    public Profesor(int id, String name, String surname, LocalDate employee_date,int subject) {
        this.id = id;
        this.subject = subject;
        this.name = name;
        this.surname = surname;
        this.employee_date = employee_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int predmet) {
        this.subject = predmet;
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

    public LocalDate getDatum_zaposljavanja() {
        return employee_date;
    }

    public void setDatum_zaposljavanja(LocalDate employee_date) {
        this.employee_date = employee_date;
    }
}

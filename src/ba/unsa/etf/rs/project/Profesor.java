package ba.unsa.etf.rs.project;

import java.time.LocalDate;

public class Profesor extends Person{

    private LocalDate employment_date;
    private Subject subject;

    public Profesor() {
    }

    public Profesor(int id, String name, String surname, LocalDate employment_date,int postalNumber,long phone_number) {
        super(id, surname, name, postalNumber,phone_number);
        this.employment_date = employment_date;
    }

    public LocalDate getEmployment_date() {
        return employment_date;
    }

    public void setEmployment_date(LocalDate employment_date) {
        this.employment_date = employment_date;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}

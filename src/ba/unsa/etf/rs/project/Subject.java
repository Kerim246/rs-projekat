package ba.unsa.etf.rs.project;

public class Subject {
    private int id,ects,semestar,obligatory;
    private String name;
    private Profesor profesor;

    public Subject() {
    }

    public Subject(int id, String name, int ects, int semestar, int obligatory, Profesor profesor) {
        this.id = id;
        this.ects = ects;
        this.semestar = semestar;
        this.obligatory = obligatory;
        this.profesor = profesor;
        this.name = name;
    }

    public Subject(int id, String name, int ects, int semestar, int obligatory) {
        this.id = id;
        this.ects = ects;
        this.semestar = semestar;
        this.obligatory = obligatory;
        this.profesor = profesor;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public int getSemestar() {
        return semestar;
    }

    public void setSemestar(int semestar) {
        this.semestar = semestar;
    }

    public int getObligatory() {
        return obligatory;
    }

    public void setObligatory(int obavezan) {
        this.obligatory = obavezan;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getName() {
        return name;
    }

    public void setName(String naziv) {
        this.name = naziv;
    }

    @Override
    public String toString() {
        return name;
    }
}

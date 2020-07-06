package ba.unsa.etf.rs.project;

import java.time.LocalDate;

public class Material {
    private int id;
    private String name,content;
    private LocalDate publication_date;
    private Subject subject;
    private Type type;

    public Material() {
    }

    public Material(int id, String name, Type type, String content, LocalDate publication_date, Subject subject) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.content = content;
        this.publication_date = publication_date;
        this.subject = subject;
    }

    public Material(String name, Type type, String content, LocalDate publication_date, Subject subject) {
        this.name = name;
        this.type = type;
        this.content = content;
        this.publication_date = publication_date;
        this.subject = subject;
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

    public void setName(String naziv) {
        this.name = naziv;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(LocalDate datum_objave) {
        this.publication_date = datum_objave;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}

package ba.unsa.etf.rs.project;

public class Account {
    private int id;
    private String username,password;
    private Profesor profesor;

    public Account() {
    }

    public Account(int id, String username, String password, Profesor profesor) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profesor = profesor;
    }

    public Account(String username, String password, Profesor profesor) {
        this.username = username;
        this.password = password;
        this.profesor = profesor;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}

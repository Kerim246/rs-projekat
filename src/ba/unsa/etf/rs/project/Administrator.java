package ba.unsa.etf.rs.project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Administrator extends Person {
    private int id;
    private String username,password;

    public Administrator() {}

    public Administrator(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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

}

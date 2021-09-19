package ba.unsa.etf.rs.project;

import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class SubjectDAO {
    private PreparedStatement addProfessorStatement,updateProfessorStatement,deleteProfessorStatement,getMaxIdProfessor,addAccountStatement,updateAccountStatement,deleteAccountStatement,getMaxAccountId,getAccount,addPersonStatement,getMaxIdPerson;
    private PreparedStatement getProfessorsUpit,getAllAccounts,getProfessorMaterial,getProfesorUpit,nadjiPredmetUpit,getProfessorPredmetUpit,getAllPredmet,nadjiPredmetId,addMaterialUpit,getIdMaterial,deleteProfessorSubject,findSubjectStatement,updateSubjectProfessor;
    private PreparedStatement findTypeStatement,getAllTypesStatement,findTypeStatementName,updateMaterialStatement,deleteMaterialStatement,getAllMaterialStatement,getAllAdminsStatement,getAllSubjectProfessorStatement,getSubjectFromProfessor,getProfessorSubject,getSubjectFromProfessor1;
    private PreparedStatement addAdminStatement,EditAdminStatement,RemoveAdminStatement,deletePersonStatement,updatePersonStatement;
    private SimpleObjectProperty<Material> currentMaterial = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Profesor> currentProfessor = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Administrator> currentAdmin = new SimpleObjectProperty<>();

    private static SubjectDAO instance;
    private static Connection connection;


    public static SubjectDAO getInstance() {
        if (instance == null) instance = new SubjectDAO();
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    public static void deleteInstance() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        instance = null;
    }

    public SubjectDAO(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:material.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            addAdminStatement = connection.prepareStatement("INSERT INTO Administrator VALUES(?,?,?)");
            EditAdminStatement = connection.prepareStatement("UPDATE Administrator SET username=?,password=? WHERE id=?");
            RemoveAdminStatement= connection.prepareStatement("DELETE FROM Administrator WHERE id=?");
            deletePersonStatement = connection.prepareStatement("DELETE FROM Person where id=?");

            getProfessorsUpit = connection.prepareStatement("SELECT p.id,p.name,p.surname,pr.employment_date,p.postal_number,p.phone_number,p.birth_year from Professor pr, Person p where p.id = pr.id");
            getAllAccounts = connection.prepareStatement("SELECT a.id,a.username,a.password,a.professor from account a,professor p where a.professor = p.id");
            getProfessorMaterial = connection.prepareStatement("SELECT m.id,m.name,m.type,m.publication_date,m.subject_id,m.content,pr.id from material m,professor pr,subject p,type t where m.subject_id = p.id AND p.professor = pr.id AND t.id = m.type");
            nadjiPredmetUpit = connection.prepareStatement("SELECT * from subject where name=?");
            getAllPredmet = connection.prepareStatement("SELECT * from subject");
            getProfesorUpit = connection.prepareStatement("SELECT p.id,p.name,p.surname,pr.employment_date,p.postal_number,p.phone_number from Professor pr,Person p where p.id = pr.id AND pr.id=?");
            nadjiPredmetId = connection.prepareStatement("SELECT * from subject where id=?");
            addMaterialUpit = connection.prepareStatement("INSERT INTO material VALUES(?,?,?,?,?,?)");
            getIdMaterial = connection.prepareStatement("SELECT Max(id)+1 FROM material");
            getSubjectFromProfessor = connection.prepareStatement("SELECT s.name FROM Subject s, Professor p WHERE s.professor=?");
            getSubjectFromProfessor1 = connection.prepareStatement("SELECT * FROM Subject s, Professor p WHERE s.professor=?");
            updatePersonStatement = connection.prepareStatement("UPDATE Person SET name=?,surname=?,postal_number=?,phone_number=?,birth_year=? WHERE ID=?");

            addPersonStatement = connection.prepareStatement("INSERT INTO Person VALUES(?,?,?,?,?,?)");
            getMaxIdPerson = connection.prepareStatement("SELECT Max(id)+1 FROM Person");
            deleteProfessorSubject = connection.prepareStatement("UPDATE Subject SET professor=NULL WHERE professor=?");
            getProfessorSubject = connection.prepareStatement("SELECT s.id FROM Subject s,Professor p where s.professor=?");
            findSubjectStatement = connection.prepareStatement("SELECT * from Subject s WHERE s.name=?");
            updateSubjectProfessor = connection.prepareStatement("UPDATE Subject SET professor=? WHERE id=?");

            findTypeStatement = connection.prepareStatement("SELECT * from type where id=?");
            getAllTypesStatement = connection.prepareStatement("SELECT * from type");
            findTypeStatementName = connection.prepareStatement("SELECT * from type where name=?");
            updateMaterialStatement = connection.prepareStatement("UPDATE Material SET name=?,type=?,publication_date=?,subject_id=?,content=? WHERE id=?");
            deleteMaterialStatement = connection.prepareStatement("DELETE FROM Material where id=?");
            getAllMaterialStatement = connection.prepareStatement("SELECT * from Material");

            getAllAdminsStatement = connection.prepareStatement("SELECT a.id,p.name,p.surname,p.postal_number,p.phone_number,a.username,a.password,p.birth_year from Administrator a, Person p where p.id = a.id");
            getAllSubjectProfessorStatement = connection.prepareStatement("SELECT * from subject where professor=?");
            addProfessorStatement = connection.prepareStatement("INSERT INTO Professor VALUES(?,?)");
            deleteProfessorStatement = connection.prepareStatement("DELETE FROM Professor where id=?");
            updateProfessorStatement = connection.prepareStatement("UPDATE Professor SET employment_date=? where id=?");
            getMaxIdProfessor = connection.prepareStatement("SELECT Max(id)+1 from Professor");
            addAccountStatement = connection.prepareStatement("INSERT INTO Account VALUES(?,?,?,?)");
            updateAccountStatement = connection.prepareStatement("UPDATE Account SET username=?,password=?,professor=? where id=?");
            deleteAccountStatement = connection.prepareStatement("DELETE FROM Account where id=?");
            getMaxAccountId = connection.prepareStatement("SELECT Max(id)+1 from Account");

            getAccount = connection.prepareStatement("SELECT * from account where professor=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int AddingPerson(Person p) throws SQLException {
        int id=1;

        ResultSet rs = getMaxIdPerson.executeQuery();

        if(rs.next()){
            id = rs.getInt(1);
        }

        addPersonStatement.setInt(1,id);
        addPersonStatement.setString(2,p.getName());
        addPersonStatement.setString(3,p.getSurname());
        addPersonStatement.setInt(4,p.getPostalNumber());
        addPersonStatement.setLong(5,p.getPhone_number());
        addPersonStatement.setInt(6,p.getBirthYear());

        addPersonStatement.executeUpdate();

        return id;
    }

    public void addAdmin(Administrator a) throws SQLException {
        int id= AddingPerson(a);
        addAdminStatement.setInt(1,id);
        addAdminStatement.setString(2,a.getUsername());
        addAdminStatement.setString(3,a.getPassword());

        addAdminStatement.executeUpdate();

    }

    public void editAdmin(Administrator a) throws SQLException {
        EditAdminStatement.setString(1,a.getUsername());
        EditAdminStatement.setString(2,a.getPassword());
        EditAdminStatement.setInt(3,a.getId());

        EditAdminStatement.executeUpdate();

        currentAdmin.setValue(a);

        updatePersonStatement.setString(1,a.getName());
        updatePersonStatement.setString(2,a.getSurname());
        updatePersonStatement.setInt(3,a.getPostalNumber());
        updatePersonStatement.setLong(4,a.getPhone_number());
        updatePersonStatement.setLong(5,a.getBirthYear());

        updatePersonStatement.setInt(6,a.getId());

        updatePersonStatement.executeUpdate();

    }

    public void removeAdmin(Administrator a) throws SQLException {
        RemoveAdminStatement.setInt(1,a.getId());
        RemoveAdminStatement.executeUpdate();
        deletePersonStatement.setInt(1,a.getId());
        deletePersonStatement.executeUpdate();
    }

    public Account getAccount(int id_prof) throws SQLException {
         getAccount.setInt(1,id_prof);
         ResultSet rs = getAccount.executeQuery();
        Account account = new Account();
        if(rs.next()){
            account = new Account(rs.getInt(1),rs.getString(2),rs.getString(3),findProfessor(rs.getInt(4)));
        }
        return account;
    }

    public ObservableList<Administrator> getAdmins() throws SQLException {
        ResultSet rs = getAllAdminsStatement.executeQuery();
        ObservableList<Administrator> admins = FXCollections.observableArrayList();
        while(rs.next()){
            Administrator a = new Administrator();
            a.setId(rs.getInt(1));
            a.setName(rs.getString(2));
            a.setSurname(rs.getString(3));
            a.setPostalNumber(rs.getInt(4));
            a.setPhone_number(rs.getLong(5));
            a.setUsername(rs.getString(6));
            a.setPassword(rs.getString(7));
            a.setBirthYear(rs.getInt(8));
            admins.add(a);
        }

        return admins;
    }

    public ObservableList<Subject> getAllSubjects() throws SQLException {
        ResultSet rs = getAllPredmet.executeQuery();
        ObservableList<Subject> subjects = FXCollections.observableArrayList();

        while(rs.next()){
            subjects.add(new Subject(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),findProfessor(rs.getInt(6))));
        }

        return subjects;
    }

    public String getSubjectFProfessor(Profesor p) throws SQLException {
        getSubjectFromProfessor.setInt(1,p.getId());
        ResultSet rs = getSubjectFromProfessor.executeQuery();

        String subjectName ="";

        if(rs.next()){
            subjectName = rs.getString(1);
        }

        return subjectName;
    }

    public Subject findSubjectName(String name) throws SQLException {
        findSubjectStatement.setString(1,name);
        ResultSet rs = findSubjectStatement.executeQuery();
        Subject s = new Subject();
        if(rs.next()){
           s = new Subject(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));
        }

        return s;
    }

    public ObservableList<Profesor> getAllProfessors() throws SQLException {
        ResultSet rs = getProfessorsUpit.executeQuery();
        ObservableList<Profesor> professors = FXCollections.observableArrayList();
        Profesor p = new Profesor();

        while(rs.next()){
            p = new Profesor();
            p.setId(rs.getInt(1));
            p.setName(rs.getString(2));
            p.setSurname(rs.getString(3));
            p.setEmployment_date(LocalDate.parse(rs.getString(4)));
            p.setPostalNumber(rs.getInt(5));
            p.setPhone_number(rs.getLong(6));
            p.setBirthYear(rs.getInt(7));
            p.setSubject(getEntireSubject(p));
          //  professors.add(new Profesor(rs.getInt(1),rs.getString(2),rs.getString(3),LocalDate.parse(rs.getString(4)),rs.getInt(5),rs.getLong(6)));
            professors.add(p);
        }

        return professors;
    }

    public Subject getEntireSubject(Profesor p) throws SQLException {
        getSubjectFromProfessor1.setInt(1,p.getId());
        ResultSet rs = getSubjectFromProfessor1.executeQuery();
        Subject s = new Subject();

        if(rs.next()){
            s = new Subject(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));
        }
        return s;
    }

    public ObservableList<Subject> getAllSubjectsProfessor(int id_prof) throws SQLException {
        getAllSubjectProfessorStatement.setInt(1,id_prof);
        ResultSet rs = getAllSubjectProfessorStatement.executeQuery();
        ObservableList<Subject> subjects = FXCollections.observableArrayList();

        while(rs.next()){
            subjects.add(new Subject(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),findProfessor(rs.getInt(6))));
        }

        return subjects;
    }

    public Profesor findProfessor(int id) throws SQLException {
        getProfesorUpit.setInt(1,id);

        Profesor profesor = new Profesor();
        ResultSet rs = getProfesorUpit.executeQuery();
        if(rs.next()){
            profesor = new Profesor(rs.getInt(1),rs.getString(2),rs.getString(3),LocalDate.parse(rs.getString(4)),rs.getInt(5),rs.getLong(6));
        }

        return profesor;
    }

    public ObservableList<Material> getAllMaterials() throws SQLException {
        ObservableList<Material> materials = FXCollections.observableArrayList();

        ResultSet rs = getAllMaterialStatement.executeQuery();

        while(rs.next()) {
            materials.add(new Material(rs.getInt(1), rs.getString(2), findType(rs.getInt(3)),  LocalDate.parse(rs.getString(4)), findSubjectId(rs.getInt(5)),rs.getString(6)));
        }
        return materials;
    }


    public ArrayList<Account> getAllAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();

        try {
            ResultSet rs = getAllAccounts.executeQuery();
            while(rs.next()){
                accounts.add(new Account(rs.getInt(1),rs.getString(2),rs.getString(3),findProfessor(rs.getInt(4))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public ObservableList<Material> getProfessorMaterial(int id_prof){
        ObservableList<Material> materijali = FXCollections.observableArrayList();

        try {
            ResultSet rs = getProfessorMaterial.executeQuery();

            while(rs.next()) {
                if (rs.getInt(7) == id_prof)                // Ako je to taj profesor koji se logirao
                    materijali.add(new Material(rs.getInt(1), rs.getString(2), findType(rs.getInt(3)),  LocalDate.parse(rs.getString(4)), findSubjectId(rs.getInt(5)),rs.getString(6)));
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materijali;
    }

    public Subject findSubject(String predmet) throws SQLException {
     //   System.out.println("\n"+predmet);
        nadjiPredmetUpit.setString(1,predmet);
        ResultSet rs = nadjiPredmetUpit.executeQuery();
        Profesor profesor = new Profesor();
        Subject p = new Subject();

        if(rs.next()){
            int id_prof = rs.getInt(6);
            getProfesorUpit.setInt(1,id_prof);
            ResultSet rs1 = getProfesorUpit.executeQuery();
            if(rs1.next()){
                profesor.setId(rs1.getInt(1));
                profesor.setName(rs1.getString(2));
                profesor.setSurname(rs1.getString(3));
                profesor.setEmployment_date(LocalDate.parse(rs1.getString(4)));
            }
            p.setId(rs.getInt(1));
            p.setName(rs.getString(2));
           // System.out.println(p.getName());
            p.setSemestar(rs.getInt(3));
            p.setEcts(rs.getInt(4));
            p.setObligatory(rs.getInt(5));
            p.setProfesor(profesor);
        }

        return p;

    }

    public Subject findSubjectId(int predmet) throws SQLException {
        nadjiPredmetId.setInt(1,predmet);
        ResultSet rs = nadjiPredmetId.executeQuery();
        Profesor profesor = new Profesor();
        Subject p = new Subject();

        if(rs.next()){
            int id_prof = rs.getInt(6);
            getProfesorUpit.setInt(1,id_prof);
            ResultSet rs1 = getProfesorUpit.executeQuery();
            if(rs1.next()){
                profesor.setId(rs1.getInt(1));
                profesor.setName(rs1.getString(2));
                profesor.setSurname(rs1.getString(3));
                profesor.setEmployment_date(LocalDate.parse(rs1.getString(4)));
            }
            p.setId(rs.getInt(1));
            p.setName(rs.getString(2));
            p.setSemestar(rs.getInt(3));
            p.setEcts(rs.getInt(4));
            p.setObligatory(rs.getInt(5));
            p.setProfesor(profesor);
        }

        return p;

    }

    public void addMaterial(Material material){
        try {
            ResultSet rs = getIdMaterial.executeQuery();

            int id =1;
            if(rs.next()){
                id = rs.getInt(1);
            }
//            System.out.println(id + " " + material.getName() + " " + material.getType().getId() + " "+ material.getContent()+ " "+ material.getPublication_date().toString() + material.getSubject().getId());
       //     System.out.println("\n"+material.getSubject().getId());
            addMaterialUpit.setInt(1,id);
            addMaterialUpit.setString(2, material.getName());
            addMaterialUpit.setInt(3, material.getType().getId());
            addMaterialUpit.setString(4, material.getPublication_date().toString());
            addMaterialUpit.setInt(5, material.getSubject().getId());
            addMaterialUpit.setString(6, material.getContent());

            addMaterialUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Type findType(int id) throws SQLException {
        findTypeStatement.setInt(1,id);
        ResultSet rs = findTypeStatement.executeQuery();
        Type type = new Type();
        if(rs.next()){
            type.setId(rs.getInt(1));
            type.setName(rs.getString(2));
        }

        return type;
    }

    public Type findType(String name) throws SQLException {
        findTypeStatementName.setString(1,name);
        ResultSet rs = findTypeStatementName.executeQuery();
        Type type = new Type();
        if(rs.next()){
            type.setId(rs.getInt(1));
            type.setName(rs.getString(2));
        }

        return type;
    }

    public ObservableList<Type> getAllTypes() throws SQLException {
        ResultSet rs = getAllTypesStatement.executeQuery();
        ObservableList<Type> types = FXCollections.observableArrayList();
        while (rs.next()){
            types.add(new Type(rs.getInt(1),rs.getString(2)));
        }
        return types;
    }

    public void updateMaterial(Material material){
        try {
            updateMaterialStatement.setString(1,material.getName());
            updateMaterialStatement.setInt(2,material.getType().getId());
            updateMaterialStatement.setString(3,material.getPublication_date().toString());
            updateMaterialStatement.setInt(4,material.getSubject().getId());
            updateMaterialStatement.setString(5,material.getContent());

            updateMaterialStatement.setInt(6,currentMaterial.get().getId());

            updateMaterialStatement.executeUpdate();

            currentMaterial.setValue(material);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Material getCurrentMaterial() {
        if(currentMaterial == null) return null;
        return currentMaterial.get();
    }

    public SimpleObjectProperty<Material> currentMaterialProperty() {
        return currentMaterial;
    }

    public void setCurrentMaterial(Material currentMaterial) {
        if(this.currentMaterial == null){
            this.currentMaterial = new SimpleObjectProperty<>(currentMaterial);
        }
        else
            this.currentMaterial.set(currentMaterial);
    }

    public void deleteMaterial(Material material){
        try {
            if(material != null) {
                deleteMaterialStatement.setInt(1, material.getId());
                deleteMaterialStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProfessor(Profesor profesor,Account account) throws SQLException {
        int id = AddingPerson(profesor);

        addProfessorStatement.setInt(1,id);
        addProfessorStatement.setString(2,profesor.getEmployment_date().toString());

        addProfessorStatement.executeUpdate();

        int id1=1;
       ResultSet rs1 = getMaxAccountId.executeQuery();

        if(rs1.next()){
            id1 = rs1.getInt(1);
        }

        addAccountStatement.setInt(1,id1);
        addAccountStatement.setString(2,account.getUsername());
        addAccountStatement.setString(3,account.getPassword());
        addAccountStatement.setInt(4,id);

        addAccountStatement.executeUpdate();

        updateSubjectProfessor.setInt(1,id);
        updateSubjectProfessor.setInt(2,profesor.getSubject().getId());

        updateSubjectProfessor.executeUpdate();

    }

    public void updateProfessor(Profesor profesor,Account account) throws SQLException {
        updateProfessorStatement.setString(1,profesor.getEmployment_date().toString());

        updateProfessorStatement.setInt(2,currentProfessor.get().getId());
        getAccount.setInt(1,profesor.getId());
        ResultSet rs = getAccount.executeQuery();

        int id=1;
        if(rs.next()){
            id = rs.getInt(1);
        }
        updateAccountStatement.setString(1,account.getUsername());
        updateAccountStatement.setString(2,account.getPassword());
        updateAccountStatement.setInt(3,profesor.getId());
        updateAccountStatement.setInt(4,id);

        updateProfessorStatement.executeUpdate();

        updateAccountStatement.executeUpdate();


        deleteProfessorSubject.setInt(1,profesor.getId());
        deleteProfessorSubject.executeUpdate();

        updateSubjectProfessor.setInt(1,profesor.getId());
        updateSubjectProfessor.setInt(2,profesor.getSubject().getId());

        updateSubjectProfessor.executeUpdate();

        updatePersonStatement.setString(1,profesor.getName());
        updatePersonStatement.setString(2,profesor.getSurname());
        updatePersonStatement.setInt(3,profesor.getPostalNumber());
        updatePersonStatement.setLong(4,profesor.getPhone_number());
        updatePersonStatement.setLong(5,profesor.getBirthYear());

        updatePersonStatement.setInt(6,profesor.getId());

        updatePersonStatement.executeUpdate();

    }

    public void deleteProfessor(Profesor profesor,Account account) throws SQLException {
        deleteAccountStatement.setInt(1,account.getId());
        deleteAccountStatement.executeUpdate();

        deleteProfessorStatement.setInt(1,currentProfessor.get().getId());

        deleteProfessorStatement.executeUpdate();

        getProfessorSubject.setInt(1,currentProfessor.get().getId());
        ResultSet rs = getProfessorSubject.executeQuery();
        int id_sub = 0;

        if(rs.next()){
            id_sub = rs.getInt(1);
        }
        deleteProfessorSubject.setInt(1,id_sub);

        deleteProfessorSubject.executeUpdate();

        deletePersonStatement.setInt(1,profesor.getId());
        deletePersonStatement.executeUpdate();

    }

    public Profesor getCurrentProfessor() {
        if(currentProfessor == null) return null;
        return currentProfessor.get();
    }

    public SimpleObjectProperty<Profesor> currentProfessorProperty() {
        return currentProfessor;
    }

    public void setCurrentProfessor(Profesor currentProfessor) {
        if(this.currentProfessor == null){
            this.currentProfessor = new SimpleObjectProperty<>(currentProfessor);
        }
        else
        this.currentProfessor.set(currentProfessor);
    }

    public Administrator getCurrentAdmin() {
        return currentAdmin.get();
    }

    public SimpleObjectProperty<Administrator> currentAdminProperty() {
        return currentAdmin;
    }

    public void setCurrentAdmin(Administrator currentAdmin) {
        if(this.currentAdmin == null){
            this.currentAdmin = new SimpleObjectProperty<>(currentAdmin);
        }
        else
        this.currentAdmin.set(currentAdmin);
    }
}

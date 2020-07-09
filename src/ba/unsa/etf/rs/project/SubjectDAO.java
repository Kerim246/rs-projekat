package ba.unsa.etf.rs.project;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SubjectDAO {
    private PreparedStatement addProfessorStatement,updateProfessorStatement,deleteProfessorStatement,getMaxIdProfessor,addAccountStatement,updateAccountStatement,deleteAccountStatement,getMaxAccountId,getAccount;
    private PreparedStatement getProfessorsUpit,getAllAccounts,getProfessorMaterial,getProfesorUpit,nadjiPredmetUpit,getProfessorPredmetUpit,getAllPredmet,nadjiPredmetId,addMaterialUpit,getIdMaterial;
    private PreparedStatement findTypeStatement,getAllTypesStatement,findTypeStatementName,updateMaterialStatement,deleteMaterialStatement,getAllMaterialStatement,getAllAdminsStatement,getAllSubjectProfessorStatement;
    private SimpleObjectProperty<Material> currentMaterial = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Profesor> currentProfessor = new SimpleObjectProperty<>();
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
            getProfessorsUpit = connection.prepareStatement("SELECT * from Professor");
            getAllAccounts = connection.prepareStatement("SELECT a.id,a.username,a.password,a.professor from account a,professor p where a.professor = p.id");
            getProfessorMaterial = connection.prepareStatement("SELECT m.id,m.name,m.type,m.publication_date,m.subject_id,m.content,pr.id from material m,professor pr,subject p,type t where m.subject_id = p.id AND p.professor = pr.id AND t.id = m.type");
            nadjiPredmetUpit = connection.prepareStatement("SELECT * from subject where name=?");
            getAllPredmet = connection.prepareStatement("SELECT * from subject");
            getProfesorUpit = connection.prepareStatement("SELECT * from professor where id=?");
            nadjiPredmetId = connection.prepareStatement("SELECT * from subject where id=?");
            addMaterialUpit = connection.prepareStatement("INSERT INTO material VALUES(?,?,?,?,?,?)");
            getIdMaterial = connection.prepareStatement("SELECT Max(id)+1 FROM material");

            findTypeStatement = connection.prepareStatement("SELECT * from type where id=?");
            getAllTypesStatement = connection.prepareStatement("SELECT * from type");
            findTypeStatementName = connection.prepareStatement("SELECT * from type where name=?");
            updateMaterialStatement = connection.prepareStatement("UPDATE Material SET name=?,type=?,publication_date=?,subject_id=?,content=? WHERE id=?");
            deleteMaterialStatement = connection.prepareStatement("DELETE FROM Material where id=?");
            getAllMaterialStatement = connection.prepareStatement("SELECT * from Material");
            getAllAdminsStatement = connection.prepareStatement("SELECT * from administrator");
            getAllSubjectProfessorStatement = connection.prepareStatement("SELECT * from subject where professor=?");
            addProfessorStatement = connection.prepareStatement("INSERT INTO Professor VALUES(?,?,?,?,?)");
            deleteProfessorStatement = connection.prepareStatement("DELETE FROM Professor where id=?");
            updateProfessorStatement = connection.prepareStatement("UPDATE Professor SET name=?,surname=?,employment_date=? where id=?");
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

    public Account getAccount(int id_prof) throws SQLException {
         getAccount.setInt(1,id_prof);
         ResultSet rs = getAccount.executeQuery();
        Account account = new Account();
        if(rs.next()){
            account = new Account(rs.getInt(1),rs.getString(2),rs.getString(3),findProfessor(rs.getInt(4)));
        }

        return account;
    }

    public ArrayList<Administrator> getAdmins() throws SQLException {
        ResultSet rs = getAllAdminsStatement.executeQuery();
        ArrayList<Administrator> admins = new ArrayList<>();
        while(rs.next()){
            admins.add(new Administrator(rs.getInt(1),rs.getString(2),rs.getString(3)));
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

    public ObservableList<Profesor> getAllProfessors() throws SQLException {
        ResultSet rs = getProfessorsUpit.executeQuery();
        ObservableList<Profesor> professors = FXCollections.observableArrayList();

        while(rs.next()){
            professors.add(new Profesor(rs.getInt(1),rs.getString(2),rs.getString(3),LocalDate.parse(rs.getString(4)),rs.getInt(5)));
        }

        return professors;
    }

    public ObservableList<Subject> getAllSubjectsProfessor(int id_prof) throws SQLException {
        getAllSubjectProfessorStatement.setInt(1,id_prof);
        ResultSet rs = getAllSubjectProfessorStatement.executeQuery();
        ObservableList<Subject> subjects = FXCollections.observableArrayList();

        System.out.println(subjects.size());

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
            profesor = new Profesor(rs.getInt(1),rs.getString(2),rs.getString(3),LocalDate.parse(rs.getString(4)),rs.getInt(5));
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


    public ArrayList<Profesor> GetAllProfessors(){
        ArrayList<Profesor> professors = new ArrayList<>();

        try {
            ResultSet rs = getProfessorsUpit.executeQuery();

            while(rs.next()){
                professors.add(new Profesor(rs.getInt(1),rs.getString(2),rs.getString(3),LocalDate.parse(rs.getString(4)),rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professors;
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
        int id = 1;
        ResultSet rs = getMaxIdProfessor.executeQuery();

        if(rs.next()){
            id = rs.getInt(1);
        }

        addProfessorStatement.setInt(1,id);
        addProfessorStatement.setString(2,profesor.getName());
        addProfessorStatement.setString(3,profesor.getSurname());
        addProfessorStatement.setString(4,profesor.getEmployment_date().toString());
        addProfessorStatement.setInt(5,profesor.getPostalNumber());

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

    }

    public void updateProfessor(Profesor profesor,Account account) throws SQLException {
        updateProfessorStatement.setString(1,profesor.getName());
        updateProfessorStatement.setString(2,profesor.getSurname());
        updateProfessorStatement.setString(3,profesor.getEmployment_date().toString());

        updateProfessorStatement.setInt(4,currentProfessor.get().getId());

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

    }

    public void deleteProfessor(Profesor profesor,Account account) throws SQLException {
        deleteAccountStatement.setInt(1,account.getId());
        deleteAccountStatement.executeUpdate();

        deleteProfessorStatement.setInt(1,currentProfessor.get().getId());

        deleteProfessorStatement.executeUpdate();
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
}

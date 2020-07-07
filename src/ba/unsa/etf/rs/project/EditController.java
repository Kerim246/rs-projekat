package ba.unsa.etf.rs.project;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class EditController {
    public TextField fldName;
    public TextField fldDate;
    public TextField fldSubject;
    public TextArea fldContent;
    public ComboBox comboType;
    private ObservableList<Type> types;
    private ObservableList<Subject> subjects;
    private static SubjectDAO dao;
    private Material material;
    public Button btnOk;



    public EditController(SubjectDAO dao,Material material){
        this.dao = dao;
        this.material = material;
    }

    @FXML
    public void initialize() throws SQLException {
        dao = new SubjectDAO();
        types = dao.getAllTypes();
        comboType.setItems(types);
        if(material == null){
            fldName.getStyleClass().add("poljeNijeIspravno");
            fldDate.getStyleClass().add("poljeNijeIspravno");
            fldContent.getStyleClass().add("poljeNijeIspravno");
            fldSubject.getStyleClass().add("poljeNijeIspravno");
        }
        else {
            fldName.getStyleClass().add("poljeIspravno");
            fldDate.getStyleClass().add("poljeIspravno");
            fldContent.getStyleClass().add("poljeIspravno");
            fldSubject.getStyleClass().add("poljeIspravno");

            fldName.setText(material.getName());
            fldContent.setText(material.getContent());
            fldDate.setText(material.getPublication_date().toString());
            fldSubject.setText(material.getSubject().getName());
        }
    }

    private Subject findSubject(String predmet){
        Subject pred = new Subject();
        try {
            pred = dao.findSubject(predmet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pred;
    }

    private Type findType(String type) throws SQLException {
        Type tajp = dao.findType(type);
        return tajp;
    }

    public void actOk(ActionEvent actionEvent) {
        try {
            System.out.println(fldName.getText() + " " +findType(comboType.getValue().toString()) + " " +fldContent.getText() + " "+ LocalDate.parse(fldDate.getText()) + " " +findSubject(fldSubject.getText()));
            material = new Material(fldName.getText(),findType(comboType.getValue().toString()), LocalDate.parse(fldDate.getText()),findSubject(fldSubject.getText()),fldContent.getText());
            dao.addMaterial(material);

            Stage stage = (Stage) btnOk.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actCancel(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}

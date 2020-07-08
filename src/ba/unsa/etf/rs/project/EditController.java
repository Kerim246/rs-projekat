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
    public ComboBox comboSubject;
    public TextArea fldContent;
    public ComboBox comboType;
    private ObservableList<Type> types;
    private ObservableList<Subject> subjects;
    private static SubjectDAO dao;
    private Material material;
    public Button btnOk;
    private int id_prof = -1;

    public EditController(Material material,int id_prof){
        this.dao = SubjectDAO.getInstance();
        this.material = material;
        this.id_prof = id_prof;
    }

    @FXML
    public void initialize() throws SQLException {
        types = dao.getAllTypes();
        if(id_prof == -1)
        subjects = dao.getAllSubjects();
        else {
            subjects = dao.getAllSubjectsProfessor(id_prof);
        }
        comboType.setItems(types);
        comboSubject.setItems(subjects);
        if(material == null){
            fldName.getStyleClass().add("poljeNijeIspravno");
            fldDate.getStyleClass().add("poljeIspravno");
            fldDate.setText(LocalDate.now().toString());
            fldContent.getStyleClass().add("poljeNijeIspravno");
           // comboSubject.getStyleClass().add("poljeNijeIspravno");
        }
        else {
            fldName.getStyleClass().add("poljeIspravno");
            fldDate.getStyleClass().add("poljeIspravno");
            fldContent.getStyleClass().add("poljeIspravno");
            comboSubject.getStyleClass().add("poljeIspravno");

            fldName.setText(material.getName());
            fldContent.setText(material.getContent());
            fldDate.setText(material.getPublication_date().toString());
            comboSubject.setValue(material.getSubject().getName());
            comboType.setValue(material.getType());
        }
        fldName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3) {
                fldName.getStyleClass().removeAll("poljeNijeIspravno");
                fldName.getStyleClass().add("poljeIspravno");
            } else {
                fldName.getStyleClass().removeAll("poljeIspravno");
                fldName.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldContent.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3) {
                fldContent.getStyleClass().removeAll("poljeNijeIspravno");
                fldContent.getStyleClass().add("poljeIspravno");
            } else {
                fldContent.getStyleClass().removeAll("poljeIspravno");
                fldContent.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldDate.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3) {
                fldDate.getStyleClass().removeAll("poljeNijeIspravno");
                fldDate.getStyleClass().add("poljeIspravno");
            } else {
                fldDate.getStyleClass().removeAll("poljeIspravno");
                fldDate.getStyleClass().add("poljeNijeIspravno");
            }
        });

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
          //   System.out.println(fldName.getText() + " " +findType(comboType.getValue().toString()) + " " +fldContent.getText() + " "+ LocalDate.parse(fldDate.getText()) + " " +findSubject(fldSubject.getText()));
            if(fldName.getText().length() > 3 && findType(comboType.getValue().toString()) != null && fldDate.getText().length() > 3 && findSubject(comboSubject.getValue().toString()) != null && fldContent.getText().length() > 3);
            material = new Material(fldName.getText(),findType(comboType.getValue().toString()), LocalDate.parse(fldDate.getText()),findSubject(comboSubject.getValue().toString()),fldContent.getText());

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

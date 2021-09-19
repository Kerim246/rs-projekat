package ba.unsa.etf.rs.project;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class EditController {
    public TextField fldName;
    public DatePicker fldDate;
    public ComboBox comboSubject;
    public TextArea fldContent;
    public ComboBox comboType;
    public TextField fldProfessor;
    private ObservableList<Type> types;
    private ObservableList<Subject> subjects;
    private static SubjectDAO dao;
    private Material material;
    public Button btnOk;
    private int id_prof = -1;
    private String newSubject = "";
    private String newType = "";
    private boolean datumIspravan = true;

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
            fldDate.setValue(LocalDate.now());
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
            fldDate.setValue(material.getPublication_date());
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
        fldDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                if (t1 == null || t1.isAfter(LocalDate.now())) {
                    fldDate.getEditor().getStyleClass().removeAll("poljeIspravno");
                    fldDate.getEditor().getStyleClass().add("poljeNijeIspravno");
                    datumIspravan = false;

                } else {
                    fldDate.getEditor().getStyleClass().removeAll("poljeNijeIspravno");
                    fldDate.getEditor().getStyleClass().add("poljeIspravno");
                    datumIspravan = true;
                }
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
            if(!datumIspravan){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect date");
                alert.setHeaderText("Incorrect date!");
                alert.setContentText("Please enter valid date!");
                alert.showAndWait();
            }
            if(fldName.getText().length() < 4){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect name of material");
                alert.setHeaderText("Incorrect name of material!");
                alert.setContentText("Name must be atleast 3 characters long!");
                alert.showAndWait();
            }
            if(comboType.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect type");
                alert.setHeaderText("Incorrect type!");
                alert.setContentText("Please choose a type!");
                alert.showAndWait();
            }
            if(comboSubject.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect subject");
                alert.setHeaderText("Incorrect subject!");
                alert.setContentText("Please choose a subject!");
                alert.showAndWait();
            }
            if(fldContent.getText().length() < 4){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect content");
                alert.setHeaderText("Incorrect content!");
                alert.setContentText("Please type content!");
                alert.showAndWait();
            }
        //     System.out.println(fldName.getText() + " " +findType(comboType.getValue().toString()) + " " +fldContent.getText() + " "+ fldDate.getValue() + " " +findSubject(comboSubject.getValue().toString()).toString());
            if (comboSubject.getValue() != null && comboType.getValue() != null) {
                if(fldName.getText().length() > 3 && findType(comboType.getValue().toString()) != null && datumIspravan && findSubject(comboSubject.getValue().toString()) != null && fldContent.getText().length() > 3 ) {
                    material = new Material(fldName.getText(), findType(comboType.getValue().toString()), fldDate.getValue(), findSubject(comboSubject.getValue().toString()), fldContent.getText());
                    Stage stage = (Stage) btnOk.getScene().getWindow();
                    stage.close();
                }
            }

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

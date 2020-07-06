package ba.unsa.etf.rs.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class MainController {

    private static SubjectDAO subjectDAO;
    public Tab materialsTab;
    public TableView tableMaterials;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colDate;
    private int id_profesora;
    private SubjectDAO model;

    public MainController(SubjectDAO subjectDAO, int id_profesora){
        this.subjectDAO = subjectDAO;
        this.id_profesora = id_profesora;
    }

    @FXML
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<Profesor,Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Profesor,Integer>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<Profesor,Integer>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<Profesor,Integer>("publication_date"));

        tableMaterials.setItems(subjectDAO.getProfessorMaterial(id_profesora));

    }


    public void prebaciSQL(ActionEvent actionEvent) {
    }

    public void prebaciXML(ActionEvent actionEvent) {
    }

    public void actAddMaterial(ActionEvent actionEvent) {
    }

    public void actRemoveMaterial(ActionEvent actionEvent) {
    }

    public void actEditMaterial(ActionEvent actionEvent) {
    }
}

package ba.unsa.etf.rs.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class MainController {

    public SubjectDAO subjectDAO;
    public Tab materialsTab;
    public TableView tableMaterials;
    public TableColumn colSubject;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colDate;
    private int id_profesora;

    public MainController(SubjectDAO subjectDAO,int id_profesora){
        this.subjectDAO = subjectDAO;
        this.id_profesora = id_profesora;
    }

    @FXML
    public void initialize(){
        colSubject.setCellValueFactory(new PropertyValueFactory<Profesor,Integer>("subject"));
        colName.setCellValueFactory(new PropertyValueFactory<Profesor,Integer>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<Profesor,Integer>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<Profesor,Integer>("publication_date"));

        tableMaterials.setItems(subjectDAO.getProfessorMaterial(id_profesora));

        tableMaterials.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Material>() {
            @Override
            public void changed(ObservableValue<? extends Material> observableValue, Material oldBook, Material newBook) {
                if (oldBook != null) {
                }
                if (newBook == null) {

                } else {
                    Material material = (Material) tableMaterials.getSelectionModel().getSelectedItem();
                    subjectDAO.setCurrentMaterial(material);
                }
                tableMaterials.refresh();
            }
        });

    }


    public void prebaciSQL(ActionEvent actionEvent) {
    }

    public void prebaciXML(ActionEvent actionEvent) {
    }

    public void actAddMaterial(ActionEvent actionEvent) {
        Stage editMaterialWindow = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit.fxml"));
            EditController editController = new EditController(null);
            loader.setController(editController);
            root = loader.load();
            editMaterialWindow.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            editMaterialWindow.setResizable(false);
            editMaterialWindow.show();
            editMaterialWindow.setOnHiding( event -> {
                Material materijal = editController.getMaterial();
                if (materijal != null) {
                    subjectDAO.addMaterial(materijal);
                    initialize();
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actRemoveMaterial(ActionEvent actionEvent) {
        Material material = (Material) tableMaterials.getSelectionModel().getSelectedItem();
        if (material != null) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("");
                alert.setContentText("Delete material?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    subjectDAO.deleteMaterial(material);
                    tableMaterials.getSelectionModel().selectFirst();
                    initialize();
                }
            } catch(IllegalArgumentException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Unable to delete material");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    public void actEditMaterial(ActionEvent actionEvent) {
        Stage editMaterialWindow = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit.fxml"));
            Material material = (Material) tableMaterials.getSelectionModel().getSelectedItem();
            EditController editController = new EditController(material);
            loader.setController(editController);
            root = loader.load();
            editMaterialWindow.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            editMaterialWindow.setResizable(false);
            editMaterialWindow.show();


            editMaterialWindow.setOnHiding( event -> {
                Material materijal = editController.getMaterial();
                if (materijal != null) {
                    subjectDAO.updateMaterial(materijal);
                    initialize();
                }

            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

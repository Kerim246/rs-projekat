package ba.unsa.etf.rs.project;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class MainController {

    public SubjectDAO subjectDAO;
    public TableView tableMaterials;
    public TableColumn colSubject;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colDate;
    public TableColumn colFirstName;
    public TableColumn colSurname;
    public TableColumn colEmpDate;
    public TableView tableProfessors;
    private int id_profesora=-1;
    public TextField fldSearch;
    public Label labelStatus;
    private int pom = 0;
    public ObservableList<Material> materials = FXCollections.observableArrayList();

    public MainController(SubjectDAO subjectDAO,int id_profesora){
        this.subjectDAO = subjectDAO;
        this.id_profesora = id_profesora;
    }

    public MainController(SubjectDAO subjectDAO){
        this.subjectDAO = subjectDAO;
    }


    @FXML
    public void initialize() throws SQLException {
        colSubject.setCellValueFactory(new PropertyValueFactory<Material,Integer>("subject"));
        colName.setCellValueFactory(new PropertyValueFactory<Material,Integer>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<Material,Integer>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<Material,Integer>("publication_date"));

        if(id_profesora != -1)
        tableMaterials.setItems(subjectDAO.getProfessorMaterial(id_profesora));
        else tableMaterials.setItems(subjectDAO.getAllMaterials());

        if(pom ==0)
        labelStatus.setText("Welcome to application!");

        pom++;
        tableMaterials.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Material>() {
            @Override
            public void changed(ObservableValue<? extends Material> observableValue, Material oldMaterial, Material newMaterial) {
                if (oldMaterial != null) {
                }
                if (newMaterial == null) {

                } else {
                    Material material = (Material) tableMaterials.getSelectionModel().getSelectedItem();
                    subjectDAO.setCurrentMaterial(material);
                }
                tableMaterials.refresh();
            }
        });

        if(id_profesora != -1) {
            FilteredList<Material> filteredData = new FilteredList<>(subjectDAO.getProfessorMaterial(id_profesora), b -> true);

            fldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(material -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    labelStatus.setText("Searching materials");

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (material.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Material> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tableMaterials.comparatorProperty());

            tableMaterials.setItems(sortedData);
        }
        else {
            FilteredList<Material> filteredData = new FilteredList<>(subjectDAO.getAllMaterials(), b -> true);

            fldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(material -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    labelStatus.setText("Searching materials");

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (material.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Material> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tableMaterials.comparatorProperty());

            tableMaterials.setItems(sortedData);
        }

        if(id_profesora == -1) {
            colFirstName.setCellValueFactory(new PropertyValueFactory<Profesor, Integer>("name"));
            colSurname.setCellValueFactory(new PropertyValueFactory<Profesor, Integer>("surname"));

            colEmpDate.setCellValueFactory(new PropertyValueFactory<Profesor, Integer>("employment_date"));


            tableProfessors.setItems(subjectDAO.getAllProfessors());

            tableProfessors.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profesor>() {
                @Override
                public void changed(ObservableValue<? extends Profesor> observableValue, Profesor oldProfessor, Profesor newProfessor) {
                    if (oldProfessor != null) {
                    }
                    if (newProfessor == null) {

                    } else {
                        Profesor profesor = (Profesor) tableProfessors.getSelectionModel().getSelectedItem();
                        subjectDAO.setCurrentProfessor(profesor);
                    }
                    tableProfessors.refresh();
                }
            });

        }
    }


    public void openXML(ActionEvent actionEvent) {
        ArrayList<Material> mat = XMLFormat.load();
        if(materials.size() != 0)
        materials.clear();

        for(Material m : mat){
            materials.add(m);
        }

        tableMaterials.setItems(materials);
    }

    public void saveXML(ActionEvent actionEvent) {
        XMLFormat.save(subjectDAO.getProfessorMaterial(id_profesora));
    }


    public void actAddMaterial(ActionEvent actionEvent) {
        labelStatus.setText("Adding Material");
        Stage editMaterialWindow = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit.fxml"));
            EditController editController = new EditController(null,id_profesora);
            loader.setController(editController);
            root = loader.load();
            editMaterialWindow.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            editMaterialWindow.setResizable(false);
            editMaterialWindow.show();
            editMaterialWindow.setOnHiding( event -> {
                Material materijal = editController.getMaterial();
                if (materijal != null) {
                    subjectDAO.addMaterial(materijal);
                    labelStatus.setText("Material added");
                    try {
                        initialize();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    labelStatus.setText("Welcome to application");
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actRemoveMaterial(ActionEvent actionEvent) {
        labelStatus.setText("Removing Material");
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
                    labelStatus.setText("Material removed");
                    initialize();
                }
                else {
                    labelStatus.setText("Welcome to application!");
                }
            } catch(IllegalArgumentException | SQLException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Unable to delete material");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    public void actEditMaterial(ActionEvent actionEvent) {
        labelStatus.setText("Editing Material");
        Stage editMaterialWindow = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit.fxml"));
            Material material = (Material) tableMaterials.getSelectionModel().getSelectedItem();
            EditController editController = new EditController(material,id_profesora);
            loader.setController(editController);
            root = loader.load();
            editMaterialWindow.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            editMaterialWindow.setResizable(false);
            editMaterialWindow.show();


            editMaterialWindow.setOnHiding( event -> {
                Material materijal = editController.getMaterial();
                if (materijal != null) {
                    subjectDAO.updateMaterial(materijal);
                    labelStatus.setText("Material edited");
                    try {
                        initialize();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    labelStatus.setText("Welcome to application!");
                }

            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actAddProfessor(ActionEvent actionEvent) {
        labelStatus.setText("Adding Professor");
        Stage editMaterialWindow = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editProfessor.fxml"));
            EditProfessorController editController = new EditProfessorController(null);
            loader.setController(editController);
            root = loader.load();
            editMaterialWindow.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            editMaterialWindow.setResizable(false);
            editMaterialWindow.show();
            editMaterialWindow.setOnHiding( event -> {
                Profesor profesor = editController.getProfessor();
                if (profesor != null) {
                    try {
                        subjectDAO.addProfessor(profesor);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    labelStatus.setText("Professor added");
                    try {
                        initialize();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    labelStatus.setText("Welcome to application");
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actRemoveProfessor(ActionEvent actionEvent) {
    }

    public void actEditProfessor(ActionEvent actionEvent) {
    }
}

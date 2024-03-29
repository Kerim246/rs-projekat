package ba.unsa.etf.rs.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {

    public TextField fldUsername;
    public TextField fldPassword;
    private static SubjectDAO subjectDAO;
    private Button btnCancel;
    boolean prof = true;

    public LoginController(Person p) {
        if (p instanceof Administrator) {
            prof = false;
        }
    }

    @FXML
    public void initialize(){
        fldPassword.setText("");
        fldUsername.setText("");
        this.subjectDAO = SubjectDAO.getInstance();
    }


    public void actLogin(ActionEvent actionEvent) throws SQLException {
        String username = fldUsername.getText();
        String pass = fldPassword.getText();
        ArrayList<Account> osobe = new ArrayList<>();
        ObservableList<Administrator> admini = FXCollections.observableArrayList();

        if (prof)
            osobe = subjectDAO.getAllAccounts();
        else {
            admini = subjectDAO.getAdmins();
        }

        if (prof) {
            for (int i = 0; i < osobe.size(); i++) {
                if (osobe.get(i).getUsername().equals(username) && osobe.get(i).getPassword().equals(pass)) {
                    fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                    fldUsername.getStyleClass().add("poljeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPassword.getStyleClass().add("poljeIspravno");
                    subjectDAO = SubjectDAO.getInstance();
                    int id_profesora = osobe.get(i).getProfesor().getId();
                    System.out.println("id "+osobe.get(i).getUsername() + " " + osobe.get(i).getId());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
                    MainController mainController = new MainController(subjectDAO, id_profesora);
                    Stage editBookWindow = new Stage();
                    loader.setController(mainController);
                    Parent root = null;
                    try {
                        root = loader.load();
                        editBookWindow.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                        editBookWindow.setResizable(false);
                        editBookWindow.show();
                        actCancel(actionEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    fldUsername.getStyleClass().removeAll("poljeIspravno");
                    fldUsername.getStyleClass().add("poljeNijeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeIspravno");
                    fldPassword.getStyleClass().add("poljeNijeIspravno");
                }
            }
        }
        else {
            for (int i = 0; i < admini.size(); i++) {
                if (admini.get(i).getUsername().equals(username) && admini.get(i).getPassword().equals(pass)) {
                    fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                    fldUsername.getStyleClass().add("poljeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPassword.getStyleClass().add("poljeIspravno");
                    subjectDAO = SubjectDAO.getInstance();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainAdmin.fxml"));
                    MainController mainController = new MainController(subjectDAO);
                    Stage stage = new Stage();
                    loader.setController(mainController);
                    Parent root = null;
                    try {
                        root = loader.load();
                        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                        stage.setResizable(false);
                        stage.show();
                        actCancel(actionEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    fldUsername.getStyleClass().removeAll("poljeIspravno");
                    fldUsername.getStyleClass().add("poljeNijeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeIspravno");
                    fldPassword.getStyleClass().add("poljeNijeIspravno");
                }
            }
        }
    }


    @FXML
    public void actCancel(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}

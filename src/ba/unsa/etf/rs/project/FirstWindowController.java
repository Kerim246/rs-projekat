package ba.unsa.etf.rs.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class FirstWindowController {


    public void actionAdmin(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Administrator a = new Administrator();
        loader.setController(new LoginController(a));
        root = loader.load();
        window.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        window.setResizable(false);
        window.show();
        actCancel(actionEvent);
    }

    public void actProfessor(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Profesor p = new Profesor();
        loader.setController(new LoginController(p));
        root = loader.load();
        window.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        window.setResizable(false);
        window.show();
        actCancel(actionEvent);
    }

    @FXML
    public void actCancel(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}

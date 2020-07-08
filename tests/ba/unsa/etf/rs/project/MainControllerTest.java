package ba.unsa.etf.rs.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class MainControllerTest {
    @Start
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("prijava.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }


    @Test
    void initialize() {
    }

    @Test
    void actAddMaterial() {
    }

    @Test
    void actRemoveMaterial() {
    }

    @Test
    void actEditMaterial() {
    }
}
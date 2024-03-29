package ba.unsa.etf.rs.project;
/*
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.util.ArrayList;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    Stage thestage;

    @Start
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FirstWindow.fxml"));
        primaryStage.setTitle("Aplikacija za upravljanje nastavnim materijalima");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
        primaryStage.toFront();
        thestage = primaryStage;
    }

    public static boolean sadrziStil(TextField polje, String stil) {
        for (String s : polje.getStyleClass())
            if (s.equals(stil)) return true;
        return false;
    }

    @Test
    void actLoginTest1(FxRobot robot) throws SQLException {    // Polje nije ispravno jer profesor ne postoji u bazi
        robot.clickOn("#btnProfessor");
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("anonymous");
        robot.clickOn("#fldPassword");
        robot.write("anonymous123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);
        assertTrue(sadrziStil(polje1, "poljeNijeIspravno"));
        assertTrue(sadrziStil(polje2, "poljeNijeIspravno"));

    }

    @Test
    void actLoginTest2(FxRobot robot) throws SQLException {    // Polje ispravno jer profesor postoji
        robot.clickOn("#btnProfessor");
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        Button btnCancel = robot.lookup("#btnCancel").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Prof");
        robot.clickOn("#fldPassword");
        robot.write("prof123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);
        assertTrue(sadrziStil(polje1, "poljeIspravno"));
        assertTrue(sadrziStil(polje2, "poljeIspravno"));
    }

    @Test
    void actCancel(FxRobot robot) {
        robot.clickOn("#btnProfessor");
        Button btnCancel = robot.lookup("#btnCancel").queryAs(Button.class);
        robot.clickOn("#btnCancel");
        thestage = (Stage)btnCancel.getScene().getWindow();
        assertFalse(thestage.isShowing());
    }
}*/
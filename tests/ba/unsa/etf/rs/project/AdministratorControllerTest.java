package ba.unsa.etf.rs.project;
/*
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class AdministratorControllerTest {
    SubjectDAO subjectDAO;

    @Start
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FirstWindow.fxml"));
        stage.setTitle("Aplikacija za upravljanje nastavnim materijalima");
        stage.setScene(new Scene(root, 400, 200));
        stage.show();
        stage.toFront();
        subjectDAO = SubjectDAO.getInstance();
    }

    @Test
    void actLoginValid(FxRobot robot) {
        robot.clickOn("#btnAdmin");

        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Admin");
        robot.clickOn("#fldPassword");
        robot.write("admin123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);


        assertTrue(LoginControllerTest.sadrziStil(polje1, "poljeIspravno"));          // Ispravno unesen admin
        assertTrue(LoginControllerTest.sadrziStil(polje2, "poljeIspravno"));

    }

    @Test
    void actLoginInValid(FxRobot robot) {
        robot.clickOn("#btnAdmin");

        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Admin");
        robot.clickOn("#fldPassword");
        robot.write("admin12345");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);


        assertTrue(LoginControllerTest.sadrziStil(polje1, "poljeNijeIspravno"));          // Ispravno unesen admin
        assertTrue(LoginControllerTest.sadrziStil(polje2, "poljeNijeIspravno"));

    }


} */
package ba.unsa.etf.rs.project;
/*
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ApplicationExtension.class)
class EditProfessorControllerTest {   // Iz nekog razloga nekada prodju testovi,nekada ne,nekada robot preskoci kontrolu,nekada ne
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
    @Order(1)
    void testAddProfessor(FxRobot robot) throws SQLException {
        robot.clickOn("#btnAdmin");

        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Admin");
        robot.clickOn("#fldPassword");
        robot.write("admin123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);

        robot.clickOn("#professorsTab");
        robot.clickOn("#tbAddProfessor");

        robot.clickOn("#tbAddProfessor");

        robot.clickOn("#fldName");
        robot.write("Test");

        robot.clickOn("#fldSurname");
        robot.write("Testic");

        robot.clickOn("#fldDate");
        robot.write("2020-04-08");

        robot.clickOn("#fldPostalNumber");
        robot.write("71000");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.clickOn("#fldUsername");
        robot.write("username");

        robot.clickOn("#fldPassword");
        robot.write("Password123");

        robot.clickOn("#btnOk");

        try {
            Thread.sleep(5000);           // Vrijeme potrebno da se validira postalnumber
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObservableList<Profesor> professors = subjectDAO.getAllProfessors();

        assertEquals("Test", professors.get(professors.size() - 1).getName());
        assertEquals("Testic", professors.get(professors.size() - 1).getSurname());

    }

    @Test
    @Order(2)
    void testEditProfessor(FxRobot robot) throws SQLException {
        robot.clickOn("#btnAdmin");

        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Admin");
        robot.clickOn("#fldPassword");
        robot.write("admin123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);

        robot.clickOn("#professorsTab");
        robot.clickOn("#tableProfessors");
        robot.clickOn("Test");
        robot.clickOn("#tbEditProfessor");

        robot.clickOn("#fldName");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("Testttttt");

        robot.clickOn("#fldSurname");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("Testicccccc");

        robot.clickOn("#fldDate");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("2020-05-08");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.clickOn("#fldUsername");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("username34");

        robot.clickOn("#fldPassword");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("Password12345");

        robot.clickOn("#btnOk");

        try {
            Thread.sleep(5000);           // Vrijeme potrebno da se validira postalnumber
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObservableList<Profesor> professors = subjectDAO.getAllProfessors();
        Account account = subjectDAO.getAccount(professors.get(professors.size()-1).getId());


        assertEquals("Testttttt", professors.get(professors.size() - 1).getName());
        assertEquals("Testicccccc", professors.get(professors.size() - 1).getSurname());
        assertEquals("2020-05-08", professors.get(professors.size() - 1).getEmployment_date().toString());
        assertEquals("username34", account.getUsername());
        assertEquals("Password12345", account.getPassword());

    }

    @Test
    @Order(3)
    void testRemoveProfessor(FxRobot robot) throws SQLException {
        robot.clickOn("#btnAdmin");
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Admin");
        robot.clickOn("#fldPassword");
        robot.write("admin123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);

        robot.clickOn("#professorsTab");
        robot.clickOn("#tableProfessors");
        robot.clickOn("Testttttt");

        ObservableList<Profesor> profesors = subjectDAO.getAllProfessors();
        int vel = profesors.size();

        robot.clickOn("#tbRemoveProfessor");


        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        profesors = subjectDAO.getAllProfessors();
        assertEquals(vel-1, profesors.size());
    }


} */
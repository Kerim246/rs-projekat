package ba.unsa.etf.rs.project;

/*
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ApplicationExtension.class)
class MainControllerTest {                   // Nekada testovi padaju,nekada ne
    SubjectDAO subjectDAO;
    Stage theStage;
    @Start
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FirstWindow.fxml"));
        stage.setTitle("Aplikacija za upravljanje nastavnim materijalima");
        stage.setScene(new Scene(root, 400, 200));
        stage.show();
        stage.toFront();
        subjectDAO = SubjectDAO.getInstance();
        theStage = stage;
    }

    @Test
    @Order(1)
    void actAddMaterial(FxRobot robot) throws SQLException {
        robot.clickOn("#btnProfessor");
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Drugi");
        robot.clickOn("#fldPassword");
        robot.write("drugi123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);

        robot.clickOn("#tbAddMaterial");
      //  SubjectDAO subjectDAO = new SubjectDAO();

        robot.clickOn("#fldName");
        robot.write("Predavanje12");
     //   robot.clickOn("#fldDate");
     //   robot.write("2020-08-10");
      //  robot.clickOn("#fldSubject");
      //  robot.write("Razvoj softvera");
        Platform.runLater(() -> theStage.hide());

        robot.lookup("#comboType").tryQuery().isPresent();

        ComboBox comboType = robot.lookup("#comboType").queryAs(ComboBox.class);

        Platform.runLater(() -> comboType.show());

        // Čekamo da se pojavi meni
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.clickOn("Predavanje");
        Platform.runLater(() -> theStage.hide());

        robot.lookup("#comboSubject").tryQuery().isPresent();

        ComboBox comboSubject = robot.lookup("#comboSubject").queryAs(ComboBox.class);

        Platform.runLater(() -> comboSubject.show());

        // Čekamo da se pojavi meni
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.clickOn("Matematika1");


        robot.clickOn("#fldContent");
        robot.write("Ovo je sadrzaj");

        robot.clickOn("#btnOk");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ObservableList<Material> materials = subjectDAO.getAllMaterials();

        assertEquals("Predavanje12",materials.get(materials.size()-1).getName());


    }

    @Test
    @Order(2)
    void actEditMaterial(FxRobot robot) throws SQLException {
        robot.clickOn("#btnProfessor");
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Drugi");
        robot.clickOn("#fldPassword");
        robot.write("drugi123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);

        robot.clickOn("#materialsTab");
        robot.clickOn("#tableMaterials");

        robot.clickOn("Predavanje12");
        robot.clickOn("#tbEditMaterial");

        robot.clickOn("#fldName");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("Tutorijal12");
        robot.clickOn("#fldDate");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("2020-04-10");
      //  robot.clickOn("#fldSubject");
      //  robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
      //  robot.write("Razvoj softvera");
        robot.clickOn("#fldContent");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("Novi sadrzaj");

        robot.lookup("#comboType").tryQuery().isPresent();

        ComboBox comboType = robot.lookup("#comboType").queryAs(ComboBox.class);

        Platform.runLater(() -> comboType.show());

        // Čekamo da se pojavi meni
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.clickOn("Tutorijal");

        robot.clickOn("#btnOk");

        ObservableList<Material> materials = subjectDAO.getAllMaterials();
        assertEquals("Tutorijal12",materials.get(materials.size()-1).getName());
        assertEquals("Novi sadrzaj",materials.get(materials.size()-1).getContent());
        assertEquals("2020-04-10",materials.get(materials.size()-1).getPublication_date().toString());
        assertEquals("Matematika1",materials.get(materials.size()-1).getSubject().getName());
        assertEquals("Tutorijal",materials.get(materials.size()-1).getType().getName());
    }

    @Test
    @Order(3)
    void actRemoveMaterial(FxRobot robot) throws SQLException {
        robot.clickOn("#btnProfessor");
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Drugi");
        robot.clickOn("#fldPassword");
        robot.write("drugi123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);
        robot.clickOn("#materialsTab");
        robot.clickOn("#tableMaterials");

        robot.clickOn("Predavanje12");

        robot.clickOn("#tbRemoveMaterial");
        ObservableList<Material> materialss = subjectDAO.getProfessorMaterial(2);
        int vel = materialss.size();

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        
        materialss = subjectDAO.getProfessorMaterial(2);
        assertEquals(vel-1, materialss.size());
    }


} */
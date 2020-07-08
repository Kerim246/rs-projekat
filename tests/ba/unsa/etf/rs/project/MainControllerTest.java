package ba.unsa.etf.rs.project;

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
class MainControllerTest {
    SubjectDAO subjectDAO;
    @Start
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setTitle("Aplikacija za upravljanje nastavnim materijalima");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        stage.toFront();
        subjectDAO = SubjectDAO.getInstance();
    }

    @Test
    @Order(1)
    void actAddMaterial(FxRobot robot) throws SQLException {
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Kerim");
        robot.clickOn("#fldPassword");
        robot.write("kera123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);

        robot.clickOn("#tbAddMaterial");
      //  SubjectDAO subjectDAO = new SubjectDAO();

        robot.clickOn("#fldName");
        robot.write("Predavanje12");
        robot.clickOn("#fldDate");
        robot.write("2020-08-10");
        robot.clickOn("#fldSubject");
        robot.write("Razvoj softvera");
        robot.clickOn("#fldContent");
        robot.write("Ovo je sadrzaj");

        ComboBox comboType = robot.lookup("#comboType").queryAs(ComboBox.class);

        Platform.runLater(() -> comboType.show());

        // Čekamo da se pojavi meni
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.clickOn("Predavanje");

        robot.clickOn("#btnOk");

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObservableList<Material> materials = subjectDAO.getAllMaterials();

        assertEquals("Predavanje12",materials.get(materials.size()-1).getName());


    }

    @Test
    @Order(2)
    void actEditMaterial(FxRobot robot) throws SQLException {
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Kerim");
        robot.clickOn("#fldPassword");
        robot.write("kera123");
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
        robot.clickOn("#fldSubject");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("Razvoj softvera");
        robot.clickOn("#fldContent");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("Novi sadrzaj");

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
        assertEquals("Razvoj softvera",materials.get(materials.size()-1).getSubject().getName());
        assertEquals("Tutorijal",materials.get(materials.size()-1).getType().getName());
    }

    @Test
    @Order(3)
    void actRemoveMaterial(FxRobot robot) throws SQLException {
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        robot.clickOn("#fldUsername");
        robot.write("Kerim");
        robot.clickOn("#fldPassword");
        robot.write("kera123");
        robot.clickOn("#btnLogin");
        TextField polje1 = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);
        robot.clickOn("#materialsTab");
        robot.clickOn("#tableMaterials");

        robot.clickOn("Predavanje12");

        robot.clickOn("#tbRemoveMaterial");


        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        
        ObservableList<Material> materialss = subjectDAO.getAllMaterials();
        assertEquals(2, materialss.size());
    }


}
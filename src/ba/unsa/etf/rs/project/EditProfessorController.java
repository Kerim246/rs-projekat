package ba.unsa.etf.rs.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class EditProfessorController {
    public TextField fldName;
    public TextField fldDate;
    public TextField fldSurname;
    public TextField fldPostalNumber;
    public Profesor professor;

    public boolean IspravanDatum(String datum){
       if(LocalDate.parse(datum).isBefore(LocalDate.now()) || LocalDate.parse(datum).isEqual(LocalDate.now())) // datum mora biti trenutni ili prije trenutnog
           return true;

       return false;
    }

    public EditProfessorController(Profesor professor){
        this.professor = professor;
    }

    public void initialize(){
        if(professor == null){
            fldName.getStyleClass().add("poljeNijeIspravno");
            fldDate.getStyleClass().add("poljeNijeIspravno");
            fldSurname.getStyleClass().add("poljeNijeIspravno");
            fldPostalNumber.getStyleClass().add("poljeNijeIspravno");
        }
        else {
            fldName.getStyleClass().add("poljeIspravno");
            fldDate.getStyleClass().add("poljeIspravno");
            fldSurname.getStyleClass().add("poljeIspravno");
            fldPostalNumber.getStyleClass().add("poljeIspravno");

            fldName.setText(professor.getName());
            fldDate.setText(professor.getEmployment_date().toString());
            fldSurname.setText(professor.getSurname());
            fldPostalNumber.setText(String.valueOf(professor.getPostalNumber()));
        }
        fldName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3) {
                fldName.getStyleClass().removeAll("poljeNijeIspravno");
                fldName.getStyleClass().add("poljeIspravno");
            } else {
                fldName.getStyleClass().removeAll("poljeIspravno");
                fldName.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldSurname.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3) {
                fldSurname.getStyleClass().removeAll("poljeNijeIspravno");
                fldSurname.getStyleClass().add("poljeIspravno");
            } else {
                fldSurname.getStyleClass().removeAll("poljeIspravno");
                fldSurname.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldDate.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() == 10) {  // Datum mora biti trenutni ili prije trenutnog
                if(IspravanDatum(newIme)) {
                    fldDate.getStyleClass().removeAll("poljeNijeIspravno");
                    fldDate.getStyleClass().add("poljeIspravno");
                }
            } else {
                fldDate.getStyleClass().removeAll("poljeIspravno");
                fldDate.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldSurname.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3) {
                fldSurname.getStyleClass().removeAll("poljeNijeIspravno");
                fldSurname.getStyleClass().add("poljeIspravno");
            } else {
                fldSurname.getStyleClass().removeAll("poljeIspravno");
                fldSurname.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void actOk(ActionEvent actionEvent) {
        Thread thread = new Thread(() -> {
            int postBroj = Integer.parseInt(fldPostalNumber.getText());
            try {
                URL provjera = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj="+postBroj);

                BufferedReader buffer = new BufferedReader(new InputStreamReader(provjera.openStream(), StandardCharsets.UTF_8));
                String rez = "", line = null;
                while ((line = buffer.readLine()) != null)
                    rez = rez + line;
                buffer.close();
                if(rez.equals("OK")) {
                    fldPostalNumber.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPostalNumber.getStyleClass().add("poljeIspravno");

                    Platform.runLater(() -> {
                        if (professor == null) professor = new Profesor();
                        if(fldName.getText().length() > 3 && fldSurname.getText().length() > 3 && IspravanDatum(fldDate.getText())) {
                            professor.setName(fldName.getText());
                            professor.setSurname(fldSurname.getText());
                            professor.setEmployment_date(LocalDate.parse(fldDate.getText()));
                            professor.setPostalNumber(postBroj);
                            Stage stage = (Stage) fldName.getScene().getWindow();
                            stage.close();
                        }
                    });
                } else if(rez.equals("NOT OK")){
                    fldPostalNumber.getStyleClass().removeAll("poljeIspravno");
                    fldPostalNumber.getStyleClass().add("poljeNijeIspravno");

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();

    }

    public void actCancel(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public Profesor getProfessor() {
        return professor;
    }

    public void setProfessor(Profesor professor) {
        this.professor = professor;
    }
}

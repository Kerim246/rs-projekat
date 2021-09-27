package ba.unsa.etf.rs.project;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.assertj.core.internal.ErrorMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdministratorController implements Validation {
    public TextField fieldUsername;
    public TextField fieldPassword;
    public TextField fieldName;
    public TextField fieldSurname;
    private static SubjectDAO subjectDAO;
    public TextField fieldPhoneNumber;
    public TextField fieldPostalNumber;
    public Slider sliderBirthYear;
    private Administrator a;
    public ProgressBar progressBar;
    final ProgressNumber broj = new ProgressNumber();


    public AdministratorController(Administrator a) {
        if (a == null) {
            a = new Administrator();
        }
        else this.a = a;
        subjectDAO = SubjectDAO.getInstance();
    }

    @Override
    public boolean passwordValidation(String password) {    // one lowercase, one upercase, one number, one special character
        Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(password);
        boolean specialCharacter = matcher.find();

        int pom=0;

        Pattern atleastOneNumber = Pattern.compile(".*\\d.*");
        if(atleastOneNumber.matcher(password).matches()) pom++;

        Pattern atleastOneBigLetter = Pattern.compile(".*[A-Z].*");
        if(atleastOneBigLetter.matcher(password).matches()) pom++;

        Pattern atleastoneSmallLetter = Pattern.compile(".*[a-z].*");
        if(atleastoneSmallLetter.matcher(password).matches()) pom++;

        if(specialCharacter) pom++;

        if(pom == 0) broj.setBroj(0);
        if(pom == 1) { broj.setBroj(0.25) ;}
        else if(pom==2) broj.setBroj(0.5);
        else if(pom==3) broj.setBroj(0.75);
        else if(pom==4) broj.setBroj(1);



        return textPattern.matcher(password).matches() && specialCharacter;
    }


    public static boolean phoneNumberValidation(String str){

        String regex = "[0-9]+";
        if(str.matches(regex) && str.length() > 6 && str.length() < 10) return true;
        return false;

    }


    @FXML
    public void initialize(){
        sliderBirthYear.setValue(2000);
        progressBar.progressProperty().bindBidirectional(broj.brojProperty());
        broj.setBroj(0);
        if(a == null) {
            fieldPassword.setPromptText("1 upercase,1 lowcase,1 spc,1 number");
            fieldUsername.setPromptText("");
            fieldName.setPromptText("Only letters!");
            fieldSurname.setPromptText("Only letters!");
            fieldPhoneNumber.setPromptText("Must contain numbers only");
            fieldPostalNumber.setPromptText("");

            fieldName.getStyleClass().add("poljeNijeIspravno");
            fieldPostalNumber.getStyleClass().add("poljeNijeIspravno");
            fieldSurname.getStyleClass().add("poljeNijeIspravno");
            fieldPassword.getStyleClass().add("poljeNijeIspravno");
            fieldUsername.getStyleClass().add("poljeNijeIspravno");
            fieldPhoneNumber.getStyleClass().add("poljeNijeIspravno");

        }
        else {
            fieldPassword.setText(a.getPassword());
            fieldName.setText(a.getName());
            fieldSurname.setText(a.getSurname());
            fieldPostalNumber.setText(String.valueOf(a.getPostalNumber()));
            fieldPhoneNumber.setText(String.valueOf(a.getPhone_number()));
            fieldUsername.setText(a.getUsername());

            fieldName.getStyleClass().add("poljeIspravno");
            fieldPostalNumber.getStyleClass().add("poljeIspravno");
            fieldSurname.getStyleClass().add("poljeIspravno");
            fieldPassword.getStyleClass().add("poljeIspravno");
            fieldUsername.getStyleClass().add("poljeIspravno");
            fieldPhoneNumber.getStyleClass().add("poljeIspravno");
            sliderBirthYear.valueProperty().setValue(a.getBirthYear());
            broj.setBroj(1);
        }

        progressBar.progressProperty().bindBidirectional(broj.brojProperty());

        broj.brojProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                progressBar.progressProperty().bindBidirectional(broj.brojProperty());


                //        progressBar.setProgress(broj.brojProperty().getValue());
                //     System.out.println(broj.brojProperty());
                if(broj.brojProperty().getValue() < 1) {
                    //progressBar.setStyle("-fx-accent: green; ");
                    //    setBarStyleClass(progressBar,GREEN_BAR);
                    progressBar.getStyleClass().removeAll("zeleniProgress");
                    progressBar.getStyleClass().addAll("crveniProgress");
                }
                else {
                    progressBar.getStyleClass().removeAll("crveniProgress");
                    progressBar.getStyleClass().addAll("zeleniProgress");
                }

            }
        });

        fieldName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3 && EditProfessorController.nameValidation(newIme)) {
                fieldName.getStyleClass().removeAll("poljeNijeIspravno");
                fieldName.getStyleClass().add("poljeIspravno");
            } else {
                fieldName.getStyleClass().removeAll("poljeIspravno");
                fieldName.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldSurname.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3 && EditProfessorController.nameValidation(newIme)) {
                fieldSurname.getStyleClass().removeAll("poljeNijeIspravno");
                fieldSurname.getStyleClass().add("poljeIspravno");
            } else {
                fieldSurname.getStyleClass().removeAll("poljeIspravno");
                fieldSurname.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fieldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3) {
                fieldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fieldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fieldUsername.getStyleClass().removeAll("poljeIspravno");
                fieldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fieldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if(newIme.isEmpty()) broj.setBroj(0);
            if (!newIme.isEmpty() && passwordValidation(newIme)) {
                fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fieldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fieldPassword.getStyleClass().removeAll("poljeIspravno");
                fieldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fieldPhoneNumber.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && phoneNumberValidation(newIme)) {
                fieldPhoneNumber.getStyleClass().removeAll("poljeNijeIspravno");
                fieldPhoneNumber.getStyleClass().add("poljeIspravno");
            } else {
                fieldPhoneNumber.getStyleClass().removeAll("poljeIspravno");
                fieldPhoneNumber.getStyleClass().add("poljeNijeIspravno");

            }
        });


        this.subjectDAO = SubjectDAO.getInstance();
    }

    public void actOk(ActionEvent actionEvent) {
        if (!EditProfessorController.nameValidation(fieldName.getText()) || !EditProfessorController.nameValidation(fieldSurname.getText()) || fieldName.getText().length() < 4 || fieldSurname.getText().length() < 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect name");
            alert.setHeaderText("Incorrect name!");
            alert.setContentText("Name must be atleast 3 characters long and can contain only letters and character - and must be atleast 3 characters long");

            alert.showAndWait();
        }
        if (!passwordValidation(fieldPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect password");
            alert.setHeaderText("Incorrect password!");
            alert.setContentText("Password must contain atleast one uppercase,one lowercase,one number,one special character.");

            alert.showAndWait();
        }
        if (!phoneNumberValidation(fieldPhoneNumber.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect phone number");
            alert.setHeaderText("Incorrect phone number!");
            alert.setContentText("Phone number must contain only digits and be long 6 to 10 numbers");
            alert.showAndWait();
        }
        if (EditProfessorController.isNumeric(fieldPostalNumber.getText())) {
            Thread thread = new Thread(() -> {
                int postBroj = Integer.parseInt(fieldPostalNumber.getText());
                try {
                    URL provjera = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + postBroj);       // Provjera da li je validan postanski broj

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(provjera.openStream(), StandardCharsets.UTF_8));
                    String rez = "", line = null;
                    while ((line = buffer.readLine()) != null)
                        rez = rez + line;
                    buffer.close();
                    if (rez.equals("OK")) {
                        fieldPostalNumber.getStyleClass().removeAll("poljeNijeIspravno");
                        fieldPostalNumber.getStyleClass().add("poljeIspravno");

                        Platform.runLater(() -> {
                            if (a == null) a = new Administrator();
                            if (fieldName.getText().length() > 3 && fieldSurname.getText().length() > 3 && EditProfessorController.nameValidation(fieldName.getText()) && EditProfessorController.nameValidation(fieldSurname.getText()) && fieldUsername.getText().length() > 3 && passwordValidation(fieldPassword.getText()) && phoneNumberValidation(fieldPhoneNumber.getText())) {
                                a.setName(fieldName.getText());
                                a.setSurname(fieldSurname.getText());
                                a.setPostalNumber(postBroj);
                                a.setPhone_number(Integer.parseInt(fieldPhoneNumber.getText()));
                                a.setUsername(fieldUsername.getText());
                                a.setPassword(fieldPassword.getText());
                                a.setBirthYear((int) sliderBirthYear.getValue());
                                actCancel(actionEvent);
                            }
                        });
                    } else if (rez.equals("NOT OK")) {
                        fieldPostalNumber.getStyleClass().removeAll("poljeIspravno");
                        fieldPostalNumber.getStyleClass().add("poljeNijeIspravno");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Incorrect postal number");
                        alert.setHeaderText("Incorrect postal number!");
                        alert.setContentText("Please enter valid postal number!");
                        alert.showAndWait();

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            thread.start();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect postal number");
            alert.setHeaderText("Incorrect postal number!");
            alert.setContentText("Please enter valid postal number!");
            alert.showAndWait();
        }
    }

    public void actCancel(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public Administrator getA() {
        return a;
    }

    public void setA(Administrator a) {
        this.a = a;
    }
}

package ba.unsa.etf.rs.project;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class EditProfessorController implements Validation{
    public TextField fldName;
    public DatePicker fldDate;
    public TextField fldSurname;
    public TextField fldPostalNumber;
    public TextField fldUsername;
    public TextField fldPassword;
    public TextField fldPhoneNumber;
    public Profesor professor;
    public ChoiceBox<String> choiceSubject;
    public Account account;
    public SubjectDAO dao;
    public Slider sliderGodinaRodjenja;
    public ProgressBar prgBar;
    final ProgressNumber broj = new ProgressNumber();


    private ObservableList<Subject> allSubjects = FXCollections.observableArrayList();
    private ObservableList<String> allSubjectsName = FXCollections.observableArrayList();
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-mm-yyyy");
    boolean datumIspravan = true;
    private String newSubject ="";

    public EditProfessorController(Profesor professor){
        this.professor = professor;
        dao = SubjectDAO.getInstance();
    }

    public boolean dateValidation(String datum){
      LocalDate d = LocalDate.parse(datum);

        if(d.isBefore(LocalDate.now()) || d.isEqual(LocalDate.now())) // datum mora biti trenutni ili prije trenutnog
           return true;

       return false;
    }

    @Override
    public boolean passwordValidation(String pass){   // one upercase, one lowercase, one number
        // String password = this.getPassword();

        boolean prviUslov = false;
        boolean drugiUslov = false;
        boolean treciUslov = false;
        //      System.out.println(pass);

        // System.out.println("Sifra je "+pass);

        for(int i=0 ; i<pass.length() ; i++){
            if(pass.charAt(i) >= 'A' && pass.charAt(i) <='Z'){
                prviUslov = true;
            }
            if(pass.charAt(i) >= 'a' && pass.charAt(i) <='z'){
                drugiUslov = true;
            }
            if(pass.charAt(i) >= '0' && pass.charAt(i) <='9'){
                treciUslov = true;
            }
        }

        int pom=0;

        Pattern atleastOneNumber = Pattern.compile(".*\\d.*");
        if(atleastOneNumber.matcher(pass).matches()) pom++;

        Pattern atleastOneBigLetter = Pattern.compile(".*[A-Z].*");
        if(atleastOneBigLetter.matcher(pass).matches()) pom++;

        Pattern atleastoneSmallLetter = Pattern.compile(".*[a-z].*");
        if(atleastoneSmallLetter.matcher(pass).matches()) pom++;

        if(pom == 0) broj.setBroj(0);
        if(pom == 1) { broj.setBroj(0.33333) ;}
        else if(pom==2) broj.setBroj(0.6666666666666667);
        else if(pom==3) broj.setBroj(1);

        //  System.out.println("Prvi "+ prviUslov + "Drgi "+ drugiUslov + "Treci " + treciUslov);

        if(prviUslov == false || drugiUslov == false || treciUslov == false) return false;

        return true;

    }


    public static boolean nameValidation(String ime){
        if(ime.length() < 3) return false;
        char c;
        //    boolean velikoSL = false;
        for(int i=0 ; i<ime.length() ; i++){
            c = ime.charAt(i);
            if(!(Character.isUpperCase(c) || Character.isLowerCase(c) || c == '-' || c == ' ')){
                return false;
            }
        }
        return true;
    }

    public void initialize() throws SQLException {
        sliderGodinaRodjenja.setValue(2000);
        allSubjects = dao.getAllSubjects();
        int i=0;
        while(i<allSubjects.size()){
            allSubjectsName.add(allSubjects.get(i).getName());
            i++;
        }
        choiceSubject.setItems(allSubjectsName);

        if(professor == null){
            fldName.getStyleClass().add("poljeNijeIspravno");
            fldDate.setValue(LocalDate.now());
            fldSurname.getStyleClass().add("poljeNijeIspravno");
            fldPostalNumber.getStyleClass().add("poljeNijeIspravno");
            fldUsername.getStyleClass().add("poljeNijeIspravno");
            fldPassword.getStyleClass().add("poljeNijeIspravno");
            fldPhoneNumber.getStyleClass().add("poljeNijeIspravno");

            fldName.setPromptText("Only letters!");
            fldSurname.setPromptText("Only letters!");
            fldPassword.setPromptText("1 upcase,1 lowcase,1 number");
            fldPhoneNumber.setPromptText("only digits");
        }
        else {
            fldName.getStyleClass().add("poljeIspravno");
            fldDate.getStyleClass().add("poljeIspravno");
            fldSurname.getStyleClass().add("poljeIspravno");
            fldPostalNumber.getStyleClass().add("poljeIspravno");
            fldUsername.getStyleClass().add("poljeIspravno");
            fldPassword.getStyleClass().add("poljeIspravno");
            fldPhoneNumber.getStyleClass().add("poljeIspravno");


            fldName.setText(professor.getName());
            fldDate.setValue(professor.getEmployment_date());
            fldSurname.setText(professor.getSurname());
            fldPostalNumber.setText(String.valueOf(professor.getPostalNumber()));
            fldUsername.setText(dao.getAccount(professor.getId()).getUsername());
            fldPassword.setText(dao.getAccount(professor.getId()).getPassword());
            choiceSubject.setValue(dao.getSubjectFProfessor(professor));
            fldPhoneNumber.setText(String.valueOf(professor.getPhone_number()));
            sliderGodinaRodjenja.valueProperty().setValue(professor.getBirthYear());
            broj.setBroj(1);


        }

        prgBar.progressProperty().bindBidirectional(broj.brojProperty());

        broj.brojProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                prgBar.progressProperty().bindBidirectional(broj.brojProperty());


                //        progressBar.setProgress(broj.brojProperty().getValue());
                //     System.out.println(broj.brojProperty());
                if(broj.brojProperty().getValue() < 1) {
                    //progressBar.setStyle("-fx-accent: green; ");
                    //    setBarStyleClass(progressBar,GREEN_BAR);
                    prgBar.getStyleClass().removeAll("zeleniProgress");
                    prgBar.getStyleClass().addAll("crveniProgress");
                }
                else {
                    prgBar.getStyleClass().removeAll("crveniProgress");
                    prgBar.getStyleClass().addAll("zeleniProgress");
                }

            }
        });

        fldName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3 && nameValidation(newIme)) {
                fldName.getStyleClass().removeAll("poljeNijeIspravno");
                fldName.getStyleClass().add("poljeIspravno");
            } else {
                fldName.getStyleClass().removeAll("poljeIspravno");
                fldName.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldSurname.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3 && nameValidation(newIme)) {
                fldSurname.getStyleClass().removeAll("poljeNijeIspravno");
                fldSurname.getStyleClass().add("poljeIspravno");
            } else {
                fldSurname.getStyleClass().removeAll("poljeIspravno");
                fldSurname.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                if (t1 == null || t1.isAfter(LocalDate.now())) {
                    fldDate.getEditor().getStyleClass().removeAll("poljeIspravno");
                    fldDate.getEditor().getStyleClass().add("poljeNijeIspravno");
                    datumIspravan = false;

                } else {
                    fldDate.getEditor().getStyleClass().removeAll("poljeNijeIspravno");
                    fldDate.getEditor().getStyleClass().add("poljeIspravno");
                    datumIspravan = true;
                }
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

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() > 3) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if(newIme.isEmpty()) broj.setBroj(0);
            if (!newIme.isEmpty() && passwordValidation(newIme)) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPhoneNumber.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && AdministratorController.phoneNumberValidation(newIme)) {
                fldPhoneNumber.getStyleClass().removeAll("poljeNijeIspravno");
                fldPhoneNumber.getStyleClass().add("poljeIspravno");
            } else {
                fldPhoneNumber.getStyleClass().removeAll("poljeIspravno");
                fldPhoneNumber.getStyleClass().add("poljeNijeIspravno");
            }
        });

        choiceSubject.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.isEmpty()) {
                    newSubject = t1;
                }
            }
        });
    }

    public static boolean isNumeric(String strNum){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public void actOk(ActionEvent actionEvent) {
        if (!nameValidation(fldName.getText()) || !nameValidation(fldSurname.getText()) || fldName.getText().length() < 4 || fldSurname.getText().length() < 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect name");
            alert.setHeaderText("Incorrect name!");
            alert.setContentText("Name must be atleast 3 characters long and can contain only letters and character - and must be atleast 3 characters long");

            alert.showAndWait();
        }
        if (!passwordValidation(fldPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect password");
            alert.setHeaderText("Incorrect password!");
            alert.setContentText("Password must contain atleast one uppercase,one lowercase,one number.");

            alert.showAndWait();
        }
        if (!AdministratorController.phoneNumberValidation(fldPhoneNumber.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect phone number");
            alert.setHeaderText("Incorrect phone number!");
            alert.setContentText("Phone number must contain only digits and be long 6 to 10 numbers");
            alert.showAndWait();
        }
        if (choiceSubject.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect subject");
            alert.setHeaderText("Incorrect subject!");
            alert.setContentText("Please choose a subject");
            alert.showAndWait();
        }
        if (isNumeric(fldPostalNumber.getText())) {
            Thread thread = new Thread(() -> {
                int postBroj = Integer.parseInt(fldPostalNumber.getText());
                try {
                    URL provjera = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + postBroj);       // Provjera da li je validan postanski broj

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(provjera.openStream(), StandardCharsets.UTF_8));
                    String rez = "", line = null;
                    while ((line = buffer.readLine()) != null)
                        rez = rez + line;
                    buffer.close();
                    if (rez.equals("OK")) {
                        fldPostalNumber.getStyleClass().removeAll("poljeNijeIspravno");
                        fldPostalNumber.getStyleClass().add("poljeIspravno");

                        Platform.runLater(() -> {
                            if (professor == null) professor = new Profesor();
                            if (fldName.getText().length() > 3 && fldSurname.getText().length() > 3 && nameValidation(fldName.getText()) && nameValidation(fldSurname.getText()) && datumIspravan && fldUsername.getText().length() > 3 && passwordValidation(fldPassword.getText()) && choiceSubject.getValue() != null) {
                                professor.setName(fldName.getText());
                                professor.setSurname(fldSurname.getText());
                                professor.setEmployment_date(fldDate.getValue());
                                professor.setPostalNumber(postBroj);
                                professor.setPhone_number(Integer.parseInt(fldPhoneNumber.getText()));
                                professor.setBirthYear((int) sliderGodinaRodjenja.getValue());
                                try {
                                    professor.setSubject(dao.findSubjectName(newSubject));
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                account = new Account(fldUsername.getText(), fldPassword.getText(), professor);
                                actCancel(actionEvent);
                            }
                        });
                    } else if (rez.equals("NOT OK")) {
                        fldPostalNumber.getStyleClass().removeAll("poljeIspravno");
                        fldPostalNumber.getStyleClass().add("poljeNijeIspravno");
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

    public Profesor getProfessor() {
        return professor;
    }

    public void setProfessor(Profesor professor) {
        this.professor = professor;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

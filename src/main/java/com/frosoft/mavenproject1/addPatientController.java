package com.frosoft.mavenproject1;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class addPatientController implements Initializable {
    public TextField tfName;
    public TextField tfContact;
    public TextField tfMRN;
    public void add(ActionEvent actionEvent) throws SQLException {
        DatabaseConnection dbCon = new DatabaseConnection();
        Connection s = dbCon.getConnection();
        PreparedStatement prep = s.prepareStatement("INSERT INTO patients VALUES("+tfMRN.getText()+",'"+tfName.getText()+"','"+tfContact.getText()+"')");
        prep.execute();
        prep = s.prepareStatement("CREATE TABLE report"+tfName.getText().toLowerCase(Locale.ROOT)+" LIKE report");
        prep.execute();
        prep = s.prepareStatement("CREATE TABLE bill"+tfName.getText().toLowerCase(Locale.ROOT)+" LIKE bill");
        prep.execute();
        PatientListController.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfMRN.setText(""+(PatientListController.mrn+1));
    }
}

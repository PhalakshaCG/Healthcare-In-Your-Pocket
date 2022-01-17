/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frosoft.mavenproject1;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author Phalaksha C G
 */
public class Secondary_staffController implements Initializable {


    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ImageView bg,ivPatient,ivLab,ivDoctor;
    @FXML
    private Button bPatient,bDoctor,bLab;
    @FXML
    private Button bInfo;
    @FXML
    public Button bLogout;
    /**
     * Initializes the controller class.
     */
    String s;
    @FXML
    private void logout(){
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView ivLogout = new ImageView();
        File n = new File("D:\\RVCE\\JAVA\\Doccount\\media\\logout.png");
        Image img = new Image(n.toURI().toString());
        ivLogout.setImage(img);
        ivLogout.setFitHeight(20);
        ivLogout.setFitWidth(20);
        bLogout.setGraphic(ivLogout);
        File f4= new File("D:\\RVCE\\JAVA\\Doccount\\media\\bg.jpg");
        Image i4=new Image(f4.toURI().toString());
        bg.setImage(i4);// TODO
        File f3= new File("D:\\RVCE\\JAVA\\Doccount\\media\\patient.png");
        Image i3=new Image(f3.toURI().toString());
        ivPatient.setImage(i3);
        File f2= new File("D:\\RVCE\\JAVA\\Doccount\\media\\doctor.png");
        Image i2=new Image(f2.toURI().toString());
        ivDoctor.setImage(i2);
        //File f1= new File("D:\\RVCE\\JAVA\\Doccount\\media\\lab.png");
        //Image i1=new Image(f1.toURI().toString());
        //ivLab.setImage(i1);

    }    
    @FXML
    public void doctor(){
        try {
            App.setRoot("doctorsList");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void patientList(ActionEvent event) throws IOException {
        try {
            App.setRoot("patientList");
           // App.setRoot("Reports");
            
        } catch (IOException ex) {
            Logger.getLogger(Secondary_staffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

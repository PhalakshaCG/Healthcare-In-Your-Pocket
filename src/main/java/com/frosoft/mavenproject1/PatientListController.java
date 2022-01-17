/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frosoft.mavenproject1;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Phalaksha C G
 */
public class PatientListController implements Initializable {


    @FXML
    private ImageView bgP;
    @FXML
    private TableView<Patient> pTable;
    @FXML
    private TableColumn<Patient,Integer> cMRN;
    @FXML
    private TableColumn<Patient,String> cName;
    
    @FXML
    private TableColumn<Patient,String> cContact;
    @FXML
    private TableColumn<Patient,Button> cReport;
    @FXML
            private TableColumn<Patient,Button> cBill;
    int count=10;
    @FXML
    Button bLogout;
    @FXML
    void logout(){
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void back(){
        try {
            App.setRoot("secondary_staff");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Initializes the controller class.
     */
    DatabaseConnection dbPatient;
    Button[] report = new Button[count];
    Button[] bills = new Button[count];
    static int mrn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView ivLogout = new ImageView();
        File n = new File("D:\\RVCE\\JAVA\\Doccount\\media\\logout.png");
        Image img = new Image(n.toURI().toString());
        ivLogout.setImage(img);
        ivLogout.setFitHeight(20);
        ivLogout.setFitWidth(20);
        bLogout.setGraphic(ivLogout);
        File f1= new File("D:\\RVCE\\JAVA\\Doccount\\media\\bg3.jpg");
        Image i1=new Image(f1.toURI().toString());
        bgP.setImage(i1);// TODO
        dbPatient= new DatabaseConnection();
        cMRN.setCellValueFactory(new PropertyValueFactory<>("MRN_Number"));
        cContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        cName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        cReport.setCellValueFactory(new PropertyValueFactory<>("Report"));
        cBill.setCellValueFactory(new PropertyValueFactory<>("Bill"));
        refresh();
    }
    public void refresh(){
        ObservableList<Patient> e = null;
        try {
            e = getPatients();
        } catch (SQLException ex) {
            Logger.getLogger(PatientListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pTable.setItems(e);
    }
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try{
                    Button b = (Button)e.getSource();
                    String num = b.getText().substring(7);
                    ReportsController.setID(Integer.parseInt(num));
                    App.setRoot("Reports");
            } catch (IOException ex) {
                Logger.getLogger(PatientListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        };
    EventHandler<ActionEvent> bill = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            try{
                Button b = (Button)e.getSource();
                String num = b.getText().substring(5);
                BillController.setID(Integer.parseInt(num));
                App.setRoot("Bill");
            } catch (IOException ex) {
                Logger.getLogger(PatientListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    public ObservableList<Patient> getPatients() throws SQLException{
        ObservableList<Patient> pat = FXCollections.observableArrayList();
            Connection s = dbPatient.getConnection();
            Statement stmt = s.createStatement();
            ResultSet aa = stmt.executeQuery("SELECT * FROM patients");
            //System.out.print(aa);
            count=0;
            while(aa.next()){
                count++;
            }
            
            aa = stmt.executeQuery("SELECT * FROM patients");
            System.out.print(aa);
            int i=0;
            while(aa.next()){
             mrn=aa.getInt("MRN_Number");
            report[i]=new Button("Report-"+mrn);
            bills[i]=new Button("Bill-"+mrn);
            report[i].setOnAction(event);
            bills[i].setOnAction(bill);
            pat.add(new Patient(mrn,aa.getString("Name"),report[i],aa.getString("Contact"),bills[i]));
            i++;
            }
            return pat;
        
    }
    public static Stage s = new Stage();
    @FXML
    public void add(){
           // App a = new App();
       try {
                Scene scene = new Scene(new FXMLLoader(App.class.getResource("addPatient" + ".fxml")).load(), 440, 280);
                s.setScene(scene);
                File n = new File("D:\\RVCE\\JAVA\\Doccount\\media\\logo.jpg");
                Image i = new Image(n.toURI().toString());
                s.getIcons().add(i);
                s.setTitle("Add Patient");
                s.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void remove(){
        int a = pTable.getSelectionModel().getFocusedIndex();
        int i = cMRN.getCellData(a);
        String name = cName.getCellData(a);
        Connection s = dbPatient.getConnection();
        try {
            PreparedStatement prep = s.prepareStatement("DELETE FROM patients WHERE MRN_Number = "+i);
            prep.execute();
            prep = s.prepareStatement("DROP TABLE report"+name.toLowerCase(Locale.ROOT));
            prep.execute();
            prep = s.prepareStatement("DROP TABLE bill"+name.toLowerCase(Locale.ROOT));
            prep.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        refresh();
    }
    public static void exit(){
        s.close();
        try {
            App.setRoot("patientList");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

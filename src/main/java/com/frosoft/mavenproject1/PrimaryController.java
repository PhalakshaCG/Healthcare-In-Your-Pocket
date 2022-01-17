package com.frosoft.mavenproject1;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import java.net.URL;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
public class PrimaryController implements Initializable{
    //@FXML public ImageView loginImage;
    //@FXML public AnchorPane ap;
    DatabaseConnection dbhospital = new DatabaseConnection();
    @FXML public Button login,bsignUp,bAdmin;
    @FXML AnchorPane Admin;
    @FXML TextField adminPassword;
    @FXML private ImageView iv;
    @FXML TextField tfUsername,tfPassword;
    @FXML CheckBox cpatient,cstaff;
    boolean patient, staff,type;
    String username,password;
    static String patientName;
    @FXML
    private void switchToSecondary() throws IOException {
        try{
            Connection s = dbhospital.getConnection();
            Statement stmt = s.createStatement();
//            PreparedStatement preparedStmt = s.prepareStatement("INSERT INTO credentials VALUES('PRANAV','password')");
//            preparedStmt.execute();
            username=tfUsername.getText();
            password=tfPassword.getText();

            if(cpatient.isSelected())
                type=false;
            if(cstaff.isSelected())
                type=true;
            ResultSet aa = stmt.executeQuery("SELECT * FROM credentials WHERE Username = '"+username+"' AND Password = '"+password+"' AND Type = "+type);
            if(aa.next()){
                if(!type){
            App.setRoot("secondary");
            patientName = tfUsername.getText();
            }
                if(type){
            App.setRoot("secondary_staff");
            }
            }
            else{
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                //alert.setContentText("Invalid username or password");
                alert.setHeaderText("Invalid username or password");
                alert.showAndWait();
            
            }
        }
        catch(Exception e){
        e.printStackTrace();
        }
    }
    
    @FXML
    private void signUp(){
        Admin.setOpacity(1);
    }
    @FXML
    private void signUpProcessing(){
        try{
        Connection s = dbhospital.getConnection();
        username=tfUsername.getText();
        password=tfPassword.getText();
        if(password.equals("")||username.equals("")){
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                //alert.setContentText("Invalid username or password");
                alert.setHeaderText("Both fields must be filled");
                alert.showAndWait();
                Admin.setOpacity(0);
                return;
        }
        Authentication a;
            a = ()->{
                if(adminPassword.getText().equals("OhioSenpai")){
                    Admin.setOpacity(0);
                    return true;
                }
                else{
                    Admin.setOpacity(0);
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR);
                //alert.setContentText("Invalid username or password");
                    alert.setHeaderText("Contact admin for registration");
                    alert.showAndWait();
                    Admin.setOpacity(0);
                    return false;
                }
            };
            if(!a.admin()){
                adminPassword.clear();
                return;
            }
            if(cpatient.isSelected())
                type=false;
            if(cstaff.isSelected())
                type=true;
        PreparedStatement preparedStmt = s.prepareStatement("INSERT INTO credentials VALUES('"+username+"','"+password+"',"+type+")");
        if(!preparedStmt.execute()){
                Alert alert;
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                //alert.setContentText("Sign Up Successful");
                alert.setHeaderText("Sign Up Successful");
                alert.showAndWait();
        }
        else{
            Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                //alert.setContentText("Usernam");
                alert.setHeaderText("Username exists");
                alert.showAndWait();
        }
    }catch(Exception e){}
       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //dbhospital.execAction("INSERT INTO CREDENTIALS VALUES('Phalaksha','password')");
        File file = new File("D:\\RVCE\\JAVA\\Doccount\\media\\adobestock_873300111-removebg-preview.png");
        Image i = new Image(file.toURI().toString());        
        iv.setImage(i);
        
    }
    @FXML
    void patient_sel(){
        cstaff.setSelected(false);
        cpatient.setSelected(true);
    }
    @FXML
    void staff_sel(){
        cpatient.setSelected(false);
        cstaff.setSelected(true);
    }
}
interface Authentication{
    abstract boolean admin();
}

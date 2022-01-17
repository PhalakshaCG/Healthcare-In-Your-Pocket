package com.frosoft.mavenproject1;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SecondaryController implements Initializable{
    @FXML Button bBill,bLogout,bReport;
    @FXML ImageView  ivinsurance,bg;
    @FXML TextField tfSearch;
    @FXML
    ImageView ivReport;
    @FXML
    private void hospitalInfo() throws IOException {
        App.setRoot("Hospital");
    }
    @FXML
    private void bill() throws IOException {
        BillController.patient=true;
        App.setRoot("Bill");
    }
    @FXML
    private void report() throws IOException {
        ReportsController.patient=true;
        App.setRoot("Reports");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File f3= new File("D:\\RVCE\\JAVA\\Doccount\\media\\Insurance.png");
        File f4= new File("D:\\RVCE\\JAVA\\Doccount\\media\\bg.jpg");
        File f5 = new File("D:\\RVCE\\JAVA\\Doccount\\media\\Capture-removebg-preview (2).png");
        Image i3=new Image(f3.toURI().toString());
        Image i4=new Image(f4.toURI().toString());
        Image i5 = new Image(f5.toURI().toString());
        ivinsurance.setImage(i3);
        bg.setImage(i4);
        ivReport.setImage(i5);
        ImageView ivLogout = new ImageView();
        File n = new File("D:\\RVCE\\JAVA\\Doccount\\media\\logout.png");
        Image img = new Image(n.toURI().toString());
        ivLogout.setImage(img);
        ivLogout.setFitHeight(20);
        ivLogout.setFitWidth(20);
        bLogout.setGraphic(ivLogout);
    }
    public void logout(){
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
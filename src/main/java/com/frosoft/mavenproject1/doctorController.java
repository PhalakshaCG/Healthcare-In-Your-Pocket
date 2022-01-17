/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frosoft.mavenproject1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import javax.print.Doc;

/**
 * FXML Controller class
 *
 * @author Phalaksha C G
 */
public class doctorController implements Initializable {


    @FXML
    private ImageView bgP;
    @FXML
    private TableView<Doctor> pTable;
    @FXML
    private TableColumn<Doctor,String> cMRN;
    @FXML
    private TableColumn<Doctor,String> cName;

    @FXML
    private TableColumn<Doctor,String> cContact;
    @FXML
    private TableColumn<Doctor,Image> cReport;
    @FXML
    private TableColumn<Doctor,Integer> cBill;
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
        cMRN.setCellValueFactory(new PropertyValueFactory<>("Specialization"));
        cContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        cName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        cReport.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(100);
            imageview.setFitWidth(100);
            //Set up the Table
            TableCell<Doctor, Image> cell = new TableCell<Doctor, Image>() {
                public void updateItem(Image item, boolean empty) {
                        imageview.setImage(item);
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });
        cReport.setCellValueFactory(new PropertyValueFactory<Doctor, Image>("image"));
        cBill.setCellValueFactory(new PropertyValueFactory<>("Fee"));
        refresh();
    }
    public void refresh(){
        //pTable.getItems().clear();
        ObservableList<Doctor> e = null;
        try {
            e = getDoctors();
        } catch (SQLException ex) {
            Logger.getLogger(doctorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pTable.setItems(e);
    }
    public Image download( String fileName){
        File file;
        MongoClient client = null;
        client = MongoClients.create("mongodb+srv://phalaksha:Phallu*30101@cluster0.rahp2.mongodb.net/MyFirstDatabase?retryWrites=true&w=majority");
        GridFSBucket bucket = GridFSBuckets.create(client.getDatabase("Doctors"));
        FileOutputStream fileOutputStream = null;
        Image i4=null;
        try {
            fileOutputStream = new FileOutputStream("D:\\RVCE\\JAVA\\Doccount\\media\\DocDownload\\" + fileName);
            bucket.downloadToStream(fileName, fileOutputStream);
            fileOutputStream.close();
            file = new File("D:\\RVCE\\JAVA\\Doccount\\media\\DocDownload\\" + fileName);
            i4 = new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i4;
    }
    public ObservableList<Doctor> getDoctors() throws SQLException{
        ObservableList<Doctor> pat = FXCollections.observableArrayList();
        Connection s = dbPatient.getConnection();
        Statement stmt = s.createStatement();
        ResultSet aa = stmt.executeQuery("SELECT * FROM doctors");
        //System.out.print(aa);
        count=0;
        while(aa.next()){
            count++;
        }
        aa = stmt.executeQuery("SELECT * FROM doctors");
        System.out.print(aa);
        while(aa.next()){
            String name = aa.getString("Name");
            pat.add(new Doctor(name,aa.getString("Specialization"),aa.getString("Contact"),aa.getInt("Fee"),download(name+".jpg")));
        }
        return pat;
    }
    public static Stage s = new Stage();
    @FXML
    public void add(){
        try {

            Scene scene = new Scene(new FXMLLoader(App.class.getResource("addDoctor" + ".fxml")).load(), 440, 280);
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
        //int i = cMRN.getCellData(a);
        String name = cName.getCellData(a);
        Connection s = dbPatient.getConnection();
        try {
            PreparedStatement prep = s.prepareStatement("DELETE FROM doctors WHERE Name = '"+name+"'");
            prep.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        refresh();
    }
    public static void exit(){
        s.close();
    }

}

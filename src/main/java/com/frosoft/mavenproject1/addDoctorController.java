package com.frosoft.mavenproject1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class addDoctorController{
    public TextField tfName;
    public TextField tfContact;
    public TextField tfMRN;
    public TextField tfFee;
    public void add(ActionEvent actionEvent) throws SQLException {
        DatabaseConnection dbCon = new DatabaseConnection();
        Connection s = dbCon.getConnection();
        FileChooser f = new FileChooser();
        f.setInitialDirectory(new File("D:\\RVCE\\JAVA\\Doccount\\media\\Doctors"));
        File file =  f.showOpenDialog(new Stage());
        upload(file.getPath(), tfName.getText()+".jpg");
        PreparedStatement prep = s.prepareStatement("INSERT INTO doctors VALUES('"+tfName.getText()+"',"+tfFee.getText()+",'"+tfContact.getText()+"','"+tfMRN.getText()+"')");
        prep.execute();
        doctorController.exit();
    }
    public ObjectId upload(String filePath, String fileName) {
        ObjectId fileId = null;
        MongoClient mongo = null;
        try {
            mongo = MongoClients.create("mongodb+srv://phalaksha:Phallu*30101@cluster0.rahp2.mongodb.net/MyFirstDatabase?retryWrites=true&w=majority");
            MongoDatabase imgDb;
            imgDb = mongo.getDatabase("Doctors");
            //Create a gridFSBucket
            GridFSBucket gridBucket = GridFSBuckets.create(imgDb);
            InputStream inStream = new FileInputStream(new File(filePath));
            // Create some customize options for the details that describes
            // the uploaded image
            GridFSUploadOptions a = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(new Document("type", "image").append("content_type", "image/png"));
            fileId = gridBucket.uploadFromStream(fileName, inStream,a);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongo.close();
        }
        return fileId;
    }
}

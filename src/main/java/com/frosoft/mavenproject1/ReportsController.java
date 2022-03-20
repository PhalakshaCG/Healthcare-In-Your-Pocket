/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frosoft.mavenproject1;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import org.apache.commons.codec.binary.Base64;
import com.mongodb.client.*;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;

/**
 * FXML Controller class
 *
 * @author Phalaksha C G
 */
public class ReportsController implements Initializable {
    @FXML
    private ImageView bg;
    @FXML
    private ImageView ivReport;
    @FXML
    private TableView<Report> tReport;
    @FXML
    private TableColumn<Report, Integer> cSLNo;
    @FXML
    private TableColumn<Report, String> cReport;
    @FXML
    private AnchorPane apKey,apKeyPrompt ;
    @FXML
    private Button bView;
    @FXML
    private Button bAdd;
    @FXML
    public TextField tfKey;
    @FXML
    public Text tKey;
    @FXML
    static ImageView iv;
    @FXML
    AnchorPane apPane;
    @FXML
    Button bBack;
    @FXML
    Button bLogout;
    static boolean patient = false;
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
        if(!patient) {
            try {
                App.setRoot("patientList");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                App.setRoot("secondary");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static byte[] data;
    static String image = "";
    static int id;
    int slno;
    String name;
    DatabaseConnection dbhospital = new DatabaseConnection();
    ImageView ivNew = new ImageView();
    FileChooser fileChooser = new FileChooser();
    /**
     * Initializes the controller class
     */
    File file;
    public ObservableList<Report> getReport() throws SQLException {

        ObservableList<Report> reports = FXCollections.observableArrayList();
        Connection s = dbhospital.getConnection();
        Statement stmt = s.createStatement();
        ResultSet patient = stmt.executeQuery("SELECT * FROM patients WHERE MRN_Number = "+id);
        patient.next();
        name = patient.getString("Name");
        ResultSet aa = stmt.executeQuery("SELECT * FROM report"+name.toLowerCase());
        System.out.print(aa);
        while(aa.next()){
            reports.add(new Report(slno=aa.getInt("SLNo"),aa.getString("Name")));
        }
        slno++;
        return reports;
    }
    static void setID(int id) {
        ReportsController.id = id;
    }

     @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ivNew.setImage(download("bg.jpg"));
        //apPane.getChildren().add(1, ivNew);
         if(patient){
             bAdd.setOpacity(0);
             name = PrimaryController.patientName;
             try {
                 Statement s = dbhospital.getConnection().createStatement();
                 ResultSet set = s.executeQuery("SELECT * FROM patients WHERE Name ='"+PrimaryController.patientName+"'");
                 set.next();
                 id = set.getInt("MRN_Number");
             } catch (SQLException throwables) {
                 throwables.printStackTrace();
             }
         }
        System.out.println(id);
         File a = new File("D:\\RVCE\\JAVA\\Doccount\\media\\bg3.jpg");
         Image bgi = new Image(a.toURI().toString());
         bg.setImage(bgi);
        fileChooser.setInitialDirectory(new File("D:\\RVCE\\JAVA\\Doccount\\media\\UploadDummy"));
        cSLNo.setCellValueFactory(new PropertyValueFactory<>("SLNo"));
        cReport.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ivNew.setFitWidth(280);
        ivNew.setFitHeight(250);
        ivNew.setLayoutX(402);
        ivNew.setLayoutY(165);
        ImageView ivLogout = new ImageView();
        File n = new File("D:\\RVCE\\JAVA\\Doccount\\media\\logout.png");
        Image img = new Image(n.toURI().toString());
        ivLogout.setImage(img);
        ivLogout.setFitHeight(20);
        ivLogout.setFitWidth(20);
        bLogout.setGraphic(ivLogout);
        apPane.getChildren().add(4,ivNew);
         slno = id*10;
        try {
            ObservableList<Report> r = getReport();
            tReport.setItems(r);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public int key;
    @FXML
    public void close(){
        apKey.setOpacity(0);
        apKeyPrompt.setOpacity(0);
    }
    public Image download( String fileName){
        MongoClient client = null;
        String ID = "" + id;
        client = MongoClients.create("mongodb+srv://phalaksha:Phallu*30101@cluster0.rahp2.mongodb.net/MyFirstDatabase?retryWrites=true&w=majority");
        GridFSBucket bucket = GridFSBuckets.create(client.getDatabase(ID));
        FileOutputStream fileOutputStream = null;
        Image i4=null;
        try {
            fileOutputStream = new FileOutputStream("D:\\RVCE\\JAVA\\Doccount\\media\\Reports\\" + fileName);
            bucket.downloadToStream(fileName, fileOutputStream);
            fileOutputStream.close();
            file = new File("D:\\RVCE\\JAVA\\Doccount\\media\\Reports\\" + fileName);
            i4 = new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i4;
    }
    void refresh(){
        ObservableList<Report> e = null;
        try {
            e = getReport();

        tReport.setItems(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    ObjectId getObjectID(String s){
        Bson filter = new Document();//"String","filename");
        Document filter1 = new Document();//"String",s);
        MongoClient mongoClient = MongoClients.create("mongodb+srv://phalaksha:Phallu*30101@cluster0.rahp2.mongodb.net/MyFirstDatabase?retryWrites=true&w=majority");
            MongoDatabase database = mongoClient.getDatabase(""+id);
            MongoCollection<Document> n = database.getCollection("fs.files");
            FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions();
            findOneAndUpdateOptions.projection(Projections.include("_id"));
        return n.findOneAndUpdate(filter, filter1, findOneAndUpdateOptions).getObjectId("_id");
        }

     File encodeImage() {
        FileInputStream imageInFile = null;
        try {
            imageInFile = new FileInputStream(file);
            byte[] imageByteArray = new byte[(int) file.length()];
            imageInFile.read(imageByteArray);
        byte[] a = new byte[imageByteArray.length];
            System.arraycopy(imageByteArray, key, a, 0, imageByteArray.length -key);
        a = Base64.encodeBase64(a);
            System.arraycopy(a, 0, imageByteArray, key, imageByteArray.length - key);
            file = write(imageByteArray, "encoded"+file.getName());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public File decodeImage() {
        FileInputStream imageInFile = null;
        try {
            imageInFile = new FileInputStream(file);
            byte[] imageByteArray = new byte[(int) file.length()];
            imageInFile.read(imageByteArray);
        byte[] a = new byte[imageByteArray.length-key];
            System.arraycopy(imageByteArray, key, a, 0, imageByteArray.length - key);
            System.out.println("\n"+a.length);
            a = Base64.decodeBase64(a);
        System.out.println(+a.length);
            System.arraycopy(a, 0, imageByteArray, key, a.length);
        file = write(imageByteArray, file.getName().substring(7));
        return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    File write(byte[] bytes,String fileName)
    {
        file = new File("D:\\RVCE\\JAVA\\Doccount\\media\\Reports\\" + fileName);
        try {
            // Initialize a pointer
            // in file using OutputStream
            OutputStream os = new FileOutputStream(file);
            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully"+ " byte inserted");
            // Close the file
            os.close();
        }

        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return  file;
    }

    @FXML
    public void closeKey(){
        key = Integer.getInteger(tfKey.getText());
        System.out.println(key);
        //addAct();
    }
    public void add() {
        file = fileChooser.showOpenDialog(new Stage());
        try {
            if (file != null) {
                String n;
                // Converting Image byte array into Base64 String
                key = new Random().nextInt();
                key= key%100+350;
                file = encodeImage();
                upload(file.getPath(), file.getName());
                Image image1=new Image(file.toURI().toString());
                ivReport.setImage(image1);
                ivNew.setImage(image1);
                Connection s = dbhospital.getConnection();
                PreparedStatement patient = s.prepareStatement("INSERT INTO report"+name+" VALUES("+slno+",'"+file.getName().substring(7)+"')");
                slno++;
                patient.execute();
                refresh();
                tKey.setText(""+key);
                apKey.setOpacity(1);
            }
        }
        catch(Exception e){
                e.printStackTrace();
    }
    }
    @FXML
    public void decodedView(){
            apKeyPrompt.setOpacity(1);
    }
    @FXML
    public Text ivDownload;
    @FXML
    public  void print(){
        String FILE_NAME = "D:\\RVCE\\JAVA\\Doccount\\media\\Report pdf\\"+name+file.getName().substring(0,file.getName().length()-4)+".pdf";
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
            document.open();
            Paragraph p = new Paragraph("Date: "+ Calendar.getInstance().getTime());
            WritableImage img = ivNew.snapshot(new SnapshotParameters(),null);
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ImageIO.write( SwingFXUtils.fromFXImage( img, null ), "png", byteOutput );
            com.itextpdf.text.Image  graph = null;
            graph = com.itextpdf.text.Image.getInstance( byteOutput.toByteArray() );
            graph.scaleAbsolute(500,400);
            document.add(graph);
            document.add(p);
            document.close();
            System.out.println("Done");
            File f = new File("D:\\RVCE\\JAVA\\Doccount\\media\\arrow.png");
            Image image1 = new Image(f.toURI().toString());
           // ivDownload.setImage(image1);
            ivDownload.setOpacity(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void clear(){
        ivDownload.setOpacity(0);
    }
    @FXML
    public void closePrompt(){
        key=Integer.parseInt(tfKey.getText());
        System.out.println(key);
        decoded();
        apKeyPrompt.setOpacity(0);
    }
    @FXML
    public void decoded(){
        file = decodeImage();
        Image i = new Image(file.toURI().toString());
        ivNew.setImage(i);
    }
    @FXML
    public void view(){
        int i = tReport.getSelectionModel().getFocusedIndex();
        String a = cReport.getCellData(i);
        System.out.println(a);
        Image image1 = download("encoded"+a);
        ivNew.setImage(image1);
    }
    @FXML
    public void delete(){
        String s;
        int i;
        if( (i = tReport.getSelectionModel().getFocusedIndex())==-1)
            return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Entry");
        alert.setHeaderText("Are you sure to delete the selected report?");
        //alert.setContentText("By clicking OK you will permanently delete the selected report");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            s = "encoded" + cReport.getCellData(i);
            System.out.println(i);
            MongoClient mongoClient = MongoClients.create("mongodb+srv://phalaksha:Phallu*30101@cluster0.rahp2.mongodb.net/MyFirstDatabase?retryWrites=true&w=majority");
            try {
                Connection con = dbhospital.getConnection();
                PreparedStatement p = con.prepareStatement("DELETE FROM report" + name + " WHERE Name = '" + cReport.getCellData(i) + "'");
                p.execute();
                refresh();
                System.out.println(i);
                MongoDatabase database = mongoClient.getDatabase("" + id);
                GridFSBucket gridBucket = GridFSBuckets.create(database);
                gridBucket.delete(getObjectID(s));
                ivNew.setImage(null);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mongoClient.close();
            }
        }
    }

    public ObjectId upload(String filePath, String fileName) {
        ObjectId fileId = null;
        MongoClient mongo = null;
        try {
            String ID = ""+id;
            mongo = MongoClients.create("mongodb+srv://phalaksha:Phallu*30101@cluster0.rahp2.mongodb.net/MyFirstDatabase?retryWrites=true&w=majority");
            MongoDatabase imgDb;
            imgDb = mongo.getDatabase(ID);
            //Create a gridFSBucket
            GridFSBucket gridBucket = GridFSBuckets.create(imgDb);
            InputStream inStream = new FileInputStream(new File(filePath));
            // Create some customize options for the details that describes
            // the uploaded image
            GridFSUploadOptions a = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(new Document("type", "image").append("content_type", "image/png").append("key",""+key));
            fileId = gridBucket.uploadFromStream(fileName, inStream,a);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongo.close();
        }
        return fileId;
    }
}
package com.frosoft.mavenproject1;

import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import com.itextpdf.text.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import java.io.IOException;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
public class BillController implements Initializable {
    @FXML
    public ImageView ivBg;
    @FXML
    public TableView<Test> tTest;
    @FXML
    public TableColumn<Test,String> cTestName;
    @FXML
    public TableColumn<Test,Integer> cTestCost;
    @FXML
    public TableView<Medicine> tMedicine;
    @FXML
    public TableColumn<Medicine,String> cMedicineName;
    @FXML
    public TableColumn<Medicine,String> cMedicineDosage;
    @FXML
    public TableColumn<Medicine,Integer> cMedicineCost;
    @FXML
    public TableView<Procedure> tProcedure;
    @FXML
    public TableColumn<Procedure,String> cProcedureDoctor;
    @FXML
    public TableColumn<Procedure,String> cProcedureProcedure;
    @FXML
    public TableColumn<Procedure,Integer> cProcedureFee;
    @FXML
    public AnchorPane apAddProcedure;
    @FXML
    public TextField tfProcedureName;
    @FXML
    public TextField tfProcedureFee;
    @FXML
    public TextField tfProcedureDoctorName;
    @FXML
    public Button bProcedureAdd;
    @FXML
    public Button bProcedureBack;
    @FXML
    public AnchorPane apAddTest;
    @FXML
    public TextField tfTestName;
    @FXML
    public TextField tfTestCost;
    @FXML
    public Button bTestAdd;
    @FXML
    public Button bTestBack;
    @FXML
    public AnchorPane apAddMedicine;
    @FXML
    public TextField tfMedicineName;
    @FXML
    public TextField tfMedicineDosage;
    @FXML
    public TextField tfMedicineCost;
    @FXML
    public Button bMedicineAdd;
    @FXML
    public Button bMedicineBack;
    @FXML
    public Text tPatient;
    @FXML
    public Text tMRN;
    @FXML
    public Text tTotal;
    @FXML
    public Button bLogout;
    @FXML
    public Button bBack;
    String name;

    @FXML
            Text tDate;
    int total=0;
    static boolean patient = false;
    DatabaseConnection dbPatient = new DatabaseConnection();
    public static int ID;
    public static void setID(int parseInt) {
        ID = parseInt;
    }
    @FXML
    public AnchorPane apMain;
    @FXML
    public Button bPrint;
    @FXML
    public  void print(){
        String FILE_NAME = "D:\\RVCE\\JAVA\\Doccount\\media\\Bill\\"+name+".pdf";
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
            document.open();
            bAdd.setOpacity(0);
            bAdd1.setOpacity(0);
            bAdd2.setOpacity(0);
            bAdd3.setOpacity(0);
            bAdd4.setOpacity(0);
            bAdd5.setOpacity(0);
            bPrint.setOpacity(0);
            Paragraph p = new Paragraph("Date Paid: "+ Calendar.getInstance().getTime());
            WritableImage img = apMain.snapshot(new SnapshotParameters(),null);
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ImageIO.write( SwingFXUtils.fromFXImage( img, null ), "png", byteOutput );
            com.itextpdf.text.Image  graph = null;
            graph = com.itextpdf.text.Image.getInstance( byteOutput.toByteArray() );
            graph.scaleAbsolute(500,400);
            document.add(graph);
            document.add(p);
            document.close();
            System.out.println("Done");
            if(!patient) {
                bAdd.setOpacity(1);
                bAdd1.setOpacity(1);
                bAdd2.setOpacity(1);
                bAdd3.setOpacity(1);
                bAdd4.setOpacity(1);
                bAdd5.setOpacity(1);
                bPrint.setOpacity(1);
            }
            bPrint.setOpacity(1);
            ivDownload.setOpacity(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public Text ivDownload;
    @FXML
    public void clear(){
        ivDownload.setOpacity(0);
    }
    ObservableList<Procedure> getProcedure(){
        ObservableList<Procedure> procedure = FXCollections.observableArrayList();
        Connection s = dbPatient.getConnection();
        Statement stmt = null;
        Procedure p;
        try {
            stmt = s.createStatement();
        ResultSet aa = stmt.executeQuery("SELECT * FROM bill"+name);
        while(aa.next()){
            p=new Procedure(aa.getString("pDoctor"),aa.getString("pProcedure"),aa.getInt("pFee") );
            total+=p.Cost;
            if(p.Doctor==null)
               continue;
            procedure.add(p);
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return procedure;
    }
    ObservableList<Medicine> getMedicine(){
        ObservableList<Medicine> procedure = FXCollections.observableArrayList();
        Connection s = dbPatient.getConnection();
        Statement stmt = null;
        Medicine p;
        try {
            stmt = s.createStatement();
            ResultSet aa = stmt.executeQuery("SELECT * FROM bill"+name);
            while(aa.next()){
                p = new Medicine(aa.getString("mName"),aa.getString("mDosage"),aa.getInt("mCost") );
                total+=p.cost;
                if(p.Name==null)
                    continue;
                procedure.add(p);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return procedure;
    }
    ObservableList<Test> getTest(){
        ObservableList<Test> procedure = FXCollections.observableArrayList();
        Connection s = dbPatient.getConnection();
        Statement stmt = null;
        Test p;
        try {
            stmt = s.createStatement();
            ResultSet aa = stmt.executeQuery("SELECT * FROM bill"+name);
            while(aa.next()){
                p = new Test(aa.getString("tName"),aa.getInt("tCost") );
                total+=p.Cost;
                if(p.Name==null)
                    continue;
                procedure.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return procedure;
    }
    public void refresh(){
        total=0;
        cMedicineCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        cMedicineDosage.setCellValueFactory(new PropertyValueFactory<>("Dosage"));
        cMedicineName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tMedicine.setItems(getMedicine());
        cProcedureDoctor.setCellValueFactory(new PropertyValueFactory<>("Doctor"));
        cProcedureFee.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        cProcedureProcedure.setCellValueFactory(new PropertyValueFactory<>("Procedure"));
        tProcedure.setItems(getProcedure());
        cTestCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        cTestName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tTest.setItems(getTest());
        tTotal.setText("Total = Rs "+total);
    }
    @FXML
    Button bAdd,bAdd1,bAdd2,bAdd3,bAdd4,bAdd5;
    @Override
    public void initialize(URL url, ResourceBundle rb){
        tDate.setText("Date Generated = "+String.valueOf(java.util.Calendar.getInstance().getTime()));
        if(patient){
            bAdd.setOpacity(0);
            bAdd1.setOpacity(0);
            bAdd2.setOpacity(0);
            bAdd3.setOpacity(0);
            bAdd4.setOpacity(0);
            bAdd5.setOpacity(0);
            name = PrimaryController.patientName.toLowerCase(Locale.ROOT);
        }
        File file = new File("D:\\RVCE\\JAVA\\Doccount\\media\\bg4.jpg");
        Image i = new Image(file.toURI().toString());
        ivBg.setImage(i);
        if(!patient){
        try {
            Statement s = dbPatient.getConnection().createStatement();
            ResultSet set = s.executeQuery("SELECT * FROM patients WHERE MRN_Number="+ID);
            set.next();
            name = set.getString("Name");
            name=name.toLowerCase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }}
        else{
            try {
                Statement s = dbPatient.getConnection().createStatement();
                ResultSet set = s.executeQuery("SELECT * FROM patients WHERE Name ='"+PrimaryController.patientName+"'");
                set.next();
                ID = set.getInt("MRN_Number");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println(ID+name);
        tPatient.setText("NAME: "+ name.toUpperCase());
        tMRN.setText("MRN: "+ID);
        ImageView ivLogout = new ImageView();
        File n = new File("D:\\RVCE\\JAVA\\Doccount\\media\\logout.png");
        Image img = new Image(n.toURI().toString());
        ivLogout.setImage(img);
        ivLogout.setFitHeight(20);
        ivLogout.setFitWidth(20);
        bLogout.setGraphic(ivLogout);
        refresh();

    }
    public void procedureAddWindow(){
        apAddProcedure.setOpacity(1);

    }

    public void procedureAdd() {
        Connection s = dbPatient.getConnection();
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = s.prepareStatement("INSERT INTO bill" + name + " VALUES('" + tfProcedureDoctorName.getText() + "','" + tfProcedureName.getText() + "'," + tfProcedureFee.getText() + ",NULL,NULL,NULL,NULL,NULL)");
            preparedStmt.execute();
            refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tfProcedureFee.clear();
        tfProcedureName.clear();
    }
    public void procedureBack(){
        apAddProcedure.setOpacity(0);
    }
    public void testAdd() {
        Connection s = dbPatient.getConnection();
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = s.prepareStatement("INSERT INTO bill" + name + " VALUES(NULL,NULL,NULL,NULL,NULL,NULL,'"+tfTestName.getText()+"',"+tfTestCost.getText()+")");
            preparedStmt.execute();
            refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tfTestCost.clear();
        tfTestName.clear();
    }
    public void testBack(){
        apAddTest.setOpacity(0);
    }
    public void medicineAdd() {
        Connection s = dbPatient.getConnection();
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = s.prepareStatement("INSERT INTO bill" + name + " VALUES(NULL,NULL,NULL,'" + tfMedicineName.getText() + "','" + tfMedicineDosage.getText() + "'," + tfMedicineCost.getText() + ",NULL,NULL)");
            preparedStmt.execute();
            refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tfMedicineName.clear();
        tfMedicineCost.clear();
        tfMedicineDosage.clear();
    }
    public void medicineBack(){
        apAddMedicine.setOpacity(0);
    }
    public void deleteProcedure() {
        int i = tProcedure.getSelectionModel().getFocusedIndex();
        String n = cProcedureProcedure.getCellData(i);
        Connection s = dbPatient.getConnection();
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = s.prepareStatement("DELETE FROM bill" + name + " WHERE pProcedure ='" + n + "'");
            preparedStmt.execute();
            refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void medicineAddWindow(){
        apAddMedicine.setOpacity(1);
    }
    public void medicineDelete() {
        int i = tMedicine.getSelectionModel().getFocusedIndex();
        String n = cMedicineName.getCellData(i);
        Connection s = dbPatient.getConnection();
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = s.prepareStatement("DELETE FROM bill" + name + " WHERE mName ='" + n + "'");
            preparedStmt.execute();
            refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void testAddWindow(){
        apAddTest.setOpacity(1);
    }
    public void TestDelete(){
        int i = tTest.getSelectionModel().getFocusedIndex();
        String n = cTestName.getCellData(i);
        Connection s = dbPatient.getConnection();
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = s.prepareStatement("DELETE FROM bill" + name + " WHERE tName ='" + n + "'");
            preparedStmt.execute();
            refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void logout(){
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void back(){
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
    /*public void pdf() throws IOException
        {
            PDDocument pdfdoc= new PDDocument();
            pdfdoc.addPage(new PDPage());
            //path where the PDF file will be store
            pdfdoc.save("D:\\RVCE\\JAVA\\Doccount\\media\\bill"+name+".pdf");
                //prints the message if the PDF is created successfully
            System.out.println("PDF created");
                    //closes the document
            pdfdoc.close();
        }*/
    }


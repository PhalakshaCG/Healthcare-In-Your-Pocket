module com.frosoft.mavenproject1 {
    //requires derbyclient;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.base;
    opens com.frosoft.mavenproject1 to javafx.fxml;
    exports com.frosoft.mavenproject1;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.desktop;
    requires org.apache.commons.codec;
    requires itextpdf;
    requires javafx.swing;

}

module com.mycompany.inventorymanagementapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.mycompany.inventorymanagementapp to javafx.fxml;
    exports com.mycompany.inventorymanagementapp;
}

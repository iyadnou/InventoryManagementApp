package com.mycompany.inventorymanagementapp;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {
    
   @FXML
    private void switchToCreate() throws IOException {
        App.setRoot("create");
    }
    
    @FXML
    private void switchToUpdate() throws IOException {
        App.setRoot("Update");
    }
    
    @FXML
    private void switchToDelete() throws IOException {
        App.setRoot("delete");
    }
    
    @FXML
    private void switchToSearch() throws IOException {
        App.setRoot("search");
    }
    
    @FXML
    private void switchToTotal() throws IOException {
        App.setRoot("totalvalue");
    }
}

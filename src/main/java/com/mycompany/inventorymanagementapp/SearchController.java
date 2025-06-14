/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementapp;

/**
 *
 * @author mohamedayadeddinenouadi
 */
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.List;
import javafx.scene.control.Alert;

public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> resultsList;
    
    @FXML 
    private TextField pathField;

    @FXML
    public void handleSearch() {
        String keyword = searchField.getText().trim();
        if (!keyword.isEmpty()) {
            List<String> results = DB.searchProduct(keyword);
            resultsList.getItems().setAll(results);
        } else {
            resultsList.getItems().clear();
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void switchToMenu() {
    try {
        App.setRoot("primary");
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Failed to load the menu screen.");
    }
}
    @FXML
private void handleExport() throws IOException {
    String keyword = searchField.getText().trim();
    String path    = pathField.getText().trim();

    if (keyword.isEmpty() || path.isEmpty()) {
        showAlert("Please enter both a search term and a file path.");
        return;
    }

    boolean ok = DB.exportSearchResultsToCSV(keyword, path);
    if (ok) {
        showAlert("Export successful!");
    } else {
        showAlert("Export failed. See console for details.");
    }
}
}
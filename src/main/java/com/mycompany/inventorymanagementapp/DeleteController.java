/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementapp;

/**
 *
 * @author mohamedayadeddinenouadi
 */

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeleteController {

    @FXML
    private TextField deleteIdField;

    @FXML
    private Button deleteButton;

    // Method triggered when deleteButton is clicked
    @FXML
    private void handleDeleteProduct() {
        String idText = deleteIdField.getText();
        if (idText.isEmpty()) {
            showAlert("Please enter a product ID.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            boolean success = DB.deleteProductById(id);
            if (success) {
                showAlert("Product deleted successfully.");
                deleteIdField.clear();
            } else {
                showAlert("No product found with that ID.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid ID. Please enter a numeric value.");
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
}


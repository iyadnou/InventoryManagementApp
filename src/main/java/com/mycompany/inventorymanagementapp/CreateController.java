/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author mohamedayadeddinenouadi
 */
public class CreateController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField supplierField;
    

    // Triggered when button is clicked
    @FXML
    public void handleCreateProduct() {
        String name = nameField.getText();
        String priceText = priceField.getText();
        String quantityText = quantityField.getText();
        String supplier = supplierField.getText(); // Not yet stored in DB unless you extend your DB class

        // Basic validation
        if (name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
            showAlert("All fields (name, price, quantity) must be filled.");
            return;
        }

        try {
            Float price = Float.valueOf(priceText);
            int quantity = Integer.parseInt(quantityText);

            // Call DB method
            boolean success = DB.createProduct(name, quantity, price, supplier);
            if (success) {
                showAlert("Product added successfully!");
                clearFields();
            } else {
                showAlert("Failed to add product.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid number format for price or quantity.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nameField.clear();
        priceField.clear();
        quantityField.clear();
        supplierField.clear();
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
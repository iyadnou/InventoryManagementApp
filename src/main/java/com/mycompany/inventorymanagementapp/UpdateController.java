/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author mohamedayadeddinenouadi
 */
public class UpdateController {
    @FXML
    private TextField idField;
    
    @FXML
    private TextField quantityField;
    
    @FXML
    private TextField priceField;

    @FXML
    private ListView<String> productListView;

    @FXML
    private void handleShowProduct() {
        String idText = idField.getText();
        if (idText.isEmpty()) {
            showAlert("Please enter a product ID.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            String result = DB.getProductById(id);
            productListView.getItems().clear();
            productListView.getItems().add(result);
        } catch (NumberFormatException e) {
            showAlert("Invalid ID format. Please enter a number.");
        }
    }
    
    @FXML
    private void handleUpdateProduct() {
        String idText = idField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        if (idText.isEmpty()) {
            showAlert("Please enter the product ID.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            Integer quantity = quantityText.isEmpty() ? null : Integer.parseInt(quantityText);
            Float price = priceText.isEmpty() ? null : Float.parseFloat(priceText);

            if (quantity == null && price == null) {
                showAlert("Enter at least one value to update.");
                return;
            }

            boolean success = DB.updateProduct(id, quantity, price);
            if (success) {
                showAlert("Product updated successfully.");
                handleShowProduct(); // refresh the view
                quantityField.clear();
                priceField.clear();
            } else {
                showAlert("Update failed. Check if the product exists.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid format for ID, quantity, or price.");
        }
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
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

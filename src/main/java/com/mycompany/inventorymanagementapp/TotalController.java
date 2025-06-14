/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 *
 * @author mohamedayadeddinenouadi
 */
public class TotalController {
    @FXML
    private TextArea totalTextArea;

    @FXML
    private Button calculateButton;

    @FXML
    private void handleCalculateTotal() {
        float totalValue = DB.calculateTotalInventoryValue();
        totalTextArea.setText("Total Inventory Value: £" + totalValue);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementapp;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mohamedayadeddinenouadi
 */
public class DB {
    private static final String URL = "jdbc:sqlite:/Users/mohamedayadeddinenouadi/DataGripProjects/Inventory/identifier.sqlite";
    // Get connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    // Optional: close connection
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
    
    public static boolean createProduct(String name, int quantity, float price, String supplier) {
        String sql = "INSERT INTO products(name, quantity, price, supplier) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setFloat(3, price);
            stmt.setString(4, supplier);

            stmt.executeUpdate();
            System.out.println("Product created successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to create product: " + e.getMessage());
            return false;
        }
    }
    public static boolean deleteProductById(int id) {
    String sql = "DELETE FROM products WHERE id = ?";

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);

        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Product with ID " + id + " deleted successfully.");
            return true;
        } else {
            System.out.println("No product found with ID " + id + ".");
            return false;
        }
    } catch (SQLException e) {
        System.out.println("Failed to delete product: " + e.getMessage());
        return false;
    }
}
public static List<String> searchProduct(String keyword) {
    String sql = "SELECT * FROM products WHERE name LIKE ? OR CAST(id AS TEXT) LIKE ? OR supplier LIKE ?";
    List<String> results = new ArrayList<>();

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        String searchPattern = "%" + keyword + "%";
        stmt.setString(1, searchPattern);
        stmt.setString(2, searchPattern);
        stmt.setString(3, searchPattern);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String row = String.format("ID: %d | Name: %s | Quantity: %d | Price: %.2f | Supplier: %s",
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("quantity"),
                rs.getFloat("price"),
                rs.getString("supplier")
            );
            results.add(row);
        }
    } catch (SQLException e) {
        System.out.println("Search failed: " + e.getMessage());
    }

    return results;
}
public static String getProductById(int id) {
    String sql = "SELECT * FROM products WHERE id = ?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        var rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            float price = rs.getFloat("price");
            String supplier = rs.getString("supplier");
            return String.format("ID: %d | Name: %s | Quantity: %d | Price: %.2f | Supplier: %s",
                                 id, name, quantity, price, supplier);
        } else {
            return "No product found with ID: " + id;
        }
    } catch (SQLException e) {
        return "Error: " + e.getMessage();
    }
}
public static boolean updateProduct(int id, Integer quantity, Float price) {
    StringBuilder sql = new StringBuilder("UPDATE products SET ");
    List<Object> params = new ArrayList<>();

    if (quantity != null) {
        sql.append("quantity = ?");
        params.add(quantity);
    }
    if (price != null) {
        if (!params.isEmpty()) sql.append(", ");
        sql.append("price = ?");
        params.add(price);
    }

    sql.append(" WHERE id = ?");
    params.add(id);

    if (params.size() <= 1) {
        return false; // nothing to update
    }

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.out.println("Update failed: " + e.getMessage());
        return false;
    }
}
public static float calculateTotalInventoryValue() {
    String sql = "SELECT SUM(quantity * price) AS total_value FROM products";

    try (Connection conn = getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql); 
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getFloat("total_value");
        }
    } catch (SQLException e) {
        System.out.println("Failed to calculate total inventory value: " + e.getMessage());
    }

    return 0f; // Return 0 if there is an error or no rows
}
public static boolean exportSearchResultsToCSV(String keyword, String filePath) throws IOException {
    String sql = "SELECT * FROM products WHERE name LIKE ? OR CAST(id AS TEXT) LIKE ? OR supplier LIKE ?";
    String searchPattern = "%" + keyword + "%";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

        // Write CSV header
        writer.write("ID,Name,Quantity,Price,Supplier");
        writer.newLine();

        // Run the query
        stmt.setString(1, searchPattern);
        stmt.setString(2, searchPattern);
        stmt.setString(3, searchPattern);
        ResultSet rs = stmt.executeQuery();

        // Write each row as CSV
        while (rs.next()) {
            int    id       = rs.getInt("id");
            String name     = rs.getString("name").replaceAll(",", " ");  // remove commas
            int    qty      = rs.getInt("quantity");
            float  price    = rs.getFloat("price");
            String supplier = rs.getString("supplier").replaceAll(",", " ");

            // e.g. "1,Pen,100,1.50,OfficeSupplyCo"
            String line = String.format("%d,%s,%d,%.2f,%s",
                                        id, name, qty, price, supplier);
            writer.write(line);
            writer.newLine();
        }

        System.out.println("Exported search results to " + filePath);
        return true;
    } catch (SQLException | IOException e) {
        System.out.println("Export failed: " + e.getMessage());
        return false;
    }
}

}

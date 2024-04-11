import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JPanel;
import javax.swing.JButton;

public class DeleteDb {
    public static void deleteProduct(JTable table, Connection con) {
        String productIdStr = JOptionPane.showInputDialog(null, "Enter Product ID to delete:");

        // Check if productIdStr is empty
        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Check if the product with the given ID exists
            boolean productExists = checkProductExists(con, productIdStr);
            if (!productExists) {
                JOptionPane.showMessageDialog(null, "No product found with ID: " + productIdStr, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get the category of the product to be deleted
            String category = getCategory(con, productIdStr);

            // Display the confirmation dialog before deleting.
            int confirm  = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the product with ID: "+ productIdStr + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                // Delete the product from the database
                Statement st = con.createStatement();
                String query = "DELETE FROM Product WHERE ID = '" + productIdStr + "'";
                st.executeUpdate(query);
                st.close();

                // Refresh the table to reflect the changes
                ListALLProducts.populateTable(table);

                // Remove the category button if it doesn't have any other associated products
                removeCategoryButton(category, con);

                JOptionPane.showMessageDialog(null, "Product deleted successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Product deletion canceled");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting product: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to check if a product with the given ID exists in the database
    private static boolean checkProductExists(Connection con, String productIdStr) throws SQLException {
        Statement st = con.createStatement();
        String query = "SELECT * FROM Product WHERE ID = '" + productIdStr + "'";
        ResultSet rs = st.executeQuery(query);
        boolean exists = rs.next(); // Check if ResultSet is not empty
        rs.close();
        st.close();
        return exists;
    }

    // Method to get the category of a product by its ID
    private static String getCategory(Connection con, String productIdStr) throws SQLException {
        Statement st = con.createStatement();
        String query = "SELECT Category FROM Product WHERE ID = '" + productIdStr + "'";
        ResultSet rs = st.executeQuery(query);
        String category = "";
        if (rs.next()) {
            category = rs.getString("Category");
        }
        rs.close();
        st.close();
        return category;
    }

    // Method to remove the category button if it doesn't have any associated products
    private static void removeCategoryButton(String category, Connection con) {
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM Product WHERE Category = '" + category + "'";
            ResultSet rs = st.executeQuery(query);

            // If no products are found with the given category, remove the category button
            if (!rs.next()) {
                JPanel categoryPanel = GUIInitiator.getCategoryPanel();
                if (categoryPanel != null) {
                    JButton buttonToRemove = null;
                    for (java.awt.Component comp : categoryPanel.getComponents()) {
                        if (comp instanceof JButton && ((JButton) comp).getText().equals(category)) {
                            buttonToRemove = (JButton) comp;
                            break;
                        }
                    }
                    if (buttonToRemove != null) {
                        categoryPanel.remove(buttonToRemove);
                        categoryPanel.revalidate();
                        categoryPanel.repaint();
                    }
                }
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}






































































/* 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;

public class DeleteDb {
    public static void deleteProduct(JTable table, Connection con) {
        String productIdStr = JOptionPane.showInputDialog(null, "Enter Product ID to delete:");

        // Check if productIdStr is empty
        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Check if the product with the given ID exists
            boolean productExists = checkProductExists(con, productIdStr);
            if (!productExists) {
                JOptionPane.showMessageDialog(null, "No product found with ID: " + productIdStr, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Display the confirmation dialog before deleting.
            int confirm  = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the product with ID: "+ productIdStr + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                // Delete the product from the database
                Statement st = con.createStatement();
                String query = "DELETE FROM Product WHERE ID = '" + productIdStr + "'";
                st.executeUpdate(query);
                st.close();

                // Refresh the table to reflect the changes
                ListALLProducts.populateTable(table);

                JOptionPane.showMessageDialog(null, "Product deleted successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Product deletion canceled");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting product: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to check if a product with the given ID exists in the database
    private static boolean checkProductExists(Connection con, String productIdStr) throws SQLException {
        Statement st = con.createStatement();
        String query = "SELECT * FROM Product WHERE ID = '" + productIdStr + "'";
        ResultSet rs = st.executeQuery(query);
        boolean exists = rs.next(); // Check if ResultSet is not empty
        rs.close();
        st.close();
        return exists;
    }
}*/






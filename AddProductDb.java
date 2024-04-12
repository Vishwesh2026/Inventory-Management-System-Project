import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;

public class AddProductDb {
    public static void addNewProduct(JTable table, Connection con) {
        // Array to hold the labels for input fields
        String[] labels = {"ID NO:", "Product name:", "Product cost:", "Product quantity:", "Category Name:"};
        // Array to hold the text fields for user input
        JTextField[] textFields = new JTextField[labels.length];

        // Panel to hold the labels and text fields
        JPanel panel = new JPanel(new GridLayout(labels.length, 2));

        // Add labels and text fields to the panel
        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i]));
            JTextField textField = new JTextField();
            panel.add(textField);
            textFields[i] = textField;
        }

        // Show the panel in an option pane
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Product Details", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            // User canceled input
            return;
        }

        // Get user inputs from text fields
        String[] inputs = new String[labels.length];
        for (int i = 0; i < labels.length; i++) {
            inputs[i] = textFields[i].getText();
            // If any input field is empty, return without adding the product
            if (inputs[i] == null || inputs[i].isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            // Prepare the SQL statement with parameters
            String query = "INSERT INTO Product (ID, Name, Cost, Quantity, Category) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set parameter values
            pstmt.setString(1, inputs[0]);
            pstmt.setString(2, inputs[1]);
            pstmt.setInt(3, Integer.parseInt(inputs[2]));
            pstmt.setInt(4, Integer.parseInt(inputs[3]));
            pstmt.setString(5, inputs[4]);

            // Execute the SQL statement
            pstmt.executeUpdate();
            pstmt.close();

            // Refresh the table to display the new product
            ListALLProducts.populateTable(table);

            JOptionPane.showMessageDialog(null, "Product added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding product: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}













































































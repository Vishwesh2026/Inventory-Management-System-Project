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






















































































//main code
/*import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;

public class AddProductDb {
    static Connection con;
    static Statement statement;
    public static void addNewProduct(JTable table, Connection con) {
        String IDStr = JOptionPane.showInputDialog(null,"Enter ID NO:");
        String name = JOptionPane.showInputDialog(null, "Enter product name:");
        String costStr = JOptionPane.showInputDialog(null, "Enter product cost:");
        String quantityStr = JOptionPane.showInputDialog(null, "Enter product quantity:");
        String  category = JOptionPane.showInputDialog(null, "Enter Category Name : ");
        
        if (IDStr == null || name == null || costStr == null || quantityStr==null||category==null){
            return;
        }
        // Convert cost and quantity to integers
        //int ID = Integer.parseInt(IDStr);
        int cost = Integer.parseInt(costStr);
        int quantity = Integer.parseInt(quantityStr);
        
        // Insert the new product into the database
        try {
            Statement st = con.createStatement();
            String query = "INSERT INTO Product (ID, Name, Cost, Quantity,Category) VALUES ('" + IDStr + "', '" + name + "', " + cost +  ", " + quantity +", " + category +")";

            st.executeUpdate(query);
            st.close();
            
            // Refresh the table to display the new product
            ListALLProducts.populateTable(table);
            
            JOptionPane.showMessageDialog(null, "Product added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding product: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}*/





// the below code is the modified version and a category session also have been added
/* 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class AddProductDb {
    public static void addNewProduct(JTable table, Connection con) {
        String IDStr = JOptionPane.showInputDialog(null, "Enter ID NO:");
        String name = JOptionPane.showInputDialog(null, "Enter product name:");
        String costStr = JOptionPane.showInputDialog(null, "Enter product cost:");
        String quantityStr = JOptionPane.showInputDialog(null, "Enter product quantity:");
        String category = JOptionPane.showInputDialog(null, "Enter Category Name:");

        if (IDStr == null || name == null || costStr == null || quantityStr == null || category == null) {
            return;
        }

        try {
            // Prepare the SQL statement with parameters
            String query = "INSERT INTO Product (ID, Name, Cost, Quantity, Category) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            
            // Set parameter values
            pstmt.setString(1, IDStr);
            pstmt.setString(2, name);
            pstmt.setInt(3, Integer.parseInt(costStr));
            pstmt.setInt(4, Integer.parseInt(quantityStr));
            pstmt.setString(5, category);

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

*/


// the below code is the modified version of the above code so that it will work if you click cancel at any of the cancel button It will close the other sussesive JOption panes from displaying
/*import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class AddProductDb {
    public static void addNewProduct(JTable table, Connection con) {
        // Array to hold the labels for input fields
        String[] labels = {"ID NO:", "Product name:", "Product cost:", "Product quantity:", "Category Name:"};
        // Array to hold the user inputs
        String[] inputs = new String[labels.length];

        // Prompt user for input using a single JOptionPane
        for (int i = 0; i < labels.length; i++) {
            inputs[i] = JOptionPane.showInputDialog(null, "Enter " + labels[i]);
            // If user cancels any input, return without adding the product
            if (inputs[i] == null) {
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
*/


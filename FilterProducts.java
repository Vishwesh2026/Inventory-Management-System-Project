




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
//import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
//import javax.swing.SwingUtilities;


public class FilterProducts {
    public static void displayCategoryButtons(Connection con, JPanel categoryPanel) {
        try {
            // Query to retrieve distinct category names
            String query = "SELECT DISTINCT Category FROM Product";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Iterate through the result set and create a button for each category
            while (resultSet.next()) {
                String category = resultSet.getString("Category");
                JButton categoryButton = new JButton(category);
                categoryButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Perform filtering based on the selected category
                        filterByCategory(con, category, GUIInitiator.table);
                    }
                });
                categoryPanel.add(categoryButton); // Add button to the panel
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void filterByCategory(Connection con, String category, JTable table) {
        try {
            // Query to retrieve products based on the selected category
            String query = "SELECT * FROM Product WHERE Category = '" + category + "'";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Create a table model
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Cost");
            model.addColumn("Quantity");
            model.addColumn("Category");

            // Populate the table model with data from the result set
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String name = resultSet.getString("Name");
                int cost = resultSet.getInt("Cost");
                int quantity = resultSet.getInt("Quantity");

                model.addRow(new Object[]{id, name, cost, quantity, category});
            }

            // Set the table model to the provided JTable
            table.setModel(model);

            // Close the statement and result set
            statement.close();
            resultSet.close();


            /* // Close the filter JPanel
             JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(table);
             JPanel filterPanel = GUIInitiator.getCategoryPanel();
             if (filterPanel != null) {
                 frame.remove(filterPanel);
                 frame.revalidate();
                 frame.repaint();
             }*/
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

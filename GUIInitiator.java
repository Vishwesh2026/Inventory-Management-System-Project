import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

public class GUIInitiator extends JFrame {
    static JTextArea textArea;
    static JScrollPane scrollPane;
    static JTextField productIdTextField;
    static JButton listAllButton, addProductButton, updateQuantityButton, updateCostButton, deleteButton, viewProductButton, exitButton, clearButton, filterButton;
    static JLabel productIdLabel;
    static JTable table;
    static Connection con;
    static JFrame frame;
    static JPanel categoryPanel; // Declare categoryPanel as static
    static JScrollPane categoryScrollPane;


    public static void initializeGUI(Connection con) {
        GUIInitiator.con = con;
        frame = new JFrame("Inventory Management System");
        frame.setSize(930, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(java.awt.Color.lightGray);

        Font font = new Font("calibri",Font.BOLD,18);

        textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setEditable(false);

        productIdLabel = new JLabel("Enter product Id: ");
        productIdLabel.setFont(font);
        productIdLabel.setBounds(220, 5, 150, 30);
        frame.add(productIdLabel);

        productIdTextField = new JTextField();
        productIdTextField.setBounds(370, 5, 100, 25);
        frame.add(productIdTextField);

        viewProductButton = new JButton("View Product");
        viewProductButton.setBounds(500, 5, 150, 25);
        frame.add(viewProductButton);

        filterButton = new JButton("Filter");
        filterButton.setBounds(125, 85, 100, 25);
        frame.add(filterButton);

        listAllButton = new JButton("List All Products");
        listAllButton.setBounds(20, 45, 150, 25);
        frame.add(listAllButton);

        addProductButton = new JButton("Add Product");
        addProductButton.setBounds(180, 45, 150, 25);
        frame.add(addProductButton);

        updateQuantityButton = new JButton("Update Quantity");
        updateQuantityButton.setBounds(340, 45, 150, 25);
        frame.add(updateQuantityButton);

        updateCostButton = new JButton("Update Cost");
        updateCostButton.setBounds(500, 45, 150, 25);
        frame.add(updateCostButton);

        deleteButton = new JButton("Delete Product");
        deleteButton.setBounds(660, 45, 150, 25);
        frame.add(deleteButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(820, 45, 70, 25);
        frame.add(exitButton);

        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(275, 95, 500, 400);///////
        frame.add(scrollPane);

        clearButton = new JButton("Clear");
        clearButton.setBounds(420, 500, 80, 25);
        frame.add(clearButton);
        
    
        categoryScrollPane = new JScrollPane();
        categoryScrollPane.setBounds(100, 120, 150, 375); // Adjust the bounds as needed
        frame.add(categoryScrollPane);

        listAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListALLProducts.populateTable(table);
            }
        });
        ViewProductId.viewProduct(viewProductButton, frame, con,productIdTextField);

        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddProductDb.addNewProduct(table, con);
                
                // Update category buttons after adding a new product
                updateCategoryButtons();
            }
        });

        updateQuantityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                UpdateDb.updateQuantity(con);
            }
        });

        updateCostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                UpdateDb.updateCost(con);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                DeleteDb.deleteProduct(table, con);
            }
        });

        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    frame.dispose(); // Close the JFrame
                }
            }
        });
        

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Clear.clearTable(table); // Clear the text area
            }
        });

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add action listener to the filter button
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCategoryButtons();
            }
        });
    }

    // Getter method for the category panel
    public static JPanel getCategoryPanel() {
        return categoryPanel;
    }
    public static void displayCategoryButtons() {
     // Check if the category panel is currently displayed
     if (categoryPanel != null && frame.getContentPane().getComponents().length > 0) {
        // Remove the category panel from the categoryScrollPane
        categoryScrollPane.setViewportView(null);
        frame.remove(categoryScrollPane);
        frame.revalidate();
        frame.repaint();
        categoryPanel = null; // Reset categoryPanel
        return; // Exit the method
    }

    // Create a new category panel
    categoryPanel = new JPanel();
    categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS)); // Set layout to vertical

    // Call method to display category buttons
    FilterProducts.displayCategoryButtons(con, categoryPanel);

    // Set the category panel as the view of the categoryScrollPane
    categoryScrollPane.setViewportView(categoryPanel);

    // Add the categoryScrollPane to the frame
    frame.add(categoryScrollPane);

    // Revalidate and repaint the frame to reflect changes
    frame.revalidate();
    frame.repaint();
    }
    private static void updateCategoryButtons() {
        if (categoryPanel != null) {
            categoryPanel.removeAll(); // Clear existing buttons
            FilterProducts.displayCategoryButtons(con, categoryPanel); // Display updated buttons
            frame.revalidate(); // Revalidate the frame to reflect changes
            frame.repaint(); // Repaint the frame
        }
    }
  
    
}

package view;

import model.FurnitureSwingModel;
import service.ApiService;
import utils.ReadOnlyTableModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class FurnitureTable extends JPanel {
    private JTable table;
    private JButton addButton, editButton, deleteButton;

    public FurnitureTable() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        UIManager.put("Table.font", new FontUIResource(new Font("Mono", Font.PLAIN, 12)));
        UIManager.put("Button.font", new FontUIResource(new Font("Mono", Font.BOLD, 12)));
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Category", "Price", "Image"};
        ReadOnlyTableModel model = new ReadOnlyTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(50);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadFurniture();

        addButton.addActionListener(e -> {
            JFrame frame = new JFrame("Add Furniture");
            frame.setContentPane(new FurnitureForm(null, this));
            frame.pack();
            frame.setVisible(true);
            loadFurniture();
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Long id = (Long) table.getValueAt(selectedRow, 0);
                FurnitureSwingModel selectedFurniture = ApiService.getFurnitureById(id);
                JFrame frame = new JFrame("Edit Furniture");
                frame.setContentPane(new FurnitureForm(selectedFurniture, this));
                frame.pack();
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a furniture item to edit.");
            }
            loadFurniture();
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Long id = (Long) table.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this item?",
                        "Delete Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    ApiService.deleteFurniture(id);
                    loadFurniture();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a furniture item to delete.");
            }
            loadFurniture();
        });
    }

    public void loadFurniture() {
        List<FurnitureSwingModel> furniture = ApiService.getFurniture();
        Object[][] data = new Object[furniture.size()][5];
        for (int i = 0; i < furniture.size(); i++) {
            FurnitureSwingModel f = furniture.get(i);
            data[i][0] = f.getId();
            data[i][1] = f.getName();
            data[i][2] = f.getCategory();
            data[i][3] = f.getPrice();
            try {
                BufferedImage myPicture = ImageIO.read(new File(System.getenv("IMAGE_FOLDER") + f.getImageName()));
                Image scaledImage = myPicture.getScaledInstance(65, 50, Image.SCALE_SMOOTH);
                JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
                data[i][4] = picLabel;
            } catch (Exception e) {
                data[i][4] = f.getImageName();
            }
        }
        table.setModel(new ReadOnlyTableModel(data, new String[]{"ID", "Name", "Category", "Price", "Image"}));
        table.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());
    }

    static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JLabel) {
                return (JLabel) value;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
package view;

import model.FurnitureSwingModel;
import service.ApiService;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;

public class FurnitureForm extends JPanel {
    private JTextField nameField, priceField;
    private JComboBox<String> categoryComboBox;
    private JButton saveButton, imageButton;
    private JLabel imageLabel;
    private File selectedImageFile;

    public FurnitureForm(FurnitureSwingModel furniture, FurnitureTable furnitureTable) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        UIManager.put("Button.font", new FontUIResource(new Font("Mono", Font.BOLD, 14)));
        UIManager.put("TextField.font", new FontUIResource(new Font("Mono", Font.PLAIN, 14)));
        UIManager.put("Label.font", new FontUIResource(new Font("Mono", Font.PLAIN, 14)));
        UIManager.put("ComboBox.font", new FontUIResource(new Font("Mono", Font.PLAIN, 14)));
        setLayout(new GridLayout(5, 2));

        nameField = new JTextField();
        priceField = new JTextField();
        categoryComboBox = new JComboBox<>(new String[]{"Диван", "Фотьойл", "Стол", "Маса", "Гардероб", "Скрин"});
        saveButton = new JButton("Save");
        imageButton = new JButton("Select Image");
        imageLabel = new JLabel();

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Price:"));
        add(priceField);
        add(new JLabel("Category:"));
        add(categoryComboBox);
        add(new JLabel("Image:"));
        add(imageButton);
        add(imageLabel);
        add(saveButton);

        if (furniture != null) {
            nameField.setText(furniture.getName());
            priceField.setText(String.valueOf(furniture.getPrice()));
            categoryComboBox.setSelectedItem(furniture.getCategory());
            imageLabel.setText(furniture.getImageName());
            selectedImageFile = new File(System.getenv("IMAGE_FOLDER") + furniture.getImageName());
        }

        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                imageLabel.setText(selectedImageFile.getName());
            }
        });

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String category = (String) categoryComboBox.getSelectedItem();
            String imageName = selectedImageFile != null ? selectedImageFile.getName() : null;

            FurnitureSwingModel newFurnitureRequest;
            if (furniture != null) {
                newFurnitureRequest = new FurnitureSwingModel(furniture.getId(), name, category, price, imageName);
                ApiService.updateFurniture(newFurnitureRequest, selectedImageFile, furniture.getImageName());
            } else {
                newFurnitureRequest = new FurnitureSwingModel(name, category, price, imageName);
                ApiService.addFurniture(newFurnitureRequest, selectedImageFile);
            }

            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }

            furnitureTable.loadFurniture();
        });
    }

}
package model;

public class FurnitureSwingModel {
    private Long id;
    private String name;
    private String category;
    private double price;
    private String imageName;

    public FurnitureSwingModel() { }

    public FurnitureSwingModel(String name, String category, double price, String imageName) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageName = imageName;
    }

    public FurnitureSwingModel(Long id, String name, String category, double price, String imageName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageName = imageName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getImageName() {
        return imageName;
    }
}

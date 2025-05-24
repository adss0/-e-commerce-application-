package com.application.ecommerce.backend.entities.product;

public class Product {

    private ProductId id;
    private ProductName name;
    private ProductPrice price;

    Product() {
    }

    public Product(ProductName name, ProductPrice price) {
        this.id = ProductId.NONE;
        this.name = name;
        this.price = price;
    }

    public Product(ProductId id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductId getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void setName(ProductName name) {
        this.name = name;
    }

    public void setPrice(ProductPrice price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", Product.class.getSimpleName(), id, name, price);
    }
}

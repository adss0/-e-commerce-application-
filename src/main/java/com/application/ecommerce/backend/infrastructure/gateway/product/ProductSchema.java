package com.application.ecommerce.backend.infrastructure.gateway.product;

import com.application.ecommerce.backend.entities.product.Product;
import com.application.ecommerce.backend.entities.product.ProductId;
import com.application.ecommerce.backend.entities.product.ProductName;
import com.application.ecommerce.backend.entities.product.ProductPrice;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidPriceException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Products")
class ProductSchema {

    public static Product toProduct(ProductSchema schema) {
        try {
            return new Product(
                    new ProductId(schema.getId()),
                    new ProductName(schema.name),
                    new ProductPrice(new BigDecimal(String.valueOf(schema.price)))
            );
        } catch (InvalidIdException | InvalidNameException | InvalidPriceException e) {
            throw new RuntimeException(e);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(length = ProductName.MAX_LENGTH, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    // required constructor
    private ProductSchema() {}

    public ProductSchema(Product product) {
        this.id = product.getId().asLong();
        this.name = product.getName().asString();
        this.price = product.getPrice().asBigDecimal();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductSchema that = (ProductSchema) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

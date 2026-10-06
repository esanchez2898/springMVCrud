package org.example.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100, unique = true, name = "name_category")
    private String nameCategory;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "category") // private CategoryEntity category;
    private List<ProductEntity> products;

    public CategoryEntity() {
    }

    public CategoryEntity(Integer id, String nameCategory, List<ProductEntity> products) {
        this.id = id;
        this.nameCategory = nameCategory;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}



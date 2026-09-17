package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100, unique = true, name = "name_category")
    private String nameCategory;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "category") // private CategoryEntity category;
    private List<ProductEntity> products;

}



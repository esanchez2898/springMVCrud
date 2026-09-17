package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double price;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "cart")
    private List<CartItemEntity> cartItems;


    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "cart") // private CartEntity cart;
    private CustomerEntity customer;

}
package org.example.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "carts")
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

    public CartEntity() {
    }

    public CartEntity(Integer id, Double price, List<CartItemEntity> cartItems, CustomerEntity customer) {
        this.id = id;
        this.price = price;
        this.cartItems = cartItems;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
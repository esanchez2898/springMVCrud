package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "address")
    private CustomerEntity customer;

    @Column(nullable = false, length = 200)
    private String country;

    @Column(nullable = false, length = 200)
    private String state;

    @Column(nullable = false, length = 200)
    private String city;

    @Column(nullable = false, length = 300, columnDefinition = "text")
    private String street;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Column(length = 300, columnDefinition = "text")
    private String deliveryInstructions;


    public AddressEntity() {
    }

    public AddressEntity(Integer id, CustomerEntity customer, String country, String state, String city, String street, String postalCode, String deliveryInstructions) {
        this.id = id;
        this.customer = customer;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.deliveryInstructions = deliveryInstructions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }
}

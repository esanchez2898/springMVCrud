package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}

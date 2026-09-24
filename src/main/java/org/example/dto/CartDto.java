package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.CartItemEntity;


import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double price;

    @NotNull
    private List<CartItemDto> cartItems;

    @NotNull
    private Integer customerId;



}
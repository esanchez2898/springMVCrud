package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull
    @Positive
    private Integer quantity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double totalPrice;

    @NotNull
    private Integer productId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer cartId;

}

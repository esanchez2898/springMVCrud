package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Size(max = 100, min = 5, message = "The size has to be between 5 and 100")
    @NotEmpty(message = "Name can not be empty")
    private String name;

    @NotNull
    @Positive
    private Integer categoryId;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @PositiveOrZero
    private Integer stock;

    @NotNull
    @PositiveOrZero
    private Double discount;

    @NotEmpty
    @Size(min = 10, max = 500)
    private String description;

    @NotEmpty
    @Size(min = 10, max = 255)
    private String imageURL;

}

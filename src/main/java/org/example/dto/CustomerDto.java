package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.AddressEntity;
import org.example.entity.CartEntity;
import org.example.entity.UserEntity;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull
    @Positive
    @Size(max = 12)
    private String customerPhone;

    @NotNull
    @Positive
    private Integer addressId;

    @NotNull
    @Positive
    private Integer userId;

    @NotNull
    @Positive
    private Integer cartId;

}

package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.CustomerEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull
    private Integer customerId;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String country;

    @NotEmpty
    @Size(min = 5, max = 100)
    private String state;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String city;

    @NotEmpty
    @Size(min = 5, max = 300)
    private String street;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String postalCode;

    @Size(max = 300)
    private String deliveryInstructions;

}

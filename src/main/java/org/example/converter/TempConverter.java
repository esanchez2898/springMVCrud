package org.example.converter;

import org.example.dto.*;
import org.example.entity.*;

public interface TempConverter {

    ProductDto entityToDto(ProductEntity productEntity);
    CategoryDto entityToDto(CategoryEntity categoryEntity);
    AddressDto entityToDto(AddressEntity addressEntity);
    CustomerDto entityToDto(CustomerEntity customerEntity);
    UserDto entityToDto(UserEntity userEntity);
    CartDto entityToDto(CartEntity cartEntity);


    ProductEntity dtoToEntity(ProductDto productDto);
    CategoryEntity dtoToEntity(CategoryDto categoryDto);
    AddressEntity dtoToEntity(AddressDto addressDto);
    CustomerEntity dtoToEntity(CustomerDto customerDto);
    UserEntity dtoToEntity(UserDto userDto);
    CartEntity dtoToEntity(CartDto cartDto);



}

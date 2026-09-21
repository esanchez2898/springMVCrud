package org.example.converter;

import org.example.dto.AddressDto;
import org.example.dto.CategoryDto;
import org.example.dto.CustomerDto;
import org.example.dto.ProductDto;
import org.example.entity.AddressEntity;
import org.example.entity.CategoryEntity;
import org.example.entity.CustomerEntity;
import org.example.entity.ProductEntity;

public interface TempConverter {

    ProductDto entityToDto(ProductEntity productEntity);
    CategoryDto entityToDto(CategoryEntity categoryEntity);
    AddressDto entityToDto(AddressEntity addressEntity);
    CustomerDto entityToDto(CustomerEntity customerEntity);
    UserDto entityToDto(CustomerEntity customerEntity);



    ProductEntity dtoToEntity(ProductDto productDto);
    CategoryEntity dtoToEntity(CategoryDto categoryDto);
    AddressEntity dtoToEntity(AddressDto addressDto);
    CustomerEntity dtoToEntity(CustomerDto customerDto);



}

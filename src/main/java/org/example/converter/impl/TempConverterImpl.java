package org.example.converter.impl;

import lombok.AllArgsConstructor;
import org.example.converter.TempConverter;
import org.example.dto.AddressDto;
import org.example.dto.CategoryDto;
import org.example.dto.ProductDto;
import org.example.entity.AddressEntity;
import org.example.entity.CategoryEntity;
import org.example.entity.CustomerEntity;
import org.example.entity.ProductEntity;
import org.example.exception.exceptions.CategoryNotFoundException;
import org.example.repository.AddressRepository;
import org.example.repository.CategoryRepository;
import org.example.repository.CustomerRepository;
import org.example.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class TempConverterImpl implements TempConverter {

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public TempConverterImpl(ModelMapper modelMapper, CategoryRepository categoryRepository, ProductRepository productRepository, AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public ProductDto entityToDto(ProductEntity productEntity) {

        ProductDto returnValue = modelMapper.map(productEntity, ProductDto.class);

        Optional<CategoryEntity> categoryEntityOptional = Optional.ofNullable(productEntity.getCategory());

        if (categoryEntityOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryEntityOptional.get();
            returnValue.setCategoryId(categoryEntity.getId());
        }

        return returnValue;
    }

    @Override
    public CategoryDto entityToDto(CategoryEntity categoryEntity) {
        CategoryDto returnValue = modelMapper.map(categoryEntity, CategoryDto.class);

        Optional<List<ProductEntity>> productsOptional = Optional.ofNullable(categoryEntity.getProducts());

        List<Integer> productsIds = new ArrayList<>();

        if (productsOptional.isPresent()) {
            for (ProductEntity p : productsOptional.get()) {
                productsIds.add(p.getId());
            }
        }

        returnValue.setProductsIds(productsIds);

        return returnValue;
    }

    @Override
    public AddressDto entityToDto(AddressEntity addressEntity) {
        AddressDto returnValue = modelMapper.map(addressEntity, AddressDto.class);

        Optional<CustomerEntity> customerOptional = Optional.ofNullable(addressEntity.getCustomer());

        if (customerOptional.isPresent()) {
            CustomerEntity customerEntity = customerOptional.get();
            returnValue.setCustomerId(customerEntity.getId());
        }

        return returnValue;
    }

    @Override
    public ProductEntity dtoToEntity(ProductDto productDto) {

        ProductEntity returnValue = modelMapper.map(productDto, ProductEntity.class);

        Optional<Integer> categoryIdOptional = Optional.ofNullable(productDto.getCategoryId());

        if (categoryIdOptional.isPresent()) {

            Integer categoryId = categoryIdOptional.get();

            Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryId);

            if (categoryEntityOptional.isEmpty()) {
                throw new CategoryNotFoundException("Category was not Found");
            }
            returnValue.setCategory(categoryEntityOptional.get());
        }

        return returnValue;
    }

    @Override
    public CategoryEntity dtoToEntity(CategoryDto categoryDto) {
        CategoryEntity returnValue = modelMapper.map(categoryDto, CategoryEntity.class);
        Optional<Integer> categoryIdOptional = Optional.ofNullable(categoryDto.getId());

        if(categoryIdOptional.isPresent()) {
            Integer categoryId = categoryIdOptional.get();
            List<ProductEntity> productList = productRepository.findAllByCategoryId(categoryId);
            returnValue.setProducts(productList);
        }

        return returnValue;
    }

    @Override
    public AddressEntity dtoToEntity(AddressDto addressDto) {
        AddressEntity returnValue = modelMapper.map(addressDto, AddressEntity.class);

        Optional<Integer> customerIdOptional = Optional.ofNullable(addressDto.getCustomerId());

        if (customerIdOptional.isPresent()) {
            Integer customerId = customerIdOptional.get();
            CustomerEntity customerEntity = customerRepository.findById(customerId).orElse(null);

            if (customerEntity != null) {
                returnValue.setCustomer(customerEntity);
            }

        }
        return returnValue;
    }
}

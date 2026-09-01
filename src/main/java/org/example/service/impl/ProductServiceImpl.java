package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.converter.TempConverter;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.exception.exceptions.DuplicateFoundException;
import org.example.exception.exceptions.ProductNotFoundException;
import org.example.repository.ProductRepository;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TempConverter tempConverter;
    private final CategoryService categoryService;

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();

        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductEntity productEntity : productEntityList) {
            productDtoList.add(tempConverter.entityToDto(productEntity));
        }

        return productDtoList;
//      return productEntityList
//                .stream()
//                .map(tempConverter::entityToDto)
//                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(Integer categoryId) {

        categoryService.getCategoryById(categoryId);

        List<ProductEntity> productEntityList = productRepository.findAllByCategoryId(categoryId);

        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductEntity productEntity : productEntityList) {
            productDtoList.add(tempConverter.entityToDto(productEntity));
        }
        return productDtoList;

//        return productEntityList
//                .stream()
//                .map(tempConverter::entityToDto)
//                .toList();
    }

    @Override
    public ProductDto getProductById(Integer id) {

        Optional<ProductEntity> productEntity = productRepository.findById(id);

        if (productEntity.isEmpty()) {
            throw new ProductNotFoundException("The product was not found");
        }

        return tempConverter.entityToDto(productEntity.get());
    }


    @Override
    public ProductDto addProduct(ProductDto productDto) {

        categoryService.getCategoryById(productDto.getCategoryId());

        Optional<ProductEntity> productEntityOptional = productRepository.findByName(productDto.getName());

        if (productEntityOptional.isPresent()) {
            throw new DuplicateFoundException("Product with name " + productDto.getName() + " already exist");
        }

        ProductEntity productEntity = tempConverter.dtoToEntity(productDto);
        ProductEntity productEntitySaved = productRepository.save(productEntity);

        return tempConverter.entityToDto(productEntitySaved);
    }

    @Override
    public ProductDto updateProduct(Integer productId, ProductDto productDto) {

        ProductDto currentProduct = getProductById(productId);

        Optional<ProductEntity> productEntityOptional = productRepository.findByName(productDto.getName());

        if (productEntityOptional.isPresent()) {
            if (!Objects.equals(productEntityOptional.get().getId(), currentProduct.getId())) {
                throw new DuplicateFoundException("Product with name " + productDto.getName() + " already exist");
            }
        }

        productDto.setId(productId);
        ProductEntity productEntity = tempConverter.dtoToEntity(productDto);

        ProductEntity productEntitySaved = productRepository.save(productEntity);

        return tempConverter.entityToDto(productEntitySaved);
    }

    @Override
    public void deleteProductById(Integer productId) {
        getProductById(productId);
        productRepository.deleteById(productId);
    }
}

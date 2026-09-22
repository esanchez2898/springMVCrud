package org.example.converter.impl;

import lombok.AllArgsConstructor;
import org.example.converter.TempConverter;
import org.example.dto.*;
import org.example.entity.*;
import org.example.exception.exceptions.CategoryNotFoundException;
import org.example.repository.*;
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
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;

    @Autowired
    public TempConverterImpl(ModelMapper modelMapper, CategoryRepository categoryRepository, ProductRepository productRepository, AddressRepository addressRepository, CustomerRepository customerRepository, AddressRepository addressRepository1, UserRepository userRepository, RoleRepository roleRepository, CartRepository cartRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository1;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
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
    public CustomerDto entityToDto(CustomerEntity customerEntity) {
        CustomerDto returnValue = modelMapper.map(customerEntity, CustomerDto.class);

        Optional<AddressEntity> addressEntityOptional = Optional.ofNullable(customerEntity.getAddress());
        Optional<UserEntity> userEntityOptional = Optional.ofNullable(customerEntity.getUser());
        Optional<CartEntity> cartEntityOptional = Optional.ofNullable(customerEntity.getCart());

        if (addressEntityOptional.isPresent()) {
            AddressEntity addressEntity = addressEntityOptional.get();
            Integer addressId = addressEntity.getId();
            returnValue.setAddressId(addressId);
        }

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            Integer userId = userEntity.getId();
            returnValue.setUserId(userId);
        }

        if (cartEntityOptional.isPresent()) {
            CartEntity cartEntity = cartEntityOptional.get();
            Integer cartId = cartEntity.getId();
            returnValue.setCartId(cartId);
        }

        return returnValue;
    }

    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

        List<Integer> rolesIds = new ArrayList<>();

        if (userEntity.getRoles() != null) {
            for (RoleEntity role : userEntity.getRoles()) {
                rolesIds.add(role.getId());
            }
        }

        returnValue.setRolesIds(rolesIds);

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

    @Override
    public CustomerEntity dtoToEntity(CustomerDto customerDto) {
        CustomerEntity returnValue = modelMapper.map(customerDto, CustomerEntity.class);

        Optional<Integer> addressIdOptional = Optional.ofNullable(customerDto.getAddressId());
        Optional<Integer> userIdOptional = Optional.ofNullable(customerDto.getUserId());
        Optional<Integer> cartIdOptional = Optional.ofNullable(customerDto.getCartId());

        if (addressIdOptional.isPresent()) {
            Integer addressId = addressIdOptional.get();
            AddressEntity addressEntity = addressRepository.findById(addressId).orElse(null);
            if (addressEntity != null) {
                returnValue.setAddress(addressEntity);
            }
        }

        if (userIdOptional.isPresent()) {
            Integer userId = userIdOptional.get();
            UserEntity userEntity = userRepository.findById(userId).orElse(null);
            if (userEntity != null) {
                returnValue.setUser(userEntity);
            }
        }

        if (cartIdOptional.isPresent()) {
            Integer cartId = cartIdOptional.get();
            CartEntity cartEntity = cartRepository.findById(cartId).orElse(null);
            if (cartEntity != null) {
                returnValue.setCart(cartEntity);
            }
        }

        return returnValue;
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {

        UserEntity returnValue = modelMapper.map(userDto, UserEntity.class);
        List<RoleEntity> roleEntities = new ArrayList<>();

        for (Integer id : userDto.getRolesIds()) {
            Optional<RoleEntity> roleEntityOptional = roleRepository.findById(id);
            if (roleEntityOptional.isPresent()) {
                roleEntities.add(roleEntityOptional.get());
            }
        }

        returnValue.setRoles(roleEntities);

        return returnValue;
    }
}

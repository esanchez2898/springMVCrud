package org.example.converter.impl;

import org.example.converter.TempConverter;
import org.example.dto.*;
import org.example.entity.*;
import org.example.exception.exceptions.CategoryNotFoundException;
import org.example.exception.exceptions.ProductNotFoundException;
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
    private final CartItemRepository cartItemRepository;

    @Autowired
    public TempConverterImpl(ModelMapper modelMapper, CategoryRepository categoryRepository, ProductRepository productRepository, AddressRepository addressRepository, CustomerRepository customerRepository, AddressRepository addressRepository1, UserRepository userRepository, RoleRepository roleRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository1;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
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
    public CartDto entityToDto(CartEntity cartEntity) {

        CartDto cartDto = modelMapper.map(cartEntity, CartDto.class);

        Optional<List<CartItemEntity>> cartItemEntityOptional = Optional.ofNullable(cartEntity.getCartItems());
        Optional<CustomerEntity> customerEntityOptional = Optional.ofNullable(cartEntity.getCustomer());

        List<Integer> cartItemsIds = new ArrayList<>();

        if (cartItemEntityOptional.isPresent()) {

            for (CartItemEntity c : cartItemEntityOptional.get()) {

                cartItemsIds.add(c.getId());

            }

            cartDto.setCartItemsIds(cartItemsIds);

        }

        if (customerEntityOptional.isPresent()) {
            Integer customerId = customerEntityOptional.get().getId();
            cartDto.setCustomerId(customerId);
        }

        return cartDto;
    }

    @Override
    public CartItemDto entityToDto(CartItemEntity cartItemEntity) {

        CartItemDto returnValue = modelMapper.map(cartItemEntity, CartItemDto.class);

        Optional<ProductEntity> productEntityOptional = Optional.ofNullable(cartItemEntity.getProduct());
        Optional<CartEntity> cartEntityOptional = Optional.ofNullable(cartItemEntity.getCart());

        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();

            returnValue.setProductId(productEntity.getId());

            Double discount = productEntity.getDiscount();
            Double productPrice = productEntity.getPrice();
            double itemPrice = productPrice - ((discount * productPrice) / 100);
            itemPrice = itemPrice * returnValue.getQuantity();

            returnValue.setTotalPrice(itemPrice);
        }

        cartEntityOptional.ifPresent(cart -> {
            returnValue.setCartId(cart.getId());
        });

        return returnValue;
    }

    @Override
    public RoleDto entityToDto(RoleEntity roleEntity) {

        RoleDto returnValue = modelMapper.map(roleEntity, RoleDto.class);

        Optional<List<UserEntity>> usersOptional = Optional.ofNullable(roleEntity.getUsers());
        List<Integer> usersIds = new ArrayList<>();

        if (usersOptional.isPresent()) {
            List<UserEntity> users = usersOptional.get();

            for (UserEntity user : users) {
                usersIds.add(user.getId());
            }
        }

        returnValue.setUsersIds(usersIds);

        return returnValue;
    }


    // DTO ---> ENTITY


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

        if (categoryIdOptional.isPresent()) {
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

    @Override
    public CartEntity dtoToEntity(CartDto cartDto) {
        CartEntity returnValue = modelMapper.map(cartDto, CartEntity.class);

        Optional<Integer> cartIdOptional = Optional.ofNullable(cartDto.getId());
        Optional<Integer> customerIdOptional = Optional.ofNullable(cartDto.getCustomerId());

        List<CartItemEntity> cartItems = new ArrayList<>();

        if (cartIdOptional.isPresent()) {
            Integer cartId = cartIdOptional.get();
            cartItems = cartItemRepository.findAllByCartId(cartId);
        }
        returnValue.setCartItems(cartItems);

        if (customerIdOptional.isPresent()) {
            Integer customerId = customerIdOptional.get();
            customerRepository.findById(customerId).ifPresent(customerEntity -> {
                returnValue.setCustomer(customerEntity);
            });
        }

        return returnValue;
    }

    @Override
    public CartItemEntity dtoToEntity(CartItemDto cartItemDto) {

        CartItemEntity returnValue = modelMapper.map(cartItemDto, CartItemEntity.class);

        Optional<Integer> productIdOptional = Optional.ofNullable(cartItemDto.getProductId());

        if (productIdOptional.isPresent()) {
            Integer productId = productIdOptional.get();
            Optional<ProductEntity> productEntity = productRepository.findById(productId);

            productEntity.ifPresent(product -> {

                Double discount = product.getDiscount();
                Double productPrice = product.getPrice();
                double itemPrice = productPrice - ((discount * productPrice) / 100);
                Double totalPrice = itemPrice * returnValue.getQuantity();

                returnValue.setTotalPrice(totalPrice);
                returnValue.setProduct(product);
            });
        }

        if (cartItemDto.getCartId() != null) {
            Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartItemDto.getCartId());

            cartEntityOptional.ifPresent(returnValue::setCart);
        }

        return returnValue;
    }

    @Override
    public RoleEntity dtoToEntity(RoleDto roleDto) {
        RoleEntity returnValue = modelMapper.map(roleDto, RoleEntity.class);

        Optional<List<Integer>> usersIdsOptional = Optional.ofNullable(roleDto.getUsersIds());
        List<UserEntity> users = new ArrayList<>();

        if (usersIdsOptional.isPresent()) {
            List<Integer> usersIds = usersIdsOptional.get();


            // homework!!! fix n + 1 !!!!!!

            for (Integer id : usersIds) {
                UserEntity userEntity = userRepository.findById(id).orElse(null); // n + 1
                if (userEntity != null) {
                    users.add(userEntity);
                }
            }

        }

        returnValue.setUsers(users);

        return returnValue;
    }


//    @Override
//    public CartEntity dtoToEntity(CartDto cartDto) {
//
//        CartEntity cartEntity = modelMapper.map(cartDto, CartEntity.class);
//
//        Optional<Integer> customerId = Optional.ofNullable(cartDto.getCustomerId());
//        Optional<List<CartItemDto>> cartItemDtos = Optional.ofNullable(cartDto.getCartItems());
//        Double total = 0.0;
//
//        List<CartItemEntity> cartItemEntities = new ArrayList<>();
//
//        if (cartItemDtos.isPresent()) {
//            for (CartItemDto c : cartItemDtos.get()) {
//                CartItemEntity cartItemEntity = modelMapper.map(c, CartItemEntity.class);
//
//                Optional<Integer> productIdOptional = Optional.ofNullable(c.getProductId());
//                if (productIdOptional.isPresent()) {
//                    Optional<ProductEntity> productEntity = productRepository.findById(productIdOptional.get());
//
//                    if (productEntity.isEmpty()) {
//                        throw new ProductNotFoundException("Product was not found");
//                    }
//                    cartItemEntity.setProductEntity(productEntity.get());
//                    Double productPrice = productEntity.get().getPrice();
//                    Double totalPrice = cartItemEntity.getQuantity() * productPrice;
//                    cartItemEntity.setTotalPrice(totalPrice);
//
//                }
//                cartItemEntities.add(cartItemEntity);
//                total += cartItemEntity.getTotalPrice();
//            }
//            cartEntity.setCartItems(cartItemEntities);
//            cartEntity.setPrice(total);
//
//
//        }
//
//        if (customerId.isPresent()) {
//            Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerId.get());
//            if (customerEntityOptional.isEmpty()) {
//                throw new CustomerNotFoundException("Customer was not found");
//            }
//            cartEntity.setCustomer(customerEntityOptional.get());
//        }
//
//
//
//
//
//        return cartEntity;
//    }
}

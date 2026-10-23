package org.example.service.impl;

import jakarta.inject.Provider;
import org.example.converter.TempConverter;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Provider<UserService> userServiceProvider;
    private TempConverter converter;

    @Autowired
    private void initialize(Provider<UserService> userServiceProvider, TempConverter converter) {
        this.userServiceProvider = userServiceProvider;
        this.converter = converter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return converter.dtoToEntity(userServiceProvider.get().getUserByEmal(username));
    }
}

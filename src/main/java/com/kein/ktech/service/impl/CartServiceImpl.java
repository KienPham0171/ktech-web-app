package com.kein.ktech.service.impl;

import com.kein.ktech.domain.Cart;
import com.kein.ktech.repository.CartRepository;
import com.kein.ktech.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Optional<Cart> getCartIsNotCheckOutById(long userId) {
        return cartRepository.getCartIsNotCheckOutByUserId(userId);
    }

    @Override
    public List<Cart> getCartByUserId(long userId) {
        return cartRepository.getCartByUserId(userId);
    }

    @Override
    public void createNewCart(Cart cart) {
         cartRepository.createNewCart(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        cartRepository.updateCart(cart);
    }
}

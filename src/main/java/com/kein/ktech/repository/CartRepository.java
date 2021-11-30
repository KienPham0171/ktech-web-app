package com.kein.ktech.repository;

import com.kein.ktech.domain.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    List<Cart>  getCartByUserId(long userId);
    Optional<Cart> getCartIsNotCheckOutByUserId(long userId);

    void createNewCart(Cart cart);
    void updateCart(Cart cart);
}

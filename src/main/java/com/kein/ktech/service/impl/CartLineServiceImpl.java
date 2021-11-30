package com.kein.ktech.service.impl;

import com.kein.ktech.domain.CartLine;
import com.kein.ktech.repository.CartLineRepository;
import com.kein.ktech.service.CartLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartLineServiceImpl implements CartLineService {
    CartLineRepository cartLineRepository;
    @Autowired
    public CartLineServiceImpl(CartLineRepository cartLineRepository) {
        this.cartLineRepository = cartLineRepository;
    }

    @Override
    public void createCartLine(CartLine cartLine) {
        cartLineRepository.createCartLine(cartLine);
    }

    @Override
    public Optional<CartLine> getCartLineById(long id) {
        return cartLineRepository.getCartLineById(id);
    }

    @Override
    public void updateCartLine(CartLine cartLine) {
        cartLineRepository.updateCartLine(cartLine);
    }

    @Override
    public void deleteCartline(long id) {
        cartLineRepository.deleteCartline(id);
    }
}

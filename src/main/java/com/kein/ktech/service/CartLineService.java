package com.kein.ktech.service;

import com.kein.ktech.domain.CartLine;

import java.util.Optional;

public interface CartLineService {
    void createCartLine(CartLine cartLine);
    Optional<CartLine> getCartLineById(long id);
    void updateCartLine(CartLine cartLine);

    void deleteCartline(long id);
}

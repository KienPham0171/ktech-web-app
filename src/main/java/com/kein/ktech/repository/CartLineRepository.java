package com.kein.ktech.repository;

import com.kein.ktech.domain.CartLine;

import java.util.Optional;

public interface CartLineRepository {
    void createCartLine(CartLine cartLine);

    Optional<CartLine> getCartLineById(long id);

    void updateCartLine(CartLine cartLine);

    void deleteCartline(long id);
}

package com.gameshop.ecommerce.cart.store.dto;

import lombok.Builder;
import java.util.UUID;

@Builder
public record CartBody(UUID id, Integer quantity) { }
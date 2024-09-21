package com.espeshop.orders.model.dto;

public record OrderItemsResponse(Long id, String sku, Double price, Long quantity) {
}
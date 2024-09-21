package com.espeshop.orders.events;

import com.espeshop.orders.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}

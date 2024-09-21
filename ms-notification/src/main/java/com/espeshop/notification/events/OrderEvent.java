package com.espeshop.notification.events;

import com.espeshop.orders.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}

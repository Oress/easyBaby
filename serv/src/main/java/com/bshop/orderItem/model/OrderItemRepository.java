package com.bshop.orderItem.model;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Integer> {
}

package com.example.PBL6.repository;

import com.example.PBL6.persistance.order.Order;
import com.example.PBL6.persistance.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    @Query
    List<OrderItem> getOrderItemByOrder(Order order);
}

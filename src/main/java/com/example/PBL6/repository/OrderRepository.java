package com.example.PBL6.repository;

import com.example.PBL6.persistance.order.Order;
import com.example.PBL6.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query
    List<Order> getOrdersByUser(User user);
}

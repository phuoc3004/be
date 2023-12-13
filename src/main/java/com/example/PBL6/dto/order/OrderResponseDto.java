package com.example.PBL6.dto.order;

import com.example.PBL6.persistance.order.Order;
import com.example.PBL6.persistance.order.OrderItem;
import com.example.PBL6.persistance.product.ProductVariant;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private Order order;
    private List<OrderItemResponseDto> orderItems;
}

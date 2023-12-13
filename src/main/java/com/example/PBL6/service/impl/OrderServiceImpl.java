package com.example.PBL6.service.impl;

import com.example.PBL6.dto.cart.CartItemDetail;
import com.example.PBL6.dto.order.OrderDto;
import com.example.PBL6.dto.order.OrderItemResponseDto;
import com.example.PBL6.dto.order.OrderRequestDto;
import com.example.PBL6.dto.order.OrderResponseDto;
import com.example.PBL6.persistance.order.Order;
import com.example.PBL6.persistance.order.OrderItem;
import com.example.PBL6.persistance.product.Product;
import com.example.PBL6.persistance.product.ProductVariant;
import com.example.PBL6.persistance.user.User;
import com.example.PBL6.repository.OrderItemRepository;
import com.example.PBL6.repository.OrderRepository;
import com.example.PBL6.repository.ProductRepository;
import com.example.PBL6.repository.ProductVariantRepository;
import com.example.PBL6.service.CartService;
import com.example.PBL6.service.OrderService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderDto saveOrder(User user,
                              String paymentMethod,
                              double totalPrice,
                              String status,
                              String addressDelivery) {
//        User user = AuthenticationUtils.getUserFromSecurityContext();
        System.out.println(user);
        List<CartItemDetail> cartItemDetails = cartService.getAllCartItems(user);
        if (cartItemDetails == null || cartItemDetails.isEmpty()) {
            return null;
        }
        List<OrderItem> orderItems = new ArrayList<>();
        OrderDto orderDto;
        System.out.println(totalPrice);
        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .totalPrice(totalPrice)
                .paymentMethod(paymentMethod)
                .user(user)
                .status(status)
                .addressDelivery(addressDelivery)
                .build();
        orderRepository.save(order);

        for (CartItemDetail cartItemDetail : cartItemDetails) {
            Optional<ProductVariant> productVariant = productVariantRepository.findById(cartItemDetail.getProductVariantId());
            if (productVariant.isPresent()) {
                OrderItem orderItem = OrderItem.builder()
                        .productVariant(productVariant.get())
                        .order(order)
                        .quantity(cartItemDetail.getQuantity())
                        .build();
                orderItems.add(orderItem);

                productVariantRepository.subtractQuantity(cartItemDetail.getProductVariantId(), orderItem.getQuantity());


                orderItemRepository.save(orderItem);
            }
        }
        orderDto = new OrderDto().builder()
                .orderId(order.getId())
                .address(addressDelivery)
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .paymentMethod(order.getPaymentMethod())
                .status(order.getStatus())
                .orderItems(orderItems)
                .build();

        cartService.deleteAllCartItems(user);

        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto saveOrderBuyNow(User user, OrderRequestDto orderRequestDto, String status, String paymentMethod) {
        OrderDto orderDto;

        // Lưu order
        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .totalPrice(orderRequestDto.getAmount())
                .paymentMethod(paymentMethod)
                .user(user)
                .status(status)
                .addressDelivery(orderRequestDto.getAddressDelivery())
                .build();
        orderRepository.save(order);

        Product product = productRepository.getById(orderRequestDto.getProductId());

        System.out.println(product.getId());
        System.out.println(orderRequestDto.getColor());
        System.out.println(orderRequestDto.getSize());

        Optional<ProductVariant> productVariant = productVariantRepository.getProductVariantByProductAndColorAndSize(
                product, orderRequestDto.getColor(), orderRequestDto.getSize());


        if (productVariant.isPresent()) {
            OrderItem orderItem = OrderItem.builder()
                    .productVariant(productVariant.get())
                    .order(order)
                    .quantity(orderRequestDto.getQuantity())
                    .build();

            productVariantRepository.subtractQuantity(productVariant.get().getId(), orderItem.getQuantity());

            orderItemRepository.save(orderItem);
        }


        orderDto = new OrderDto().builder()
                .orderId(order.getId())
                .address(orderRequestDto.getAddressDelivery())
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .paymentMethod(order.getPaymentMethod())
                .status(order.getStatus())
                .build();

        return orderDto;
    }

    @Override
    public List<OrderResponseDto> getAllOrders(User user) {
        List<Order> orders = orderRepository.getOrdersByUser(user);
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        if (orders == null || orders.isEmpty()) {
            return null;
        } else {
            for (Order order : orders) {
                List<OrderItem> orderItems = orderItemRepository.getOrderItemByOrder(order);
                List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();
                for (OrderItem orderItem : orderItems) {
                    ProductVariant productVariant = orderItem.getProductVariant();
                    Product product = productRepository.getById(productVariant.getProduct().getId());
                    OrderItemResponseDto dto = new OrderItemResponseDto().builder()
                            .quantity(orderItem.getQuantity())
                            .product(product)
                            .productVariant(orderItem.getProductVariant())
                            .build();

                    orderItemResponseDtos.add(dto);
                }
                OrderResponseDto orderResponseDto = new OrderResponseDto().builder()
                        .order(order)
                        .orderItems(orderItemResponseDtos)
                        .build();
                orderResponseDtos.add(orderResponseDto);
            }
        }
        return orderResponseDtos;
    }
}

package com.example.PBL6.persistance.product;

import com.example.PBL6.persistance.cart.CartItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_variants")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductVariant {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    @Size(min = 1, max = 5, message = "size just from 1 to 5 characters")
    private String size;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference
    private Product product;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<CartItem> cartItems;
}

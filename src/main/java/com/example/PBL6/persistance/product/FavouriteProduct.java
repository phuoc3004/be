package com.example.PBL6.persistance.product;

import com.example.PBL6.persistance.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "favourite_products")
public class FavouriteProduct {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "products_id", referencedColumnName = "id")
    private Product product;

}

package com.example.PBL6.repository;

import com.example.PBL6.dto.user.UserOrderDto;
import com.example.PBL6.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();

    @Query
    Optional<User> findUserByEmail(String username);

    @Modifying
    @Query(value = "UPDATE users u SET " +
            "u.name = COALESCE(NULLIF(:name, ''), u.name), " +
            "u.address = COALESCE(NULLIF(:address, ''), u.address), " +
            "u.phone = COALESCE(NULLIF(:phone, ''), u.phone), " +
            "u.avatar = COALESCE(NULLIF(:avatar, ''), u.avatar), " +
            "u.gender = COALESCE(NULLIF(:gender, ''), u.gender) " +
            "WHERE u.id = :id", nativeQuery = true)
    void updateUserFields(@Param("id") Integer id,
                          @Param("name") String name,
                          @Param("address") String address,
                          @Param("phone") String phone,
                          @Param("avatar") String avatar,
                          @Param("gender") String gender);

    @Query(value = "SELECT\n" +
            "    u.id,\n" +
            "    u.name,\n" +
            "    u.email,\n" +
            "    u.address,\n" +
            "    u.phone,\n" +
            "    COALESCE(SUM(o.total_price), 0) AS total_order_price\n" +
            "FROM\n" +
            "    clothes_store.users u\n" +
            "LEFT JOIN\n" +
            "    clothes_store.orders o ON u.id = o.users_id\n" +
            "GROUP BY\n" +
            "    u.id, u.name, u.email;", nativeQuery = true)
    List<Object[]> getAllUserOrder();
}

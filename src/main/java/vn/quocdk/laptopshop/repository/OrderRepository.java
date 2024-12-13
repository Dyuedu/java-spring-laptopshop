package vn.quocdk.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.quocdk.laptopshop.domain.Order;
import vn.quocdk.laptopshop.domain.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAll();

    List<Order> findByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = ?1 WHERE o.id = ?2")
    void updateOrderStatus(String status, Long id);
}

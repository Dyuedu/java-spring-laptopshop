package vn.quocdk.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quocdk.laptopshop.domain.Cart;
import vn.quocdk.laptopshop.domain.CartDetail;
import vn.quocdk.laptopshop.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    boolean existsByCartAndProduct(Cart cart, Product product);

    CartDetail findByCartAndProduct(Cart cart, Product product);

    Optional<CartDetail> findById(long id);

    void deleteById(long id);

}

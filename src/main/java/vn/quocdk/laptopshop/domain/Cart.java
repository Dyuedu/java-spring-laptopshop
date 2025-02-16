package vn.quocdk.laptopshop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    // Cart Detail (1 - n)
    @OneToMany(mappedBy = "cart")
    List<CartDetail> cartDetails;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // user_id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    // sum
    @Min(value = 0)
    private int sum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

}

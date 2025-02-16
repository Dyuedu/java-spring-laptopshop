package vn.quocdk.laptopshop.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.quocdk.laptopshop.service.CartService;

class CartRequest {
    private int quantity;
    private long productId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

@RestController
public class CartAPI {
    private final CartService cartService;

    public CartAPI(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/api/add-product-to-cart")
    public ResponseEntity<Integer> addProductToCart(
            @RequestBody CartRequest cartRequest,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        this.cartService.handleAddProductToCart(email, cartRequest.getProductId(), session,
                cartRequest.getQuantity());
        int sum = (int) session.getAttribute("cartSum");
        return ResponseEntity.ok().body(sum);
    }

}
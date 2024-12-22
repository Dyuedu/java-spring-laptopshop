package vn.quocdk.laptopshop.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.quocdk.laptopshop.domain.Product;
import vn.quocdk.laptopshop.domain.User;
import vn.quocdk.laptopshop.domain.dto.RegisterDTO;
import vn.quocdk.laptopshop.service.ProductService;
import vn.quocdk.laptopshop.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getHomepage(Model model, HttpServletRequest request) {
        List<Product> top4OfficeLaptop = productService.getTop4OfficeLaptop();
        List<Product> top4BusinessLaptop = productService.getTop4BusinessLaptop();
        List<Product> top4ThinLaptop = productService.getTop4ThinLaptop();
        List<Product> top4GamingLaptop = productService.getTop4GamingLaptop();
        model.addAttribute("office", top4OfficeLaptop);
        model.addAttribute("business", top4BusinessLaptop);
        model.addAttribute("thin", top4ThinLaptop);
        model.addAttribute("gaming", top4GamingLaptop);
        return "client/homepage/show";
    }

    @GetMapping("register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @GetMapping("login")
    public String getLoginPage() {
        return "client/auth/login";
    }

    @PostMapping("register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "client/auth/register";
        }

        User user = userService.registerDTOtoUser(registerDTO);
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("User"));
        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("access-denied")
    public String getDeniedPage() {
        return "client/auth/denied";
    }

    @GetMapping("thankyou")
    public String getThankYouPage() {
        return "client/cart/thankyou";
    }

    @GetMapping("products")
    public String getProductPage(Model model,
            @RequestParam("page") Optional<String> pageOptional,
            @RequestParam("min-price") Optional<String> minPrice,
            @RequestParam("max-price") Optional<String> maxPrice,
            @RequestParam("brand") Optional<String> brand,
            @RequestParam("price-range") Optional<String> priceRange) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }
        Pageable pageable = PageRequest.of(page - 1, 6);
        Page<Product> prs = this.productService.getProductWithBrandList(pageable, brand);
        List<Product> products = prs.getContent();
        int totalPages = prs.getTotalPages();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "client/product/show";
    }

}

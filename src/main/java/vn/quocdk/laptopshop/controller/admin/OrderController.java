package vn.quocdk.laptopshop.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.quocdk.laptopshop.domain.Order;
import vn.quocdk.laptopshop.domain.OrderDetail;
import vn.quocdk.laptopshop.service.OrderService;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;
    private static final String ORDER = "order";

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("admin/order")
    public String getDashboard(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Order> ordersRaw = orderService.getAllOrders(pageable);
        List<Order> orders = ordersRaw.getContent();
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersRaw.getTotalPages());
        return "admin/order/show";
    }

    @GetMapping("admin/order/update/{id}")
    public String getUpdateOrderPage(@PathVariable long id, Model model) {
        long orderId = id;
        Order order = orderService.getOrderById(orderId);
        model.addAttribute(ORDER, order);
        return "admin/order/update";
    }

    @PostMapping("admin/order/update")
    public String postUpdateOrder(@ModelAttribute("order") Order order) {
        orderService.handleUpdateOrder(order);
        return "redirect:/admin/order";
    }

    @GetMapping("admin/order/delete/{id}")
    public String getDeleteOrderPage(@PathVariable long id, Model model) {
        Order order = new Order();
        order.setId(id);
        model.addAttribute(ORDER, order);
        return "admin/order/delete";
    }

    @PostMapping("admin/order/delete")
    public String postDeleteOrder(@ModelAttribute("order") Order order) {
        long orderId = order.getId();
        Order currentOrder = orderService.getOrderById(orderId);
        orderService.handleDeleteOrder(currentOrder);
        return "admin/order/show";
    }

    @GetMapping("admin/order/info/{id}")
    public String getOrderInfoPage(Model model, @PathVariable long id) {
        long orderId = id;
        Order order = orderService.getOrderById(orderId);
        model.addAttribute(ORDER, order);
        return "admin/order/orderInfo";
    }

    @GetMapping("admin/order/detail/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        long orderId = id;
        Order order = orderService.getOrderById(orderId);
        List<OrderDetail> orderDetails = order.getOrderDetails();
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("id", orderId);
        return "admin/order/orderDetail";
    }

}

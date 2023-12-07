package com.example.springmvcpractice.serviceImpl;

import com.example.springmvcpractice.models.Order;
import com.example.springmvcpractice.models.Users;
import com.example.springmvcpractice.repositories.OrderRepositories;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.function.Function;

@Service
public class OrderServiceImpl {

    private OrderRepositories orderRepositories;
    private UsersServiceImpl usersService;

    @Autowired
    public OrderServiceImpl(OrderRepositories orderRepositories, UsersServiceImpl usersService) {
        this.orderRepositories = orderRepositories;
        this.usersService = usersService;
    }

    public Function<Order, Order> saveOrder = (order) -> orderRepositories.save(order);

    public String makePayment(HttpSession session, Model model){
        Users user = usersService.findUserById.apply((Long) session.getAttribute("userID"));
        Order order = (Order) session.getAttribute("order");
        if (user.getBalance().doubleValue()<order.getTotalPrice().doubleValue()){
            model.addAttribute("paid","Insufficient balance in your account!");
            return "checkout";
        }
        user.setBalance(user.getBalance().subtract(order.getTotalPrice()));
        usersService.saveUser.apply(user);
        Order order1 = saveOrder.apply(order);
        session.setAttribute("order", null);
        model.addAttribute("paid", "Payment was successful!");
        return "successfully-paid";
    }
}

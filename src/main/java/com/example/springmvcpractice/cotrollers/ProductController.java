package com.example.springmvcpractice.cotrollers;

import com.example.springmvcpractice.dtos.ProductDto;
import com.example.springmvcpractice.models.Order;
import com.example.springmvcpractice.models.Product;
import com.example.springmvcpractice.models.Users;
import com.example.springmvcpractice.serviceImpl.OrderServiceImpl;
import com.example.springmvcpractice.serviceImpl.ProductServiceImpl;
import com.example.springmvcpractice.serviceImpl.UsersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductServiceImpl productService;
    private UsersServiceImpl usersService;
    private OrderServiceImpl orderService;

    @Autowired
    public ProductController(ProductServiceImpl productService, UsersServiceImpl usersService, OrderServiceImpl orderService) {
        this.productService = productService;
        this.usersService = usersService;
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ModelAndView findAllProducts(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Product> productList = productService.findAllProducts.get();
        return new ModelAndView("dashboard")
                .addObject("products", productList)
                .addObject("cartItems", "Cart Items: "+session.getAttribute("cartItems"));
    }

    @GetMapping("/add-cart")
    public String addToCart(@RequestParam(name = "cart") Long id, HttpServletRequest request, Model model){
        productService.addProductToCart(id, request);
        return "redirect:/products/all";
    }



    @GetMapping("/payment")
    public String checkOut(HttpSession session, Model model){
        productService.checkOutCart(session, model);
        model.addAttribute("paid", "");
        return "checkout";
    }

    @GetMapping("/pay")
    public String orderPayment(HttpSession session, Model model){
        return orderService.makePayment(session, model);
    }

    @GetMapping("/admin-view")
    public ModelAndView adminViewProducts(HttpServletRequest request){

        List<Product> productList = productService.findAllProducts.get();
        return new ModelAndView("admin-view-product")
                .addObject("products", productList);
    }

    @GetMapping("/admin-add-products")
    public ModelAndView adminAddProductsPage(HttpServletRequest request, Model model){
        return new ModelAndView("admin-add-products")
                .addObject("products", new ProductDto());
    }

    @GetMapping("/delete-products")
    public String deleteProducts(@RequestParam Long id, HttpServletRequest request, Model model){
        productService.deleteProducts(id, request);
        return "redirect:/products/admin-view";
    }

    @GetMapping("/edit-products")
    public ModelAndView editProductsPage(@RequestParam Long id, HttpSession session, HttpServletRequest request){
        session.getAttribute("userID");
        Product product = productService.findById.apply(id);
        return new ModelAndView("admin-edit-products")
                .addObject("products", product);
    }

    @PostMapping ("/edit-products")
    public String editProduct(@ModelAttribute ProductDto productDto, HttpServletRequest request){
        request.setAttribute("productId", productDto.getId());
        Product product = productService.saveProducts.apply(new Product(productDto));
        return "redirect:/products/admin-view";
    }


}

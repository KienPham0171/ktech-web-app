package com.kein.ktech.controller;

import com.kein.ktech.domain.Product;
import com.kein.ktech.service.CategoryService;
import com.kein.ktech.service.ProductService;
import com.kein.ktech.service.UserService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    ProductService productService;
    UserService userService;
    CategoryService catService;

    @Autowired
    public AdminController(ProductService productService, UserService userService, CategoryService catService) {
        this.productService = productService;
        this.userService = userService;
        this.catService = catService;
    }

    @GetMapping("/dashboard")
    public String dashboard()
    {
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String products(@RequestParam(name = "page",defaultValue = "1") int page, Model model)
    {

        List<Product> products = productService.getProducts();
        System.out.println(products.size());
        model.addAttribute("products",products);
        model.addAttribute("pages",2);
        return "admin/products";
    }
    @GetMapping("/users")
    public String users(Model model)
    {
        return "admin/users";
    }
    @GetMapping("/categories")
    public String categories(Model model)
    {
        return "admin/categories";
    }
    @GetMapping("/orders")
    public String orders(Model model)
    {
        return "admin/orders";
    }
    @GetMapping("/feedbacks")
    public String feedbacks(Model model)
    {
        return "admin/feedbacks";
    }
}

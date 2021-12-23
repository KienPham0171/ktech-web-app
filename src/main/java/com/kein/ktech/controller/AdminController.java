package com.kein.ktech.controller;

import com.kein.ktech.constant.InvoiceStatus;
import com.kein.ktech.domain.Invoice;
import com.kein.ktech.domain.Product;
import com.kein.ktech.service.*;
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
    StatsService statsService;
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    public AdminController(ProductService productService, UserService userService,
                           CategoryService catService,StatsService statsService) {
        this.productService = productService;
        this.userService = userService;
        this.catService = catService;
        this.statsService = statsService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model)
    {
        List<Object []> catStats =statsService.catStats();
        model.addAttribute("catStats", catStats);
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String products(@RequestParam(name = "page",defaultValue = "1") int page,
                           @RequestParam(name = "size",defaultValue = "4") int size,
                           Model model)
    {

        List<Product> products = productService.getProductsByPageAndSize(page,size);
        System.out.println(products.size());
        model.addAttribute("products",products);
        model.addAttribute("pages",Math.ceil(productService.countProducts()/size));
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
        List<Invoice> notConfirmedList = invoiceService.getInvoicesByStatus(InvoiceStatus.NOT_CONFIRMED);
        model.addAttribute("notConfirmedList",notConfirmedList);
        return "admin/orders";
    }
    @GetMapping("/feedbacks")
    public String feedbacks(Model model)
    {
        return "admin/feedbacks";
    }
}

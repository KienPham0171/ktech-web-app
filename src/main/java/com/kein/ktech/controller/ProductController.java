package com.kein.ktech.controller;

import com.kein.ktech.domain.*;
import com.kein.ktech.domain.dto.ProductDTO;
import com.kein.ktech.service.CategoryService;
import com.kein.ktech.service.FileService;
import com.kein.ktech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;


import java.io.IOException;
import java.util.*;

@Controller
public class ProductController {
    private CategoryService catService;
    private ProductService productService;
    private FileService fileService;

    @Autowired
    public ProductController(CategoryService catService,
                             ProductService productService,FileService fileService) {
        this.productService = productService;
        this.catService = catService;
        this.fileService = fileService;
    }
    @ModelAttribute
    public void init(Model model){
        List<OptionProductDetails> opts = new ArrayList<OptionProductDetails>(10);
        List<SmallImagesProduct> smallImages = new ArrayList<>();
        ProductDetails productDetails = new ProductDetails(opts,smallImages);
        ProductDTO newProduct = new ProductDTO(productDetails);
        newProduct.setId(-1);
        List<Category> categories = catService.getCategories();
        Map<String, Object> attr = new HashMap<String, Object>();
        for (int i = 0; i < 3 ; i++){
            opts.add(new OptionProductDetails());
        }
        for (int i = 0; i < 4 ; i++){
            smallImages.add(new SmallImagesProduct());
        }

        attr.put("product",newProduct);
        attr.put("cats",categories);
        model.addAllAttributes(attr);
    }

    @GetMapping("/newProduct")
    public String newProduct(Model model)
    {

        return "admin/data/newProduct";
    }
    @GetMapping("/admin/editProduct/{id}")
    public String editProduct(Model model,@PathVariable("id") String id){
        Product product = null;
        Optional<Product> p =productService.findProductById(Long.parseLong(id));
        if(p.isPresent()) product = p.get();else{
            return "admin/data/newProduct";
        }

        model.addAttribute("product",product);
        return "admin/data/newProduct";
    }

    @PostMapping("/admin/updateProduct")
    public Object updateProduct(@Validated(ProductDTO.CustomValid.class) @ModelAttribute(name="product") ProductDTO product,
                                BindingResult result, Model model,
                                @RequestParam("cat") String cat){

        Product p = productService.findProductById(product.getId()).get();
        if(result.hasErrors()){
            System.err.println(result.getModel());

            String bigImg = p.getImage();
            List<String> sList = new ArrayList<>();
            p.getProductDetails().getSmallImagesProducts().forEach(s -> sList.add(s.getImage()));

            sList.add(0,bigImg);
            model.addAttribute("listImg",sList);
            return "admin/data/newProduct";
        }

        p.setProductName(product.getProductName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setAuthor(product.getAuthor());
        Optional<Category> t = catService.getCategoryByName(cat);
        if(t.isPresent()) p.setCategoryId(t.get());
        productService.updateProduct(p);
        return new RedirectView("/admin/products");
    }

    @PostMapping("/saveNewProduct")
    public String saveNewProduct( @Validated({ProductDTO.BaseInfo.class}) @ModelAttribute(name = "product") ProductDTO product, BindingResult result,
                                 //@RequestParam("primeImage") MultipartFile multipartFile,
                                  @RequestParam(name = "isUpdate",defaultValue = "false") boolean isUpdate,
                                 @RequestParam("cat") String cat,Model model) throws IOException {
        if(result.hasErrors()){
            System.err.println("loi loi loi");
            System.err.println(result.getModel());
            System.err.println("product.author: "+ product.getAuthor());
            return "admin/data/newProduct";

        }else{
            MultipartFile multipartFile = product.getFile();
            if(multipartFile!= null)
            {
                String url =  fileService.saveMultipartFile(multipartFile);
                if (url != null) {
                    product.setImage(url);
                } else {
                    System.err.print("encounter error!!");
                }
            }


            List<SmallImagesProduct> imgs = product.getProductDetails().getSmallImagesProducts();
            imgs.forEach(smallImage ->{
                MultipartFile file = smallImage.getMultipartFile();
                if(file != null)
                {
                    String url = fileService.saveMultipartFile(file);
                    if(url!=null)
                    {
                        smallImage.setImage(url);
                    }
                    else {
                        System.err.println("encounter error!!");
                    }
                }
            });

            Optional<Category> t = catService.getCategoryByName(cat);
            if(t.isPresent()) product.setCategoryId(t.get());
            product.getProductDetails().getSmallImagesProducts().forEach(smallImage->{
                smallImage.setProductDetailsId(product.getProductDetails());
            });

            Product realProduct = new Product(product);
            realProduct.getProductDetails().setProductId(realProduct);
            realProduct.getProductDetails().getOptionProductDetails().forEach( optDetails ->{
                optDetails.setProductDetailsId(realProduct.getProductDetails());
            });

            productService.saveProduct(realProduct);
            return "productDetails";
        }

    }
    @GetMapping("/productDetails/{id}")
    public String productDetails(@PathVariable(name = "id") long id,Model model)
    {
        Optional<Product> product = productService.findProductById(id);
        if(product.isPresent())
        {
            System.err.println(product.get().getProductName());
            model.addAttribute("product",product.get());

        }
        return "productDetails";
    }
}

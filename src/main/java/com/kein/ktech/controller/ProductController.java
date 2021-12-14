package com.kein.ktech.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kein.ktech.domain.*;
import com.kein.ktech.service.CategoryService;
import com.kein.ktech.service.FileService;
import com.kein.ktech.service.ProductService;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
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
        Product newProduct = new Product(productDetails);
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

    @PostMapping("/saveNewProduct")
    public String saveNewProduct( @Valid @ModelAttribute(name = "product") Product product, BindingResult result,
                                 @RequestParam("primeImage") MultipartFile multipartFile,
                                 @RequestParam("cat") String cat,Model model) throws IOException {
        if(result.hasErrors()){
            System.err.println("loi loi loi");
            return "admin/data/newProduct";
        }else{
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
            product.getProductDetails().setProductId(product);
            product.getProductDetails().getOptionProductDetails().forEach( optDetails ->{
                optDetails.setProductDetailsId(product.getProductDetails());
            });

            productService.saveProduct(product);
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

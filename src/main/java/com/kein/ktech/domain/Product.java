package com.kein.ktech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kein.ktech.domain.dto.ProductDTO;
import com.kein.ktech.validator.FileInputValidator;
import com.kein.ktech.validator.OptionPrices;
import com.kein.ktech.validator.ProductDetailValidator;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    public interface BaseInfo{

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Can not be null!",groups = BaseInfo.class)
    private String productName;
    @NotBlank(message = "Can not be null!",groups = BaseInfo.class)
    private String description;
    @Max(value = 10000,message = "max 10.000$!",groups = BaseInfo.class)
    @Min(value = 1,message= "Min is 1$!",groups = BaseInfo.class)
    private double price;
    private String image;
    @NotBlank(message = "Can not be null!",groups = BaseInfo.class)
    private String author;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
    @JsonIgnore
    @OneToOne(mappedBy ="productId",cascade = CascadeType.ALL)
    //@ProductDetailValidator
    @Valid
    private ProductDetails productDetails;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Comment> comments;

    @Transient
    //@NotNull(message ="Can not be null!")
    //@FileInputValidator(message = "File input can not be null")
    private MultipartFile file ;


    public Product(ProductDetails pd){
        this.productDetails = pd ;
    }
    public Product(ProductDTO dto){
        this.productName = dto.getProductName();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.image = dto.getImage();
        this.author = dto.getAuthor();
        this.categoryId = dto.getCategoryId();
        this.productDetails = dto.getProductDetails();
        this.comments = dto.getComments();
    }
}

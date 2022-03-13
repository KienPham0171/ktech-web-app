package com.kein.ktech.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kein.ktech.domain.Category;
import com.kein.ktech.domain.Comment;
import com.kein.ktech.domain.Product;
import com.kein.ktech.domain.ProductDetails;
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


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ProductDTO {

    public interface BaseInfo{

    }
    public interface CustomValid{

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Can not be null!",groups = {BaseInfo.class,CustomValid.class})
    @NotNull
    private String productName;
    @NotBlank(message = "Can not be null!",groups = {BaseInfo.class,CustomValid.class})
    @NotNull
    private String description;
    @Max(value = 10000,message = "max 10.000$!",groups = {BaseInfo.class,CustomValid.class})
    @Min(value = 1,message= "Min is 1$!",groups = {BaseInfo.class,CustomValid.class})
    @NotNull
    private double price;
    private String image;
    @NotBlank(message = "Can not be null!",groups = {BaseInfo.class,CustomValid.class})
    @NotNull(message="Can not be null")
    private String author;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
    @JsonIgnore
    @OneToOne(mappedBy ="productId",cascade = CascadeType.ALL)
    @ProductDetailValidator(groups = BaseInfo.class)
    @Valid
    private ProductDetails productDetails;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Comment> comments;

    @Transient
    @NotNull(message ="Can not be null!",groups = {BaseInfo.class})
    @FileInputValidator(message = "File input can not be null",groups = {BaseInfo.class})
    private MultipartFile file ;


    public ProductDTO(ProductDetails pd){
        this.productDetails = pd ;
    }
    public ProductDTO(Product dto){
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

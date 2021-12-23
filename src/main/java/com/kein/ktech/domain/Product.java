package com.kein.ktech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kein.ktech.validator.FileInputValidator;
import com.kein.ktech.validator.OptionPrice;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Can not be null!")
    private String productName;
    @NotBlank(message = "Can not be null!")
    private String description;
    @Max(value = 10000,message = "max 10.000$!")
    @Min(value = 1,message= "Min is 1$!")
    private double price;
    private String image;
    @NotBlank(message = "Can not be null!")
    private String author;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;


    @FileInputValidator
    @JsonIgnore
    @OneToOne(mappedBy ="productId",cascade = CascadeType.ALL)
    private ProductDetails productDetails;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Comment> comments;

    @Transient
    @NotNull(message ="Can not be null!")
    @FileInputValidator(message = "File input can not be null")
    private MultipartFile file ;

    @Override
    public String toString() {
        return "something";
    }
    public Product(ProductDetails pd){
        this.productDetails = pd ;
    }
}

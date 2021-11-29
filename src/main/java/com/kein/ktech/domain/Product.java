package com.kein.ktech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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
    private String productName;
    private String description;
    private double price;
    private String image;
    private String author;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
    @JsonIgnore
    @OneToOne(mappedBy ="productId",cascade = CascadeType.ALL)
    private ProductDetails productDetails;

    @Override
    public String toString() {
        return "something";
    }
    public Product(ProductDetails pd){
        this.productDetails = pd ;
    }
}

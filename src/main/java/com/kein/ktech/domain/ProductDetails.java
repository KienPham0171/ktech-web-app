package com.kein.ktech.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Product_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "product_information")
    private String productInformation;
    private boolean available;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @OneToMany(mappedBy ="productDetailsId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OptionProductDetails> optionProductDetails;
    @OneToMany(mappedBy = "productDetailsId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SmallImagesProduct> smallImagesProducts;


    public ProductDetails(List<OptionProductDetails> opts , List<SmallImagesProduct> imgs)
    {
        this.optionProductDetails = opts;
        this.smallImagesProducts = imgs;
    }
}

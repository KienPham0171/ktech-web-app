package com.kein.ktech.domain;

import com.kein.ktech.domain.dto.ProductDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "option_product_details")
@AllArgsConstructor
public class OptionProductDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "not null",groups = {ProductDTO.BaseInfo.class,ProductDTO.CustomValid.class})
    private String name;
    @Column(name = "price_option")
    @NotNull(message = "not null",groups = {ProductDTO.BaseInfo.class,ProductDTO.CustomValid.class})
    @Max(value = 10000, message = "max 10.000$",groups = {ProductDTO.BaseInfo.class,ProductDTO.CustomValid.class})
    @Min(value = 1,message = "1$",groups = {ProductDTO.BaseInfo.class,ProductDTO.CustomValid.class})
    private double priceOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_details_id")
    private ProductDetails productDetailsId;

    public OptionProductDetails(){
        this.name = "Default";
        this.priceOption= 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceOption() {
        return priceOption;
    }

    public void setPriceOption(double priceOption) {
        this.priceOption = priceOption;
    }

    public ProductDetails getProductDetailsId() {
        return productDetailsId;
    }

    public void setProductDetailsId(ProductDetails productDetailsId) {
        this.productDetailsId = productDetailsId;
    }
}

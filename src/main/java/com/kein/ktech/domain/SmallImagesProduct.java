package com.kein.ktech.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "small_Images_Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmallImagesProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_details_id")
    private ProductDetails productDetailsId;
    @Transient
    private MultipartFile multipartFile;

    public SmallImagesProduct(String image)
    {
        this.image = image;
    }

}

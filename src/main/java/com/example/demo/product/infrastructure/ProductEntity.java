package com.example.demo.product.infrastructure;

import com.example.demo.product.domain.Product;
import com.example.demo.product.domain.ProductStatus;
import com.example.demo.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column
    private Long price;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @Column
    private Long count;
    @Column
    private LocalDateTime registDt;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserEntity seller;

    public static ProductEntity from(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.id = product.getId();
        productEntity.name = product.getName();
        productEntity.price = product.getPrice();
        productEntity.status = product.getStatus();
        productEntity.count = product.getCount();
        productEntity.registDt = product.getRegistDt();
        productEntity.seller = UserEntity.from(product.getSeller());
        return productEntity;
    }

    public Product toModel() {
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .status(status)
                .count(count)
                .registDt(registDt)
                .seller(seller.toModel())
                .build();
    }
}

package com.example.demo.product.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.order.domain.OrderHistory;
import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Product {
    private final Long id;
    private final String name;
    private final Long price;
    private final ProductStatus status;
    private final Long count;
    private final LocalDateTime registDt;
    private final LocalDateTime updateDt;
    private final User seller;

    @Builder
    public Product(Long id, String name, Long price, ProductStatus status, Long count, LocalDateTime registDt, LocalDateTime updateDt, User seller) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.count = count;
        this.registDt = registDt;
        this.updateDt = updateDt;
        this.seller = seller;
    }

    public static Product from(User seller, ProductCreate productCreate, ClockHolder clockHolder) {
        return Product.builder()
                .name(productCreate.getProductNm())
                .price(productCreate.getProductPrice())
                .status(ProductStatus.SALE)
                .count(productCreate.getCount())
                .registDt(clockHolder.getNowDt())
                .seller(seller)
                .build();
    }

    public Product update(ProductUpdate productUpdate, List<OrderHistory> orderHistories, ClockHolder clockHolder) {
        ProductStatus newStatus = productUpdate.getCount().isPresent() ?
                ProductStatusCalculator.calculateNewStatus(productUpdate.getCount().get(), orderHistories) : this.status;

        return Product.builder()
                .id(this.id)
                .name(productUpdate.getProductNm().orElse(this.name))
                .price(productUpdate.getProductPrice().orElse(this.price))
                .status(newStatus)
                .count(productUpdate.getCount().orElse(this.count))
                .registDt(this.registDt)
                .updateDt(clockHolder.getNowDt())
                .seller(this.seller)
                .build();
    }

    public Product decreaseCount(List<OrderHistory> orderHistories, ClockHolder clockHolder) {
        Long newCount = count - 1;

        ProductStatus newStatus = ProductStatusCalculator.calculateNewStatus(newCount, orderHistories);

        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .status(newStatus)
                .count(newCount)
                .registDt(registDt)
                .updateDt(clockHolder.getNowDt())
                .seller(seller)
                .build();
    }
}
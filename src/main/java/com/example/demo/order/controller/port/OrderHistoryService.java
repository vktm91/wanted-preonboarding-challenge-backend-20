package com.example.demo.order.controller.port;

import com.example.demo.order.domain.OrderHistory;

import java.util.List;

public interface OrderHistoryService {
    List<OrderHistory> getOrderHistoriesByProductNo(long productId);
    List<OrderHistory> getOrderHistoriesByBuyerId(long buyerId);
    List<OrderHistory> getOrderHistoriesBySellerId(long sellerId);
    List<OrderHistory> getOrderHistoriesByProductIdAndBuyerId(long productId, long buyerId);
    List<OrderHistory> getOrderHistoriesByPorductIdAndSellerId(long productId, long sellerId);
}
package ro.msg.learning.shop.service.strategy;

import ro.msg.learning.shop.model.OrderDetailProductIdAndQuantity;
import ro.msg.learning.shop.model.Stock;

import java.util.List;

public interface LocationStrategy {
    List<Stock> getStockLocations(List<OrderDetailProductIdAndQuantity> productIdAndQuantityList);
}

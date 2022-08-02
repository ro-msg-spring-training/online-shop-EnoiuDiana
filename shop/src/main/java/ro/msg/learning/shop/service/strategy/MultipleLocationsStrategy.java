package ro.msg.learning.shop.service.strategy;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

import java.util.*;

@RequiredArgsConstructor
public class MultipleLocationsStrategy implements LocationStrategy{
    private final StockService stockService;

    @Override
    public List<Stock> getStockLocations(List<OrderDetail> productIdAndQuantityList) {
        List<Stock> stocks = new ArrayList<>();
        for(OrderDetail productIdAndQuantity : productIdAndQuantityList) {
            List<Stock> foundStocks = stockService.findAllStocksByProductId(productIdAndQuantity.getProduct().getId());
            if(foundStocks.isEmpty()) {
                throw new NotFoundException("Not found product in stock");
            }
            Optional<Stock> stock = foundStocks.stream()
                    .filter(s -> s.getQuantity() >= productIdAndQuantity.getQuantity())
                    .max(Comparator.comparing(Stock::getQuantity));
            if(!stock.isPresent()) {
                throw new NotFoundException("Quantity not enough");
            }
            stocks.add(stock.get());
        }
        return stocks;
    }
}

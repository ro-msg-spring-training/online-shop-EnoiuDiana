package ro.msg.learning.shop.service.strategy;


import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

import java.util.*;

public class MultipleLocationsStrategy implements LocationStrategy{

    private final StockRepository stockRepository;

    public MultipleLocationsStrategy(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> getStockLocations(List<OrderDetail> productIdAndQuantityList) {
        List<Stock> stocks = new ArrayList<>();
        for(OrderDetail productIdAndQuantity : productIdAndQuantityList) {
            List<Stock> foundStocks = stockRepository.findAllByProductId(productIdAndQuantity.getProduct().getId());
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

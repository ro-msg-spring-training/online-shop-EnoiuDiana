package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public void updateStock(Stock stock) {
        if(stockRepository.existsById(stock.getId())) {
            stockRepository.save(stock);
        } else {
            throw new NotFoundException("Stock not found");
        }
    }

    public List<Stock> findAllStocksByProductId(int productId) {
        return stockRepository.findAllByProductId(productId);
    }

    public List<Stock> findAllStocksByLocationId(int locationId) {
        return stockRepository.findAllByLocationId(locationId);
    }
}

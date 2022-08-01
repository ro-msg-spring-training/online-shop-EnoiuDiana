package ro.msg.learning.shop.service.strategy;

import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetailProductIdAndQuantity;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class SingleLocationStrategy implements LocationStrategy{

    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;

    public SingleLocationStrategy(StockRepository stockRepository, LocationRepository locationRepository) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
    }

    private boolean stockHasNeededProduct(Stock stock,
                                          List<OrderDetailProductIdAndQuantity> productIdAndQuantityList ) {

        List<OrderDetailProductIdAndQuantity> found = productIdAndQuantityList.stream().filter(p -> stock.getProduct().getId() == p.getProductId() &&
                stock.getQuantity() > p.getQuantity()).collect(Collectors.toList());
        return !found.isEmpty();
    }

    @Override
    public List<Stock> getStockLocations(List<OrderDetailProductIdAndQuantity> productIdAndQuantityList) {

        List<Location> locations = locationRepository.findAll();
        for(Location location : locations) {
            List<Stock> stocksByLocation = stockRepository.findAllByLocationId(location.getId());
            List<Stock> foundStocksForSingleLocation =
                    stocksByLocation.stream()
                            .filter(stock -> stockHasNeededProduct(stock, productIdAndQuantityList))
                            .collect(Collectors.toList());
            if(foundStocksForSingleLocation.size() == productIdAndQuantityList.size()) {
                return foundStocksForSingleLocation;
            }
        }
        throw new NotFoundException("Unable to find products in stock");
    }
}

package ro.msg.learning.shop.service.strategy;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SingleLocationStrategy implements LocationStrategy{

    private final StockService stockService;

    private final LocationService locationService;

    @Override
    public List<Stock> getStockLocations(List<OrderDetail> productIdAndQuantityList) {

        List<Location> locations = locationService.findAllLocations();
        for(Location location : locations) {
            List<Stock> stocksByLocation = stockService.findAllStocksByLocationId(location.getId());
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

    private boolean stockHasNeededProduct(Stock stock,
                                          List<OrderDetail> productIdAndQuantityList ) {

        List<OrderDetail> found = productIdAndQuantityList.stream()
                .filter(p -> stock.getProduct().getId() == p.getProduct().getId() &&
                        stock.getQuantity() > p.getQuantity()).collect(Collectors.toList());
        return !found.isEmpty();
    }
}

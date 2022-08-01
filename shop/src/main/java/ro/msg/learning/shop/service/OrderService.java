package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.exceptions.NotFoundException;
import ro.msg.learning.shop.service.strategy.LocationStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    private final PlacedOrderRepository placedOrderRepository;

    private final CustomerRepository customerRepository;

    private final LocationRepository locationRepository;

    private final ProductRepository productRepository;

    private final LocationStrategy locationStrategy;

    private final StockRepository stockRepository;


    public OrderService(PlacedOrderRepository placedOrderRepository, CustomerRepository customerRepository, LocationRepository locationRepository, ProductRepository productRepository, LocationStrategy locationStrategy, StockRepository stockRepository) {
        this.placedOrderRepository = placedOrderRepository;
        this.customerRepository = customerRepository;
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
        this.locationStrategy = locationStrategy;
        this.stockRepository = stockRepository;
    }

    private Set<OrderDetail> findProductsForOrderDetail(PlacedOrder placeOrder, List<OrderDetailProductIdAndQuantity> productIdAndQuantityList) {
        Set<OrderDetail> orderDetails = new HashSet<>();
        for (OrderDetailProductIdAndQuantity productIdAndQuantity : productIdAndQuantityList) {
            if(productRepository.existsById(productIdAndQuantity.getProductId())) {
                Product foundProduct = productRepository.getReferenceById(productIdAndQuantity.getProductId());
                OrderDetail orderDetail = new OrderDetail(placeOrder, foundProduct, productIdAndQuantity.getQuantity());
                orderDetails.add(orderDetail);
            } else {
                throw new NotFoundException("Product not found");
            }
        }
        return orderDetails;
    }

    private void modifyStocks(List<Stock> stocks, List<OrderDetailProductIdAndQuantity> productIdAndQuantityList) {
        for(Stock stock : stocks) {
            Optional<OrderDetailProductIdAndQuantity> foundProduct =
                    productIdAndQuantityList.stream()
                            .filter(l-> l.getProductId() == stock.getProduct().getId()).findFirst();
            foundProduct.ifPresent(orderDetailProductIdAndQuantity -> stock.setQuantity(stock.getQuantity() - orderDetailProductIdAndQuantity.getQuantity()));
            stockRepository.save(stock);
        }
    }

    public PlacedOrder placeOrder(PlacedOrder placedOrder, int customerID, List<OrderDetailProductIdAndQuantity> productIdAndQuantityList) {
        if(customerRepository.existsById(customerID)) {
            Customer customer = customerRepository.getReferenceById(customerID);
            placedOrder.setCustomer(customer);
            Set<OrderDetail> orderDetails = findProductsForOrderDetail(placedOrder, productIdAndQuantityList);
            placedOrder.setOrderDetails(orderDetails);

            //implement strategy location
            List<Stock> foundStocks = locationStrategy.getStockLocations(productIdAndQuantityList);
            Location location = locationRepository.getReferenceById(foundStocks.get(0).getLocation().getId());
            placedOrder.setLocation(location);

            //place order
            modifyStocks(foundStocks, productIdAndQuantityList);
            placedOrderRepository.save(placedOrder);
            return placedOrder;
        } else {
            throw new NotFoundException("Customer does not exist");
        }
    }


}

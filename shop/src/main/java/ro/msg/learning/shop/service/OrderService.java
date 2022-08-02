package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDetailDTO;
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

    private Set<OrderDetail> findProductsForOrderDetail(PlacedOrder placeOrder, List<OrderDetail> productIdAndQuantityList) {
        Set<OrderDetail> orderDetails = new HashSet<>();
        for (OrderDetail productIdAndQuantity : productIdAndQuantityList) {
            if(productRepository.existsById(productIdAndQuantity.getProduct().getId())) {
                Product foundProduct = productRepository.getReferenceById(productIdAndQuantity.getProduct().getId());
                OrderDetail orderDetail = new OrderDetail(placeOrder, foundProduct, productIdAndQuantity.getQuantity());
                orderDetails.add(orderDetail);
            } else {
                throw new NotFoundException("Product not found");
            }
        }
        return orderDetails;
    }

    private void modifyStocks(List<Stock> stocks, List<OrderDetail> productIdAndQuantityList) {
        for(Stock stock : stocks) {
            Optional<OrderDetail> foundProduct =
                    productIdAndQuantityList.stream()
                            .filter(l-> l.getProduct().getId() == stock.getProduct().getId()).findFirst();
            foundProduct.ifPresent(orderDetailProductIdAndQuantity -> stock.setQuantity(stock.getQuantity() - orderDetailProductIdAndQuantity.getQuantity()));
            stockRepository.save(stock);
        }
    }

    public PlacedOrder placeOrder(PlacedOrder placedOrder, int customerID, List<OrderDetail> productIdAndQuantityList) {
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

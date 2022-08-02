package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OrderService {
    private final PlacedOrderRepository placedOrderRepository;

    private final CustomerService customerService;

    private final LocationService locationService;

    private final ProductService productService;

    private final LocationStrategy locationStrategy;

    private final StockService stockService;


    public PlacedOrder placeOrder(PlacedOrder placedOrder, int customerID, List<OrderDetail> productIdAndQuantityList) {
        if(customerService.existsById(customerID)) {
            Customer customer = customerService.findCustomerById(customerID);
            placedOrder.setCustomer(customer);
            Set<OrderDetail> orderDetails = findProductsForOrderDetail(placedOrder, productIdAndQuantityList);
            placedOrder.setOrderDetails(orderDetails);
            List<Stock> foundStocks = locationStrategy.getStockLocations(productIdAndQuantityList);
            Location location = locationService.findLocationById(foundStocks.get(0).getLocation().getId());
            placedOrder.setLocation(location);
            modifyStocks(foundStocks, productIdAndQuantityList);
            placedOrderRepository.save(placedOrder);
            return placedOrder;
        } else {
            throw new NotFoundException("Customer does not exist");
        }
    }

    private Set<OrderDetail> findProductsForOrderDetail(PlacedOrder placeOrder, List<OrderDetail> productIdAndQuantityList) {
        Set<OrderDetail> orderDetails = new HashSet<>();
        for (OrderDetail productIdAndQuantity : productIdAndQuantityList) {
            if(productService.existsById(productIdAndQuantity.getProduct().getId())) {
                Product foundProduct = productService.findProductById(productIdAndQuantity.getProduct().getId());
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
            stockService.updateStock(stock);
        }
    }
}

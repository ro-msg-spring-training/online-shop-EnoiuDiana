package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final PlacedOrderRepository placedOrderRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final CustomerRepository customerRepository;

    private final LocationRepository locationRepository;

    private final ProductRepository productRepository;


    public OrderService(PlacedOrderRepository placedOrderRepository, OrderDetailRepository orderDetailRepository, CustomerRepository customerRepository, LocationRepository locationRepository, ProductRepository productRepository) {
        this.placedOrderRepository = placedOrderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.customerRepository = customerRepository;
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
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

    public PlacedOrder placeOrder(PlacedOrder placedOrder, int customerID, List<OrderDetailProductIdAndQuantity> productIdAndQuantityList) {
        if(customerRepository.existsById(customerID)) {
            Customer customer = customerRepository.getReferenceById(customerID);
            placedOrder.setCustomer(customer);
            Set<OrderDetail> orderDetails = findProductsForOrderDetail(placedOrder, productIdAndQuantityList);
            placedOrder.setOrderDetails(orderDetails);
            //todo implement strategy location
            Location location = locationRepository.getReferenceById(1);
            placedOrder.setLocation(location);
            placedOrderRepository.save(placedOrder);
            return placedOrder;
        } else {
            throw new NotFoundException("Customer does not exist");
        }
    }


}

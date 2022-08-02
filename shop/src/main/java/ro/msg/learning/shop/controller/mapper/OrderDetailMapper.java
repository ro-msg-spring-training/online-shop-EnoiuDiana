package ro.msg.learning.shop.controller.mapper;


import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;

@Component
public class OrderDetailMapper {

    public OrderDetail convertFromDTO(OrderDetailDTO orderDetailDTO) {
        return OrderDetail.builder()
                .product(new Product(orderDetailDTO.getProductId()))
                .quantity(orderDetailDTO.getQuantity())
                .build();

    }

    public OrderDetailDTO convertFromEntity(OrderDetail orderDetail) {
        return OrderDetailDTO.builder()
                .productId(orderDetail.getProduct().getId())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}

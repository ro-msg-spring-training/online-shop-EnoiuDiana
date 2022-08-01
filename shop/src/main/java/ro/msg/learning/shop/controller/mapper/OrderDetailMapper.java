package ro.msg.learning.shop.controller.mapper;


import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.CreateOrderDTO;
import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.OrderDetailProductIdAndQuantity;
import ro.msg.learning.shop.model.PlacedOrder;

@Component
public class OrderDetailMapper {

    public OrderDetailProductIdAndQuantity convertFromDTO(OrderDetailDTO orderDetailDTO) {
        return OrderDetailProductIdAndQuantity.builder()
                .productId(orderDetailDTO.getProductId())
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

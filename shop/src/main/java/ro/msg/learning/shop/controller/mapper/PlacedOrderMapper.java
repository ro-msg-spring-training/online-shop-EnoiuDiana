package ro.msg.learning.shop.controller.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.CreateOrderDTO;
import ro.msg.learning.shop.dto.PlacedOrderDTO;
import ro.msg.learning.shop.model.PlacedOrder;

import java.util.stream.Collectors;

@Component
public class PlacedOrderMapper {

    private final OrderDetailMapper orderDetailMapper;

    public PlacedOrderMapper(OrderDetailMapper orderDetailMapper) {
        this.orderDetailMapper = orderDetailMapper;
    }

    public PlacedOrder convertFromDTO(CreateOrderDTO createOrderDTO) {
        return PlacedOrder.builder()
                .id(createOrderDTO.getId())
                .createdAt(createOrderDTO.getCreatedAt())
                .address_country(createOrderDTO.getAddressCountry())
                .address_city(createOrderDTO.getAddressCity())
                .address_county(createOrderDTO.getAddressCounty())
                .address_street(createOrderDTO.getAddressStreet())
                .build();

    }

    public PlacedOrderDTO convertFromEntity(PlacedOrder placedOrder) {
        return PlacedOrderDTO.builder()
                .id(placedOrder.getId())
                .customerID(placedOrder.getCustomer().getId())
                .createdAt(placedOrder.getCreatedAt())
                .addressCountry(placedOrder.getAddress_country())
                .addressCity(placedOrder.getAddress_city())
                .addressCounty(placedOrder.getAddress_county())
                .addressStreet(placedOrder.getAddress_street())
                .orderDetails(placedOrder.getOrderDetails().stream().map(orderDetailMapper::convertFromEntity).collect(Collectors.toList()))
                .build();
    }
}

package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.controller.mapper.OrderDetailMapper;
import ro.msg.learning.shop.controller.mapper.PlacedOrderMapper;
import ro.msg.learning.shop.dto.CreateOrderDTO;
import ro.msg.learning.shop.dto.PlacedOrderDTO;
import ro.msg.learning.shop.model.PlacedOrder;
import ro.msg.learning.shop.service.OrderService;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderDetailMapper orderDetailMapper;
    private final PlacedOrderMapper placedOrderMapper;

    @PostMapping(value = "/create_order")
    public ResponseEntity<PlacedOrderDTO> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        PlacedOrder placedOrder = orderService.placeOrder(placedOrderMapper.convertFromDTO(createOrderDTO),
                createOrderDTO.getCustomerID(),
                createOrderDTO.getOrderDetails().stream().map(orderDetailMapper::convertFromDTO).collect(Collectors.toList())
        );
        return new ResponseEntity<>(placedOrderMapper.convertFromEntity(placedOrder), HttpStatus.CREATED);
    }
}

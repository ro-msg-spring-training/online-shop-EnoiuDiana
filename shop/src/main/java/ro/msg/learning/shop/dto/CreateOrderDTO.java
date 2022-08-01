package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.controller.serialization.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {
    private int id;
    private int customerID;

    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    private String addressCountry;
    private String addressCity;
    private String addressCounty;
    private String addressStreet;
    private List<OrderDetailDTO> orderDetails;

}

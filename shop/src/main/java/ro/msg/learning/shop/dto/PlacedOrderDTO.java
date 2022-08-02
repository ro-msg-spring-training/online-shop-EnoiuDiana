package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.controller.serialization.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlacedOrderDTO {
    private int id;
    private int customerID;
    private int shippedFromId;
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    private String addressCountry;
    private String addressCity;
    private String addressCounty;
    private String addressStreet;
    private List<OrderDetailDTO> orderDetails;
}

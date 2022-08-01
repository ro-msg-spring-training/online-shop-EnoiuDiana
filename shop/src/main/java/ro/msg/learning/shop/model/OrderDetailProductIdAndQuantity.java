package ro.msg.learning.shop.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailProductIdAndQuantity {
    private int productId;
    private int quantity;
}

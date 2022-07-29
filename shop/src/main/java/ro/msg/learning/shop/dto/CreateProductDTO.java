package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CreateProductDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private String imageUrl;
    private int productCategoryId;
    private int supplierId;
}

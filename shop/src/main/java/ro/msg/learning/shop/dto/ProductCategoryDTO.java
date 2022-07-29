package ro.msg.learning.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductCategoryDTO {

    private int id;
    private String name;
    private String description;

}

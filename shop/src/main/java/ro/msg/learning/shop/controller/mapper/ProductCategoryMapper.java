package ro.msg.learning.shop.controller.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductCategoryDTO;
import ro.msg.learning.shop.model.ProductCategory;

@Component
public class ProductCategoryMapper {

    public ProductCategory convertFromDTO(ProductCategoryDTO productCategoryDTO) {
        return ProductCategory.builder()
                .id(productCategoryDTO.getId())
                .name(productCategoryDTO.getName())
                .description(productCategoryDTO.getDescription())
                .build();
    }

    public ProductCategoryDTO convertFromEntity(ProductCategory productCategory) {
        return ProductCategoryDTO.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .build();
    }
}


package ro.msg.learning.shop.service.mapper;

import ro.msg.learning.shop.dto.ProductCategoryDTO;
import ro.msg.learning.shop.model.ProductCategory;

public class ProductCategoryMapper {
    public static ProductCategory convertFromDTO(ProductCategoryDTO productCategoryDTO) {
        return ProductCategory.builder()
                .id(productCategoryDTO.getId())
                .name(productCategoryDTO.getName())
                .description(productCategoryDTO.getDescription())
                .build();

    }

    public static ProductCategoryDTO convertFromEntity(ProductCategory productCategory) {
        return ProductCategoryDTO.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .build();
    }
}


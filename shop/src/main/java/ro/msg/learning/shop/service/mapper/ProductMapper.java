package ro.msg.learning.shop.service.mapper;

import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.model.BaseEntity;
import ro.msg.learning.shop.model.Product;

public class ProductMapper {
    public static Product convertFromDTO(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .weight(productDTO.getWeight())
                .imageUrl(productDTO.getImageUrl())
                .productCategory(ProductCategoryMapper.convertFromDTO(productDTO.getProductCategory()))
                .supplier(SupplierMapper.convertFromDTO(productDTO.getSupplier()))
                .build();

    }

    public static ProductDTO convertFromEntity(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .imageUrl(product.getImageUrl())
                .productCategory(ProductCategoryMapper.convertFromEntity(product.getProductCategory()))
                .supplier(SupplierMapper.convertFromEntity(product.getSupplier()))
                .build();
    }
}

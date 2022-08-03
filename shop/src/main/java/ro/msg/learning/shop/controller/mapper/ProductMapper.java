package ro.msg.learning.shop.controller.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.CreateProductDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.model.Product;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ProductCategoryMapper productCategoryMapper;
    private final SupplierMapper supplierMapper;

    public Product convertFromDTO(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .weight(productDTO.getWeight())
                .imageUrl(productDTO.getImageUrl())
                .productCategory(productCategoryMapper.convertFromDTO(productDTO.getProductCategory()))
                .supplier(supplierMapper.convertFromDTO(productDTO.getSupplier()))
                .build();
    }

    public ProductDTO convertFromEntity(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .imageUrl(product.getImageUrl())
                .productCategory(productCategoryMapper.convertFromEntity(product.getProductCategory()))
                .supplier(supplierMapper.convertFromEntity(product.getSupplier()))
                .build();
    }

    public Product convertFromCreationDTO(CreateProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .weight(productDTO.getWeight())
                .imageUrl(productDTO.getImageUrl())
                .build();
    }
}

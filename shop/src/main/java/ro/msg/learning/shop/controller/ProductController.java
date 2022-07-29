package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.CreateProductDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.service.ProductCategoryService;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/get_all_products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProducts()
                .stream().map(ProductMapper::convertFromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/get_all_product_categories")
    public List<String> getAllProductCategories() {
        return productCategoryService.findAllProductCategories().stream().map(ProductCategory::getDescription).collect(Collectors.toList());
    }

    @PostMapping("/create_product")
    public ResponseEntity<String> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        productService.createProduct(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

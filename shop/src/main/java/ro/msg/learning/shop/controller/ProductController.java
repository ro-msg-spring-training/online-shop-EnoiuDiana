package ro.msg.learning.shop.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.controller.mapper.ProductMapper;
import ro.msg.learning.shop.dto.CreateProductDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/get_all_products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProducts()
                .stream().map(productMapper::convertFromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/get_product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productMapper.convertFromEntity(productService.findProductById(id)));
    }

    @PostMapping(value = "/create_product")
    public ResponseEntity<Object> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        productService.createProduct(productMapper.convertFromCreationDTO(createProductDTO),
                createProductDTO.getProductCategoryId(), createProductDTO.getSupplierId());
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete_product/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
    }

    @PutMapping(value = "/update_product")
    public ResponseEntity<Object> updateProduct(@RequestBody CreateProductDTO createProductDTO) {
        productService.updateProduct(productMapper.convertFromCreationDTO(createProductDTO),
                createProductDTO.getProductCategoryId(), createProductDTO.getSupplierId());
        return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
    }
}

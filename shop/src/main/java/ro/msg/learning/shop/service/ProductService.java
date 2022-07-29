package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.CreateProductDTO;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;
    private final SupplierService supplierService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryService productCategoryService, SupplierService supplierService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
        this.supplierService = supplierService;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public void createProduct(CreateProductDTO createProductDTO) {
        ProductCategory productCategory = productCategoryService.findProductCategoryById(createProductDTO.getProductCategoryId());
        Supplier supplier = supplierService.findSupplierById(createProductDTO.getSupplierId());

        Product newProduct = new Product(
                createProductDTO.getName(),
                createProductDTO.getDescription(),
                createProductDTO.getPrice(),
                createProductDTO.getWeight(),
                productCategory,
                supplier,
                createProductDTO.getImageUrl());

        productRepository.save(newProduct);
    }


}

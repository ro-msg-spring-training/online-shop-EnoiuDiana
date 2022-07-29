package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.CreateProductDTO;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.SupplierRepository;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryService productCategoryService, SupplierService supplierService, ProductCategoryRepository productCategoryRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public void createProduct(CreateProductDTO createProductDTO) {
        if(productCategoryRepository.existsById(createProductDTO.getProductCategoryId()) &&
        supplierRepository.existsById(createProductDTO.getSupplierId())) {
            ProductCategory productCategory = productCategoryRepository.getReferenceById(createProductDTO.getProductCategoryId());
            Supplier supplier = supplierRepository.getReferenceById(createProductDTO.getSupplierId());
            Product newProduct = new Product(
                    createProductDTO.getName(),
                    createProductDTO.getDescription(),
                    createProductDTO.getPrice(),
                    createProductDTO.getWeight(),
                    productCategory,
                    supplier,
                    createProductDTO.getImageUrl());

            productRepository.save(newProduct);
        } else {
            throw new NotFoundException("Product category or supplier not found");
        }

    }

    public void deleteProduct(int id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new NotFoundException("Product not found");
        }
    }


}

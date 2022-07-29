package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.CreateProductDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.SupplierRepository;
import ro.msg.learning.shop.service.exceptions.NotFoundException;
import ro.msg.learning.shop.controller.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

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

    public Product findProductById(int id) {
        if(productRepository.existsById(id)) {
            return productRepository.getReferenceById(id);
        }
        else throw new NotFoundException("product not found");
    }

    public void createProduct(Product product, int productCategoryId, int supplierId) {
        saveProduct(product, productCategoryId, supplierId);

    }

    private void saveProduct(Product product, int productCategoryId, int supplierId) {
        if(productCategoryRepository.existsById(productCategoryId) &&
        supplierRepository.existsById(supplierId)) {
            ProductCategory productCategory = productCategoryRepository.getReferenceById(productCategoryId);
            Supplier supplier = supplierRepository.getReferenceById(supplierId);
            product.setProductCategory(productCategory);
            product.setSupplier(supplier);
            productRepository.save(product);
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

    public void updateProduct(Product product, int productCategoryId, int supplierId) {
        if(productRepository.existsById(product.getId())) {
            saveProduct(product, productCategoryId, supplierId);
        } else {
            throw new NotFoundException("Product not found");
        }
    }


}

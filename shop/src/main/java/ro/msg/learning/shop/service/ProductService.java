package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductCategoryService productCategoryService;

    private final SupplierService supplierService;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(int id) {
        if(productRepository.existsById(id)) {
            return productRepository.getReferenceById(id);
        }
        else throw new NotFoundException("product not found");
    }

    public boolean existsById(int id) {
        return productRepository.existsById(id);
    }

    public void createProduct(Product product, int productCategoryId, int supplierId) {
        saveProduct(product, productCategoryId, supplierId);
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

    private void saveProduct(Product product, int productCategoryId, int supplierId) {
        if(productCategoryService.existsById(productCategoryId) &&
        supplierService.existsById(supplierId)) {
            ProductCategory productCategory = productCategoryService.findProductCategoryById(productCategoryId);
            Supplier supplier = supplierService.findSupplierById(supplierId);
            product.setProductCategory(productCategory);
            product.setSupplier(supplier);
            productRepository.save(product);
        } else {
            throw new NotFoundException("Product category or supplier not found");
        }
    }
}

package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategory> findAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory findProductCategoryById(int id) {
        return productCategoryRepository.getReferenceById(id);
    }

}

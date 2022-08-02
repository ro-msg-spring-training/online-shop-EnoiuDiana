package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> findAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory findProductCategoryById(int id) {
        return productCategoryRepository.getReferenceById(id);
    }

    public boolean existsById(int id) {
        return productCategoryRepository.existsById(id);
    }
}

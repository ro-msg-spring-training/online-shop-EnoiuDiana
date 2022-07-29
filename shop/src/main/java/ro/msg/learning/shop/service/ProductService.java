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
import ro.msg.learning.shop.service.mapper.ProductMapper;

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

    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::convertFromEntity).collect(Collectors.toList());
    }

    public ProductDTO findProductById(int id) {
        if(productRepository.existsById(id)) {
            Product product = productRepository.getReferenceById(id);
            return ProductMapper.convertFromEntity(product);
        }
        else throw new NotFoundException("product not found");
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

    public void updateProduct(CreateProductDTO createProductDTO) {
        if(productRepository.existsById(createProductDTO.getId())) {
            if(productCategoryRepository.existsById(createProductDTO.getProductCategoryId()) &&
                    supplierRepository.existsById(createProductDTO.getSupplierId())) {
                ProductCategory productCategory = productCategoryRepository.getReferenceById(createProductDTO.getProductCategoryId());
                Supplier supplier = supplierRepository.getReferenceById(createProductDTO.getSupplierId());
                Product newProduct = new Product(
                        createProductDTO.getId(),
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
        } else {
            throw new NotFoundException("Product not found");
        }
    }


}

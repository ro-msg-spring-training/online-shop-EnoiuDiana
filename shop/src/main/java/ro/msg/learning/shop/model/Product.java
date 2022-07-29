package ro.msg.learning.shop.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Product extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    public Product(String name,
                   String description,
                   BigDecimal price,
                   Double weight,
                   ProductCategory productCategory,
                   Supplier supplier,
                   String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.productCategory = productCategory;
        this.supplier = supplier;
        this.imageUrl = imageUrl;
    }

    public Product(int id,
                   String name,
                   String description,
                   BigDecimal price,
                   Double weight,
                   ProductCategory productCategory,
                   Supplier supplier,
                   String imageUrl) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.productCategory = productCategory;
        this.supplier = supplier;
        this.imageUrl = imageUrl;
    }
}

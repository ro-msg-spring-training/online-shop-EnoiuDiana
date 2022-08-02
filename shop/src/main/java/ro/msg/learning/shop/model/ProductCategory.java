package ro.msg.learning.shop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCTCATEGORY")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductCategory extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public ProductCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

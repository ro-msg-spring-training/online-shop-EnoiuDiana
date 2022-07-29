package ro.msg.learning.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PLACEDORDER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class PlacedOrder extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "shipped_from_id", nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String address_country;

    @Column(nullable = false)
    private String address_city;

    @Column(nullable = false)
    private String address_county;

    @Column(nullable = false)
    private String address_street;

    @OneToMany(mappedBy = "placedOrder", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<>();

}

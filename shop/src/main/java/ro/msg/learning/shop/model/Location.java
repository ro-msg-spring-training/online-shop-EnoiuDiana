package ro.msg.learning.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "LOCATION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Location extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address_country;

    @Column(nullable = false)
    private String address_city;

    @Column(nullable = false)
    private String address_county;

    @Column(nullable = false)
    private String address_street;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private Set<PlacedOrder> placedOrders = new HashSet<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private Set<Revenue> revenues = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Stock> stocks = new HashSet<>();

}

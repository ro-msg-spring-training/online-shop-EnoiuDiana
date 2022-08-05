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

    public Location(String locationName, String country, String city, String county, String street) {
        this.name = locationName;
        this.address_country = country;
        this.address_city = city;
        this.address_county = county;
        this.address_street = street;
    }
}

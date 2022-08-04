package ro.msg.learning.shop;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ro.msg.learning.shop.configuration.StrategyUnitTestConfig;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.service.strategy.MultipleLocationsStrategy;
import ro.msg.learning.shop.service.strategy.SingleLocationStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(StrategyUnitTestConfig.class)
public class LocationStrategyIntegrationTest {

    @Autowired
    @Qualifier("singleLocationStrategy")
    private SingleLocationStrategy singleLocationStrategy;

    @Autowired
    @Qualifier("multipleLocationStrategy")
    private MultipleLocationsStrategy multipleLocationsStrategy;

    private final List<OrderDetail> productIdAndQuantityList =  new ArrayList<>();

    @BeforeEach
    public void setUp() {
        initOrderDetailList();
    }


    @Test
    void testGetStockForSingleLocation() {
        List<Stock> stocks = singleLocationStrategy.getStockLocations(productIdAndQuantityList);
        assertThat(stocks).hasSize(2);
        assertThat(stocks.get(0).getLocation().getId()).isEqualTo(stocks.get(1).getLocation().getId());
    }

    @Test
    void testGetStockForMultipleLocations() {
        List<Stock> stocks = multipleLocationsStrategy.getStockLocations(productIdAndQuantityList);
        assertThat(stocks).hasSize(2);
        assertThat(stocks.get(0).getLocation().getId()).isEqualTo(1);
        assertThat(stocks.get(1).getLocation().getId()).isEqualTo(2);

    }

    private void initOrderDetailList() {
        OrderDetail orderDetail = OrderDetail.builder()
                .product(new Product(1))
                .quantity(3)
                .build();
        OrderDetail orderDetail2 = OrderDetail.builder()
                .product(new Product(2))
                .quantity(1)
                .build();
        productIdAndQuantityList.add(orderDetail);
        productIdAndQuantityList.add(orderDetail2);
    }

}

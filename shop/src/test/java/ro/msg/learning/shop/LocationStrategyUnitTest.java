package ro.msg.learning.shop;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.parameters.P;
import ro.msg.learning.shop.configuration.StrategyUnitTestConfig;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.strategy.MultipleLocationsStrategy;
import ro.msg.learning.shop.service.strategy.SingleLocationStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class LocationStrategyUnitTest {

    @InjectMocks
    @Qualifier("singleLocationStrategy")
    private SingleLocationStrategy singleLocationStrategy;

    @InjectMocks
    @Qualifier("multipleLocationStrategy")
    private MultipleLocationsStrategy multipleLocationsStrategy;

    @Mock
    private LocationService locationService;

    @Mock
    private StockService stockService;

    private final List<OrderDetail> productIdAndQuantityList = new ArrayList<>();

    Location location1 = new Location("location one",
            "Romania",
            "CLuj-Napoca",
            "CLuj",
            "Str. Croitorilor");

    Location location2 = new Location("location two",
            "Romania",
            "CLuj-Napoca",
            "CLuj",
            "Str. Croitorilor");

    Location location3 = new Location("location three",
            "Romania",
            "CLuj-Napoca",
            "CLuj",
            "Str. Croitorilor");

    ProductCategory productCategory1 = new ProductCategory("name", "desc");
    Supplier supplier = new Supplier("name");

    Product product1 = new Product(1,
            "pr 1",
            "desc",
            new BigDecimal(200),
            200.0,
            productCategory1,
            supplier,
            "url");

    Product product2 = new Product(2,
            "pr 3",
            "desc",
            new BigDecimal(200),
            200.0,
            productCategory1,
            supplier,
            "url");

    @BeforeEach
    public void setUp() {
        initOrderDetailList();
    }


    @Test
    void testGetStockForSingleLocation() {

        List<Location> locations = new ArrayList<>();
        locations.add(location1);
        Stock stock1 = new Stock(product1, location1, 10);
        Stock stock2 = new Stock(product2, location1, 10);

        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);

        Mockito.when(locationService.findAllLocations()).thenReturn(locations);
        Mockito.when(stockService.findAllStocksByLocationId(0)).thenReturn(stocks);


        List<Stock> stocksFound = singleLocationStrategy.getStockLocations(productIdAndQuantityList);
        assertThat(stocksFound).hasSize(2);
        assertThat(stocksFound.get(0).getLocation().getId()).isEqualTo(stocks.get(1).getLocation().getId());

    }

    @Test
    void testGetStockForMultipleLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(location1);
        locations.add(location2);
        locations.add(location3);
        Stock stock1 = new Stock(product1, location1, 10);
        Stock stock2 = new Stock(product2, location1, 100);

        Stock stock3 = new Stock(product1, location2, 2);
        Stock stock4 = new Stock(product2, location2, 15);

        Stock stock5 = new Stock(product1, location3, 100);
        Stock stock6 = new Stock(product2, location3, 10);

        List<Stock> stocksLoc1 = new ArrayList<>();
        stocksLoc1.add(stock1);
        stocksLoc1.add(stock2);
        List<Stock> stocksLoc2 = new ArrayList<>();
        stocksLoc2.add(stock3);
        stocksLoc2.add(stock4);
        List<Stock> stocksLoc3 = new ArrayList<>();
        stocksLoc3.add(stock5);
        stocksLoc3.add(stock6);

        Mockito.when(locationService.findAllLocations()).thenReturn(locations);
        Mockito.when(stockService.findAllStocksByLocationId(location1.getId())).thenReturn(stocksLoc1);
        Mockito.when(stockService.findAllStocksByLocationId(location2.getId())).thenReturn(stocksLoc2);
        Mockito.when(stockService.findAllStocksByLocationId(location3.getId())).thenReturn(stocksLoc3);



        List<Stock> stocksFound = singleLocationStrategy.getStockLocations(productIdAndQuantityList);
        assertThat(stocksFound).hasSize(2);
        assertThat(stocksFound.get(0).getLocation().getId()).isEqualTo(location3.getId());
        assertThat(stocksFound.get(1).getLocation().getId()).isEqualTo(location1.getId());

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

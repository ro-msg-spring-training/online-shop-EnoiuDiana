package ro.msg.learning.shop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.strategy.MultipleLocationsStrategy;
import ro.msg.learning.shop.service.strategy.SingleLocationStrategy;


@TestConfiguration
@RequiredArgsConstructor
@Import({LocationService.class, StockService.class})
public class StrategyUnitTestConfig {
    private final LocationService locationService;
    private final StockService stockService;

    @Bean(name = "multipleLocationStrategy")
    @Primary
    public MultipleLocationsStrategy getMultipleLocationStrategy() {
        return new MultipleLocationsStrategy(stockService);
    }

    @Bean(name = "singleLocationStrategy")
    public SingleLocationStrategy getSingleLocationStrategy() {
        return new SingleLocationStrategy(stockService, locationService);
    }
}

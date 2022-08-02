package ro.msg.learning.shop.config;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.strategy.LocationStrategy;
import ro.msg.learning.shop.service.strategy.MultipleLocationsStrategy;
import ro.msg.learning.shop.service.strategy.SingleLocationStrategy;

@Configuration
@RequiredArgsConstructor
public class StrategyConfig {

    public enum StrategySelected {
        SINGLE_LOCATION_STRATEGY,
        MULTIPLE_LOCATIONS_STRATEGY
    }

    private final StockService stockService;

    private final LocationService locationService;
    @Value("${strategy}")
    private StrategySelected strategy;

    @Bean
    public LocationStrategy getStrategyType() {
        if(strategy == StrategySelected.SINGLE_LOCATION_STRATEGY) {
            return new SingleLocationStrategy(stockService, locationService);
        } else if (strategy == StrategySelected.MULTIPLE_LOCATIONS_STRATEGY) {
            return new MultipleLocationsStrategy(stockService);
        }
        return null;
    }
}

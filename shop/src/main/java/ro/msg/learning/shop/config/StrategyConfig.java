package ro.msg.learning.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.strategy.LocationStrategy;
import ro.msg.learning.shop.service.strategy.MultipleLocationsStrategy;
import ro.msg.learning.shop.service.strategy.SingleLocationStrategy;

@Configuration
@RequiredArgsConstructor
public class StrategyConfig {

    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;

    @Value("${strategy}")
    private int strategy;

    @Bean
    public LocationStrategy getStrategyType() {
        if(strategy == 0) {
            return new SingleLocationStrategy(stockRepository, locationRepository);
        } else if (strategy == 1) {
            return new MultipleLocationsStrategy(stockRepository);
        }
        return null;
    }

}

package ro.msg.learning.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ro.msg.learning.shop.configuration.StrategyUnitTestConfig;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.service.LocationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@Import(LocationService.class)
public class LocationUnitTest {

    @Mock
    private LocationRepository locationRepository;

    @Autowired
    private LocationService locationService;


    @Test
    void savedLocationWorks() {
        Location location = new Location("First location",
                "Romania",
                "CLuj-Napoca",
                "CLuj",
                "Str. Croitorilor");

        locationRepository.save(location);
        assertEquals("First location", locationService.findAllLocations().get(0).getName());
    }

}

package ro.msg.learning.shop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.service.LocationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class LocationUnitTest {

    @Mock
    private LocationRepository locationRepository;


    @InjectMocks
    private LocationService locationService;

    @Test
    void savedLocationWorks() {
        Location location = new Location("Fi location",
                "Romania",
                "CLuj-Napoca",
                "CLuj",
                "Str. Croitorilor");

        Mockito.when(locationRepository.save(Mockito.any(Location.class))).then(returnsFirstArg());
        Location locationSaved = locationService.createLocation(location);
        assertThat(locationSaved).isNotNull();
    }

}

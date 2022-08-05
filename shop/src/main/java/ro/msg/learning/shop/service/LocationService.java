package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location findLocationById(int id) {
        if(locationRepository.existsById(id)) {
            return locationRepository.getReferenceById(id);
        }
        else throw new NotFoundException("Location not found");
    }

    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }
}

package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.service.strategy.LocationStrategy;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}

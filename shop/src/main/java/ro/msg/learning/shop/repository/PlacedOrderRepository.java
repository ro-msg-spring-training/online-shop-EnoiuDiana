package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.PlacedOrder;

@Repository
public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, Integer> {
}

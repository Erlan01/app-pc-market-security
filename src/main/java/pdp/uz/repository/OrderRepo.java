package pdp.uz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.domain.Order;



@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {


}

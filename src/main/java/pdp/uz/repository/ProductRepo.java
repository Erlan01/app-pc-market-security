package pdp.uz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.domain.Product;

import java.util.Optional;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Page<Product> findAllByActiveTrue(Pageable pageable);

    Optional<Product> findByIdAndActiveTrue(Long id);
}

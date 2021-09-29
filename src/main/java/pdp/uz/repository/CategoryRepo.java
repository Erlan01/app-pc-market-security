package pdp.uz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.domain.Category;

import java.util.Optional;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Page<Category> findAllByActiveTrue(Pageable pageable);

    Optional<Category> findByIdAndActiveTrue(Long id);

    Optional<Category> findByNameAndActiveTrue(String name);
}

package pdp.uz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.domain.Client;

import java.util.Optional;


@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {

    Page<Client> findAllByActiveTrue(Pageable pageable);

    Optional<Client> findByIdAndActiveTrue(Long id);

    boolean existsByPhoneNumberAndEmailAndActiveTrue(String phoneNumber, String email);
}

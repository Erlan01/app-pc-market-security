package pdp.uz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.domain.AttachmentContent;

import java.util.Optional;

@Repository
public interface AttachmentContentRepo extends JpaRepository<AttachmentContent, Long> {

    Optional<AttachmentContent> findByAttachmentId(Long id);
}

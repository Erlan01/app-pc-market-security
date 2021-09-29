package pdp.uz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.domain.Attachment;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
}

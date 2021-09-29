package pdp.uz.domain;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AttachmentContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "attachment_id", referencedColumnName = "id")
    private Attachment attachment;
}

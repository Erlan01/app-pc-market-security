package pdp.uz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

    private String name;

    private String model;

    private String mark;

    private String description;

    private Long categoryId;

    private Long attachmentId;

    boolean active = true;
}

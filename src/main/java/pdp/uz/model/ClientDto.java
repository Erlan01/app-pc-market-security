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
public class ClientDto implements Serializable {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private boolean active = true;
}

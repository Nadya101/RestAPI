package reqres_objects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Registration {
    private String email;
    private String password;
    private int id;
    private String token;
}

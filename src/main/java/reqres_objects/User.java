package reqres_objects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private String name;
    private String job;
}

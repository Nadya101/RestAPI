package reqres_objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Job {
    private String name;
    private String job;
    private int id;
    private String createdAt;
}

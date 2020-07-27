package Work1.Project1.Package.requestresponseobject;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyResponseEntity implements Serializable {

    @Getter @Setter private long status;
    @Getter @Setter private String message;
}

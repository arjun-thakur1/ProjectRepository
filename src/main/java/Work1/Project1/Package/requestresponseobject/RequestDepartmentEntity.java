package Work1.Project1.Package.requestresponseobject;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDepartmentEntity implements Serializable {

    @Getter @Setter private Long companyId;
    @Getter @Setter private Long departmentId;
    @Getter @Setter private String departmentName;

}

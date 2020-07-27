package Work1.Project1.Package.requestresponseobject;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEmployeeEntity implements Serializable {
    @Getter @Setter private Long companyId;
    @Getter @Setter private Long departmentId;
    @Getter @Setter private Long employeeId;
    @Getter @Setter private String empName;
    @Getter @Setter private String phone;
    @Getter @Setter private Long salary;
}

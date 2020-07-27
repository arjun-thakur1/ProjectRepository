package Work1.Project1.Package.requestresponseobject;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

public class RequestEmployeeEntity implements Serializable {

    @Getter @Setter private Long companyId;
    @Getter @Setter private Long departmentId;
    @Getter @Setter private Long employeeId;
    @Getter @Setter private String empName;
    @Getter @Setter private String phone;
    @Getter @Setter private Long salary;
}

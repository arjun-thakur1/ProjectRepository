package Work1.Project1.Package.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

public class RequestEmployee implements Serializable {

    @Getter @Setter private Long companyId;
    @Getter @Setter private Long departmentId;
    @Getter @Setter private String empName;
    @Getter @Setter private String phone;
    @Getter @Setter private Long salary;
}

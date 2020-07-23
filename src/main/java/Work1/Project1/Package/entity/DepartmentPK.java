package Work1.Project1.Package.entity;

import lombok.Data;


import java.io.Serializable;
import javax.persistence.Embeddable;
//import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@Embeddable
public class DepartmentPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter @Setter private Long companyId;
    @Getter @Setter private Long departmentId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public DepartmentPK()
    {

    }
    public DepartmentPK(Long departmentId,Long companyId)
    {
        this.companyId=companyId;
        this.departmentId=departmentId;
    }


}

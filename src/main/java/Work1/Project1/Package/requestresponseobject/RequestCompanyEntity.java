package Work1.Project1.Package.requestresponseobject;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCompanyEntity implements Serializable {


    @Getter @Setter
    private String companyName;
    @Getter @Setter
    private String ceoName;

}

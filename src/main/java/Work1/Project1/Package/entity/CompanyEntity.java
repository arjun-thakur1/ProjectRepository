package Work1.Project1.Package.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CompanyEntity {

    @Id
    @Column(name="company_id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long  companyId;

    @Column(name="company_name")

    private String companyName;
    @Column(name="ceo_name")
    private String ceoName;





    public CompanyEntity(String companyName, String ceoName) {
        this.companyName = companyName;
        this.ceoName = ceoName;

    }


}

package Work1.Project1.Package.entity;

import javax.persistence.*;

@Entity
@Table  //(name="company_table")
public class CompanyEntity {

    @Column(name="company_name")
    private String companyName;
    @Column(name="ceo_name")
    private String ceoName;

    @Id
    @Column(name="company_id")
    private Long  companyId;

   /* @OneToMany(mappedBy = "companyEntity1", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<DepartmentEntity> departmentEntitySet;
*/
    public CompanyEntity() {
    }

    public CompanyEntity(String companyName, String ceoName, Long companyId) {
        this.companyName = companyName;
        this.ceoName = ceoName;
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCeoName() {
        return ceoName;
    }

    public void setCeoName(String ceoName) {
        this.ceoName = ceoName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


}

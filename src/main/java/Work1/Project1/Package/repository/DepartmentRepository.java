package Work1.Project1.Package.repository;

import Work1.Project1.Package.entity.DepartmentEntity;
import Work1.Project1.Package.entity.DepartmentPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, DepartmentPK> {
   // public List<DepartmentEntity> findByCompanyId(String companyId);
    public List<DepartmentEntity> findAllByDepartmentPKCompanyId(Long companyId);

        public void deleteByDepartmentPK(DepartmentPK departmentPK);
}

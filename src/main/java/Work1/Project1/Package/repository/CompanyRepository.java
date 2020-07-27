package Work1.Project1.Package.repository;

import Work1.Project1.Package.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository   //so that this is scanned during classpath
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByCompanyName(String companyName);

    // @Query(value="SELECT m FROM CompanyRepository m WHERE m.companyName=1? AND m.companyId!=:2?" , nativeQuery )
    //boolean existsByCompanyName(String companyName , long companyId); //, @Param("companyId'") @Param("companyName")
}

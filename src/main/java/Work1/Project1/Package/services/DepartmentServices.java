package Work1.Project1.Package.services;

import Work1.Project1.Package.entity.DepartmentEntity;
import Work1.Project1.Package.entity.DepartmentPK;
import Work1.Project1.Package.repository.CompanyRepository;
import Work1.Project1.Package.repository.DepartmentRepository;
import Work1.Project1.Package.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional      //delete request is not performed without this annotation
public class DepartmentServices {

    @Autowired
    private DepartmentRepository departmentRepository;
    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;
    public List<DepartmentEntity> getAllDetails() {
        return this.departmentRepository.findAll();
    }


    public String addDepartment(DepartmentEntity departmentEntity) {
        Long companyId= departmentEntity.getDepartmentPK().getCompanyId();
   //     boolean companyPresent=companyRepository.existsById(companyId);
       // if(companyPresent) {
            this.departmentRepository.save(departmentEntity);
            return "Successfully added";
    //    }
     //   return "Company Not registered";
    }

    public Optional<DepartmentEntity> getDepartmentDetail(Long departmentId, Long companyId) {
        DepartmentPK departmentPK = new  DepartmentPK(departmentId,companyId);

        return this.departmentRepository.findById(departmentPK);
    }

    public void deleteDepartmentDetails(DepartmentPK departmentPK) throws Exception{

            this.departmentRepository.deleteByDepartmentPK(departmentPK); //deleteByDepartmentPK(departmentPK);
        return;
    }


    public void updateDetails(DepartmentEntity updatedepartmentEntity) {

        Optional<DepartmentEntity> fetcheddepartmentEntity=departmentRepository.findById(updatedepartmentEntity.getDepartmentPK());

        if(fetcheddepartmentEntity.isPresent())
        {
            DepartmentEntity departmentEntity=fetcheddepartmentEntity.get();
            if(updatedepartmentEntity.getDepartmentName()==null)
            {
                updatedepartmentEntity.setDepartmentName(departmentEntity.getDepartmentName());
            }

        }

        departmentRepository.save(updatedepartmentEntity);
        return;
    }

    public List<DepartmentEntity> getAllDepartmentsOfCompany(long companyId) {
        return departmentRepository.findAllByDepartmentPKCompanyId(companyId);
    }





}

package Work1.Project1.Package.services;

import Work1.Project1.Package.converter.EmployeeEntityListToResponseEmpList;
import Work1.Project1.Package.entity.*;
import Work1.Project1.Package.repository.CompanyRepository;
import Work1.Project1.Package.repository.DepartmentRepository;
import Work1.Project1.Package.requestresponseobject.RequestEmployeeEntity;
import Work1.Project1.Package.repository.EmployeeRepository;
import Work1.Project1.Package.requestresponseobject.ResponseDepartmentEntity;
import Work1.Project1.Package.requestresponseobject.ResponseEmployeeEntity;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static Work1.Project1.Package.constants.ApplicationConstants.*;

@Service
@CacheConfig(cacheNames={"employee_cache"})
@Component
@Transactional
public class EmployeeServices  {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeEntityListToResponseEmpList employeeEntityListToResponseEmpList;

    public Object getAllDetails(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);/*
 List<CompanyEntity> allCompanyEntity= companyRepository.findAll(paging);
 HashMap<Long,HashMap <Long,List<ResponseEmployeeEntity>>> companyToDeptToEmpMapp= new HashMap<Long,HashMap<Long,List<ResponseEmployeeEntity>>>();  //l.getCompanyId()>
   allCompanyEntity.forEach((c)->{
                    List<DepartmentEntity> allDepartmentEntities= departmentRepository.findAllByDepartmentPKCompanyId(c.getCompanyId());
            HashMap <Long,List<ResponseEmployeeEntity>> deptToEmployeemap=new HashMap<Long,List<ResponseEmployeeEntity>>();
     allDepartmentEntities.forEach((d)-> {
                        List<EmployeeEntity> employeeEntities = employeeRepository.findByEmployeePKCompanyIdAndEmployeePKDepartmentId(c.getCompanyId(), d.getDepartmentPK().getDepartmentId());
                        List<ResponseEmployeeEntity> responseEmployeeEntities = employeeEntityListToResponseEmpList.convert(employeeEntities);
                        deptToEmployeemap.put(d.departmentPK.getDepartmentId(), responseEmployeeEntities);
                    });
                    companyToDeptToEmpMapp.put(c.getCompanyId(),deptToEmployeemap);
                } );
*/
        Page<CompanyEntity> allCompanyEntity= companyRepository.findAll(paging);
        HashMap<Long,HashMap <Long,List<ResponseEmployeeEntity>>> companyToDeptToEmpMapp= new HashMap<Long,HashMap<Long,List<ResponseEmployeeEntity>>>();  //l.getCompanyId()>
        allCompanyEntity.forEach((c)->{
            List<DepartmentEntity> allDepartmentEntities= departmentRepository.findAllByDepartmentPKCompanyId(c.getCompanyId());
            HashMap <Long,List<ResponseEmployeeEntity>> deptToEmployeemap=new HashMap<Long,List<ResponseEmployeeEntity>>();
            allDepartmentEntities.forEach((d)-> {
                List<EmployeeEntity> employeeEntities = employeeRepository.findByEmployeePKCompanyIdAndEmployeePKDepartmentId(c.getCompanyId(), d.getDepartmentPK().getDepartmentId());
                List<ResponseEmployeeEntity> responseEmployeeEntities = employeeEntityListToResponseEmpList.convert(employeeEntities);
                deptToEmployeemap.put(d.departmentPK.getDepartmentId(), responseEmployeeEntities);
            });
            companyToDeptToEmpMapp.put(c.getCompanyId(),deptToEmployeemap);
        } );
        if(companyToDeptToEmpMapp.isEmpty())
            return null;
        return  companyToDeptToEmpMapp;
    }

    @Cacheable(value = "employee_cache")
    public Object getEmployeeDetails(EmployeePK employeePK) {
        if(employeeRepository.existsById(employeePK)) {
            Optional<EmployeeEntity> employeeEntity= employeeRepository.findById(employeePK);    //HttpStatus.OK
            EmployeeEntity employeeEntity1= employeeEntity.get();
            return( new ResponseEmployeeEntity(employeeEntity1.getEmployeePK().getCompanyId(),employeeEntity1.getEmployeePK().getDepartmentId(),
                    employeeEntity1.getEmployeePK().getEmployeeId(),employeeEntity1.getEmpName(),employeeEntity1.getPhone(),employeeEntity1.getSalary()) );
       }
        else {
            return null;
        }
}

    public String addEmployee(RequestEmployeeEntity requestEmployeeEntity) {
        try {
            EmployeePK employeePK =  new EmployeePK(requestEmployeeEntity.getCompanyId(),requestEmployeeEntity.getDepartmentId(),requestEmployeeEntity.getEmployeeId());//employeeEntity.getEmployeePK();
            EmployeeEntity employeeEntity=new EmployeeEntity(employeePK,requestEmployeeEntity.getEmpName(),requestEmployeeEntity.getPhone(),requestEmployeeEntity.getSalary());
           // DepartmentPK departmentPK=new DepartmentPK(requestEmployeeEntity.getCompanyId(),)
           if (!employeeRepository.existsById(employeePK)) {
                try {
                    this.employeeRepository.saveAndFlush(employeeEntity);
                    return Add_Success;
                }catch(Exception e) {
                  return Add_Failed;
                }
            }else {
               try {
                   this.updateDetails(employeeEntity);
                   return Update_Success;
               }
                catch(RuntimeException e) {
                      return Add_Failed;
                   }
            }
        } catch (RuntimeException e) {
            System.out.println(e);
            return Add_Failed;
        }
    }

    @CacheEvict(value = "employee_cache", allEntries = true)
    public String deleteEmployeeDetails(EmployeePK employeePK) throws Exception {
        if(employeeRepository.existsById(employeePK)){
            try {
                this.employeeRepository.deleteByEmployeePK(employeePK);
                return Delete_Success;
            }catch(Exception e)
            {
                return Failed;
            }
        } else{
            return Failed;
        }
    }

    public List<EmployeeEntity> getAllEmployeeOfDepartment(DepartmentPK departmentPK) {
        long companyId = departmentPK.getCompanyId();
        long departmentId = departmentPK.getDepartmentId();
        return employeeRepository.findByEmployeePKCompanyIdAndEmployeePKDepartmentId(companyId, departmentId);
    }

    @CachePut(value = "employee_cache")
    public String updateDetails(EmployeeEntity updateemployeeEntity) {
        Optional<EmployeeEntity> fetchedemployeeEntity = employeeRepository.findById(updateemployeeEntity.getEmployeePK());
        if (fetchedemployeeEntity.isPresent()) {
            EmployeeEntity employeeEntity = fetchedemployeeEntity.get();
            if (updateemployeeEntity.getEmpName() == null) {
                updateemployeeEntity.setEmpName(employeeEntity.getEmpName());
            }
            if (updateemployeeEntity.getPhone() == null) {
                updateemployeeEntity.setPhone(employeeEntity.getPhone());
            }
            if (updateemployeeEntity.getSalary() == 0) {
                updateemployeeEntity.setSalary(employeeEntity.getSalary());
            }
        }
        try {
            this.employeeRepository.save(updateemployeeEntity);
            return Update_Success;
        } catch (Exception e) {
            System.out.println("error ms" + e);
            return Failed;
        }


    }


    public void updateSalaryByAbsoluteValue(Long companyId, Long departmentId, Long employeeId, Long increment) {
        if (departmentId == 0) {
            //increment for all emp in the org
            updateAllCompanyEmployeeSalary(companyId, increment);
        } else if (employeeId == 0) {
            //inc for all emp in dept in particular org
            updateCompanyAllDepartmentEmployeeSalary(companyId, departmentId, increment);
        } else {
            updateEmployeeSalary(companyId, departmentId, employeeId, increment);
        }
    }

    public void updateSalaryByPercentage(long companyId, long departmentId, long employeeId, long increment) {
        if (departmentId == 0) {
            //increment for all emp in the org
            updateAllCompanyEmployeeSalaryByPercentage(companyId, increment);
        } else if (employeeId == 0) {
            //inc for all emp in dept in particular org
            updateAllDepartmentEmployeeSalarybyPercentage(companyId, departmentId, increment);
        } else {
            updateEmployeeSalaryByPercentage(companyId, departmentId, employeeId, increment);
        }
    }

    public void updateAllCompanyEmployeeSalary(Long companyId, Long increment) {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAllByEmployeePKCompanyId(companyId);
        employeeEntityList.forEach((l) -> {
            l.setSalary(l.getSalary() + increment);
            updateDetails(l);
        });
    }

    public void updateCompanyAllDepartmentEmployeeSalary(Long companyId, Long departmentId, Long increment) {

        List<EmployeeEntity> employeeEntityList = employeeRepository.findByEmployeePKCompanyIdAndEmployeePKDepartmentId(companyId, departmentId);
        ;
        employeeEntityList.forEach((l) -> {
            // int salary=l.getSalary();
            l.setSalary(l.getSalary() + increment);
            updateDetails(l);
        });
    }


    public void updateEmployeeSalary(Long companyId, Long departmentId, Long employeeId, Long increment) {
        EmployeePK employeePK = new EmployeePK(employeeId, departmentId, companyId);
        List<EmployeeEntity> employeeEntityList = employeeRepository.findByEmployeePK(employeePK);
        employeeEntityList.forEach((l) -> {
            l.setSalary(l.getSalary() + increment);
            updateDetails(l);
        });
    }


    public void updateAllCompanyEmployeeSalaryByPercentage(long companyId, long increment) {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAllByEmployeePKCompanyId(companyId);
        employeeEntityList.forEach((l) -> {
            Long salary = l.getSalary();
            l.setSalary(salary + ((increment * salary) / 100));
            updateDetails(l);
        });
    }

    public void updateAllDepartmentEmployeeSalarybyPercentage(long companyId, long departmentId, long increment) {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findByEmployeePKCompanyIdAndEmployeePKDepartmentId(companyId, departmentId);
        ;
        employeeEntityList.forEach((l) -> {
            long salary = l.getSalary();
            l.setSalary(salary + ((increment * salary) / 100));
            updateDetails(l);
        });
    }

    public void updateEmployeeSalaryByPercentage(long companyId, long departmentId, long employeeId, long increment) {
        EmployeePK employeePK = new EmployeePK(employeeId, departmentId, companyId);
        List<EmployeeEntity> employeeEntityList = employeeRepository.findByEmployeePK(employeePK);
        employeeEntityList.forEach((l) -> {
            long salary = l.getSalary();
            l.setSalary(salary + ((increment * salary) / 100));
            updateDetails(l);
        });
    }
}




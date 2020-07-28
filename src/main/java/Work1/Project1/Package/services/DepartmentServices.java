package Work1.Project1.Package.services;

import Work1.Project1.Package.converter.DepartmentEntityListToResponseDeptList;
import Work1.Project1.Package.entity.*;
import Work1.Project1.Package.repository.CompanyRepository;
import Work1.Project1.Package.repository.DepartmentRepository;
import Work1.Project1.Package.repository.EmployeeRepository;
import Work1.Project1.Package.request.RequestUpdateDepartment;
import Work1.Project1.Package.response.ResponseError;
import Work1.Project1.Package.request.RequestDepartment;
import Work1.Project1.Package.response.ResponseDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static Work1.Project1.Package.constants.ApplicationConstants.*;

@Service
@Transactional      //delete request is not performed without this annotation
public class DepartmentServices {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    DepartmentEntityListToResponseDeptList departmentEntityListToResponseDeptList;

    public Object getAllDetails() {
            List<CompanyEntity> allCompanyEntity= companyRepository.findAllByIsActive(true);
            HashMap<Long,List<ResponseDepartment>> mapp= new HashMap<Long, List<ResponseDepartment> >();
            allCompanyEntity.forEach((l)->{
               List<DepartmentEntity> departmentEntities= departmentRepository.findAllByDepartmentPKCompanyIdAndIsActive(l.getCompanyId(),true);
               List<ResponseDepartment> responseDepartmentEntities= departmentEntityListToResponseDeptList.convert(departmentEntities);
                        mapp.put(l.getCompanyId() , responseDepartmentEntities);
                    }
                    );

          if(mapp.isEmpty())
              return null;
           return  mapp;
    }


    public String addDepartment(RequestDepartment requestDepartment) {
//
        long companyId=requestDepartment.getCompanyId();
        boolean companyPresent=companyRepository.existsById(companyId);
         if(!companyPresent)
             return Failed;
        String departmentName=requestDepartment.getDepartmentName();
        if(departmentRepository.existsByDepartmentPKCompanyIdAndDepartmentName(companyId ,departmentName))
        {
            DepartmentEntity departmentEntity= departmentRepository.findByDepartmentPKCompanyIdAndDepartmentName(companyId,departmentName);
             departmentEntity.setActive(true);
             departmentEntity.setManagerId(-1);
            departmentRepository.save(departmentEntity);
            return Add_Success;
        }
           long departmentId= departmentRepository.countByDepartmentPKCompanyId(requestDepartment.getCompanyId());
           DepartmentPK departmentPK = new DepartmentPK(companyId, departmentId);
           DepartmentEntity departmentEntity = new DepartmentEntity(departmentPK, departmentName,true,-1);
           this.departmentRepository.save(departmentEntity);
           return "Department with ID "+departmentId +" Successfully added!!";
    }

    public Object getDepartmentDetail(Long departmentId, Long companyId) {
        DepartmentPK departmentPK = new  DepartmentPK(companyId,departmentId);
            Optional<DepartmentEntity> departmentEntity=departmentRepository.findByDepartmentPKAndIsActive(departmentPK,true);
            if(!departmentEntity.isPresent())
               return "Department with id "+ departmentId + Not_Present;
            else
            {
                DepartmentEntity departmentEntity1=departmentEntity.get();
                return (new ResponseDepartment(departmentEntity1.getDepartmentPK().getCompanyId(),
                        departmentEntity1.getDepartmentPK().getDepartmentId(),departmentEntity1.getDepartmentName(),
                        departmentEntity1.getManagerId()));
            }

    }

    public Object deleteDepartmentDetails(DepartmentPK departmentPK) throws Exception{
        Optional<DepartmentEntity> departmentEntity=departmentRepository.findById(departmentPK);
        if(departmentEntity.isPresent()) {
            DepartmentEntity departmentEntity1 = departmentEntity.get();
            departmentEntity1.setActive(false);
            departmentEntity1.setManagerId(-1);
            departmentRepository.save(departmentEntity1);
            ResponseError responseError = new ResponseError(200, Delete_Success);
            return responseError;
        }
        else{
            ResponseError responseError = new ResponseError(204, Failed);
            return responseError;
        }

    }

    public ResponseError updateDetails(long companyId,long departmentId,String departmentName,long managerId) {

        DepartmentEntity updateDepartmentEntity=new DepartmentEntity( (new DepartmentPK(companyId,
        departmentId)), departmentName,true,managerId);
        Optional<DepartmentEntity> fetchedDepartmentEntity=departmentRepository.findById(updateDepartmentEntity.getDepartmentPK());
        if(fetchedDepartmentEntity.isPresent())
        {
            DepartmentEntity departmentEntity=fetchedDepartmentEntity.get();
            if(updateDepartmentEntity.getDepartmentName()==null)    //if update dept name not given so existing name
            {
                updateDepartmentEntity.setDepartmentName(departmentEntity.getDepartmentName());
            }
            if(updateDepartmentEntity.getManagerId()==-1)   //if updated manager id not given then existing
            {
                updateDepartmentEntity.setManagerId(departmentEntity.getManagerId());
            }
            departmentRepository.save(updateDepartmentEntity);
            ResponseError responseError =new ResponseError(200 , Update_Success);
            return responseError;
        }
        else
        {
            ResponseError responseError =new ResponseError(204 , Not_Present);
            return responseError;
        }

    }

    public List<DepartmentEntity> getAllDepartmentsOfCompany(long companyId) {
        return departmentRepository.findAllByDepartmentPKCompanyIdAndIsActive(companyId,true);
    }

}

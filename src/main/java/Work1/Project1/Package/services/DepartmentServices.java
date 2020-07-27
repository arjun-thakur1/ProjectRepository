package Work1.Project1.Package.services;

import Work1.Project1.Package.converter.DepartmentEntityListToResponseDeptList;
import Work1.Project1.Package.entity.*;
import Work1.Project1.Package.repository.CompanyRepository;
import Work1.Project1.Package.repository.DepartmentRepository;
import Work1.Project1.Package.repository.EmployeeRepository;
import Work1.Project1.Package.requestresponseobject.MyResponseEntity;
import Work1.Project1.Package.requestresponseobject.RequestDepartmentEntity;
import Work1.Project1.Package.requestresponseobject.ResponseDepartmentEntity;
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

            List<CompanyEntity> allCompanyEntity= companyRepository.findAll();
            HashMap<Long,List<ResponseDepartmentEntity>> mapp= new HashMap<Long, List<ResponseDepartmentEntity> >();  //l.getCompanyId()>

            allCompanyEntity.forEach((l)->{
               List<DepartmentEntity> departmentEntities= departmentRepository.findAllByDepartmentPKCompanyId(l.getCompanyId());
               List<ResponseDepartmentEntity> responseDepartmentEntities= departmentEntityListToResponseDeptList.convert(departmentEntities);
                        mapp.put(l.getCompanyId() , responseDepartmentEntities);
                    }
                    );

          if(mapp.isEmpty())
              return null;
           return  mapp;
    }


    public String addDepartment(RequestDepartmentEntity requestDepartmentEntity ) {
//
            DepartmentPK departmentPK = new DepartmentPK(requestDepartmentEntity.getCompanyId(),requestDepartmentEntity.getDepartmentId());
            String departmentName=requestDepartmentEntity.getDepartmentName();
            DepartmentEntity departmentEntity = new DepartmentEntity(departmentPK, departmentName);

        Long companyId= departmentEntity.getDepartmentPK().getCompanyId();
        boolean companyPresent=companyRepository.existsById(companyId);
        boolean departmentPresent=departmentRepository.existsById(departmentPK);
        if(departmentPresent)
            return Failed;
        if(companyPresent) {
            this.departmentRepository.save(departmentEntity);
            return "Department with ID "+departmentEntity.getDepartmentPK().getDepartmentId() +" Successfully added!!";
        }
        return Failed;
    }

    public Object getDepartmentDetail(Long departmentId, Long companyId) {

        DepartmentPK departmentPK = new  DepartmentPK(companyId,departmentId);
            Optional<DepartmentEntity> departmentEntity=departmentRepository.findById(departmentPK);
            if(!departmentEntity.isPresent())
               return "Department with id "+ departmentId + Not_Present;
            else
            {
                DepartmentEntity departmentEntity1=departmentEntity.get();
                return (new ResponseDepartmentEntity(departmentEntity1.getDepartmentPK().getCompanyId(),
                        departmentEntity1.getDepartmentPK().getDepartmentId(),departmentEntity1.getDepartmentName()));
            }

    }

    public Object deleteDepartmentDetails(DepartmentPK departmentPK) throws Exception{
        try {
        if(departmentRepository.existsById(departmentPK)) {
            this.departmentRepository.deleteByDepartmentPK(departmentPK); //deleteByDepartmentPK(departmentPK);
            MyResponseEntity myResponseEntity =new MyResponseEntity(200 , Delete_Success);
            return  myResponseEntity ;
           }
          else
            {
                MyResponseEntity myResponseEntity =new MyResponseEntity(204 , Not_Present);
                return  myResponseEntity ;
            }
       }catch(Exception e)
       {
         return Failed;
       }
    }

    public MyResponseEntity updateDetails(RequestDepartmentEntity requestDepartmentEntity) {
        DepartmentEntity updatedepartmentEntity=new DepartmentEntity( (new DepartmentPK(requestDepartmentEntity.getCompanyId(),
                requestDepartmentEntity.getDepartmentId())), requestDepartmentEntity.getDepartmentName());
        Optional<DepartmentEntity> fetcheddepartmentEntity=departmentRepository.findById(updatedepartmentEntity.getDepartmentPK());
        if(fetcheddepartmentEntity.isPresent())
        {
            DepartmentEntity departmentEntity=fetcheddepartmentEntity.get();
            if(updatedepartmentEntity.getDepartmentName()==null)
            {
                updatedepartmentEntity.setDepartmentName(departmentEntity.getDepartmentName());
            }
        }
        else
        {
            MyResponseEntity myResponseEntity =new MyResponseEntity(204 , Not_Present);
            return  myResponseEntity ;
        }
        departmentRepository.save(updatedepartmentEntity);

        MyResponseEntity myResponseEntity =new MyResponseEntity(200 , Update_Success);
        return  myResponseEntity ;
    }

    public List<DepartmentEntity> getAllDepartmentsOfCompany(long companyId) {
        return departmentRepository.findAllByDepartmentPKCompanyId(companyId);
    }

}

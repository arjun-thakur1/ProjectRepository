package Work1.Project1.Package.services;

import Work1.Project1.Package.entity.CompanyEntity;
import Work1.Project1.Package.entity.DepartmentEntity;
import Work1.Project1.Package.requestresponseobject.MyResponseEntity;
import Work1.Project1.Package.requestresponseobject.RequestCompanyEntity;
import Work1.Project1.Package.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static Work1.Project1.Package.constants.ApplicationConstants.*;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentServices departmentServices;

    public Object getAllDetails() {
       List<CompanyEntity>companyEntities= companyRepository.findAll();
       if(companyEntities.isEmpty())
           {
               MyResponseEntity myResponseEntity =new MyResponseEntity(200 , "No content!!");
               return  myResponseEntity ;
           }
       return companyEntities;
    }

    public Object addCompany(RequestCompanyEntity requestCompanyEntity) {

        if(companyRepository.existsByCompanyName(requestCompanyEntity.getCompanyName()))
          {
           return Duplicate_Name_Error;
          }
       else {
           CompanyEntity companyEntity=new CompanyEntity(requestCompanyEntity.getCompanyName(),requestCompanyEntity.getCeoName());
           try {
               this.companyRepository.save(companyEntity);
               return Add_Success +" "+Company_ID +companyEntity.getCompanyId();
           }catch(Exception e)
           {
               return Add_Failed;
           }
       }

    }



    public Object getCompanyDetails(Long id) {
        if(companyRepository.existsById(id)) {
            return companyRepository.findById(id);    //HttpStatus.OK
        }
        else {
                return null;
         }
    }



    public MyResponseEntity deleteCompanyDetails(Long company_id) {
        try {
                if (companyRepository.existsById(company_id)) {
                       this.companyRepository.deleteById(company_id);
                        MyResponseEntity myResponseEntity =new MyResponseEntity(200 , Delete_Success);
                        return  myResponseEntity ;

                }
                else{
                    MyResponseEntity myResponseEntity = new MyResponseEntity(204, Not_Present);
                    return myResponseEntity;
                }
        }catch (Exception e) {
            System.out.println(e);
            MyResponseEntity myResponseEntity =new MyResponseEntity(500 , Failed);
            return  myResponseEntity ;
        }
    }

    public String updateDetails(CompanyEntity updateCompanyEntity) {
       long companyId=updateCompanyEntity.getCompanyId();

        if(companyRepository.existsById(companyId)){
            Optional<CompanyEntity> companyEntity=companyRepository.findById(companyId);
          CompanyEntity fetchedCompanyEntity=companyEntity.get();//......................................
            if(updateCompanyEntity.getCompanyName()==null)
            {
                updateCompanyEntity.setCompanyName(fetchedCompanyEntity.getCompanyName());
            }
            if(updateCompanyEntity.getCeoName()==null)
            {
                updateCompanyEntity.setCeoName(fetchedCompanyEntity.getCeoName());
            }
            try {
                     companyRepository.save(updateCompanyEntity);
               } catch(Exception e){
                     return Duplicate_Name_Error;
               }
            return Update_Success;
        }
       return Not_Present;
    }

    public Object getCompanyCompleteDetails(long companyId) {

      Optional<CompanyEntity> companyEntity=  companyRepository.findById(companyId);
      if(companyEntity.isPresent())
      {
         List<DepartmentEntity> departmentEntityList= departmentServices.getAllDepartmentsOfCompany(companyId);
          if(departmentEntityList.isEmpty())
              return null;
          else
              return  departmentEntityList;
      }
      //return Not_Present;
        return  Not_Present;
    }



}

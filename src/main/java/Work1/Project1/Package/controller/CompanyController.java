package Work1.Project1.Package.controller;

import Work1.Project1.Package.entity.CompanyEntity;
import Work1.Project1.Package.requestresponseobject.MyResponseEntity;
import Work1.Project1.Package.requestresponseobject.RequestCompanyEntity;
import Work1.Project1.Package.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.serializer.DelegatingSerializer;
import org.springframework.web.bind.annotation.*;

import static Work1.Project1.Package.constants.ApplicationConstants.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;



    @RequestMapping(value = "/all" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllCompaniesDetails() {
          return   companyService.getAllDetails();
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCompanyDetailsById(@PathVariable("id") Long companyId) {
         Object companyDetail = companyService.getCompanyDetails(companyId);
         if(companyDetail==null){
            MyResponseEntity myResponseEntity =new MyResponseEntity(200 , Not_Present);
            return  myResponseEntity ;
        }
        else{
        return companyDetail;
        }
    }

    @DeleteMapping("/{companyId}")
    public MyResponseEntity deleteCompany(@PathVariable("companyId") Long companyId) {
        return companyService.deleteCompanyDetails(companyId);
    }

    @PutMapping("")
     public String updateCompanyDetails(@RequestBody  CompanyEntity companyEntity){   //CompanyEntity companyEntity ) {
        return companyService.updateDetails(companyEntity);
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addCompanyDetails(@RequestBody RequestCompanyEntity requestCompanyEntity) {
        return new ResponseEntity<> (companyService.addCompany(requestCompanyEntity), HttpStatus.OK);

    }

    @GetMapping (value="/complete-details")
    public Object getCompanyAllDepartmentDetails(@RequestParam("companyId") long companyId) {

        if (companyService.getCompanyDetails(companyId) == null) {
            MyResponseEntity myResponseEntity =new MyResponseEntity(200 , "Company not found!!");
            return  myResponseEntity ;
        }
        Object departmentEntities = companyService.getCompanyCompleteDetails(companyId);
        if (departmentEntities == null) {
            MyResponseEntity myResponseEntity = new MyResponseEntity(200, "No Department Present with given company Id!!");
            return myResponseEntity;
        }
        else
            return departmentEntities;

    }
}












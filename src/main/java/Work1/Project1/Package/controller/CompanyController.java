package Work1.Project1.Package.controller;

import Work1.Project1.Package.entity.CompanyEntity;
import Work1.Project1.Package.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import static Work1.Project1.Package.constants.ApplicationConstants.Add_Success;
import static Work1.Project1.Package.constants.ApplicationConstants.Update_Success;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;



    @GetMapping("")
    public void getAllCompaniesDetails() { //List<CompanyEntity>  chang must........

        try {
            companyService.getAllDetails();
            return;
        }catch (Exception e){
            System.out.println("....."+e);
            return ;
        }
    }

    @GetMapping(value="/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<CompanyEntity> getCompanyDetailsById(@RequestParam("companyId") Long companyId) {
        return companyService.getDetails(companyId);
    }

    @DeleteMapping("/{companyId}")
    public String deleteCompany(@PathVariable("companyId") Long companyId) {
        return companyService.deleteCompanyDetails(companyId);
    }

    @RequestMapping(value="/update-details",method = RequestMethod.PUT)
     public String updateCompanyDetails(@RequestBody CompanyEntity companyEntity ) {
        companyService.updateDetails(companyEntity);
        return Update_Success;
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addCompanyDetails(@RequestBody CompanyEntity companyEntity) {
        companyService.addCompany(companyEntity);
        return Add_Success;
    }

    @GetMapping (value="/complete-details")
    public Object getCompanyAllDepartmentDetails(@RequestParam("companyId") long companyId){
      return companyService.getCompanyCompleteDetails(companyId);
    }

}












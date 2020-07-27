package Work1.Project1.Package.controller;

import Work1.Project1.Package.entity.*;
import Work1.Project1.Package.requestresponseobject.MyResponseEntity;
import Work1.Project1.Package.requestresponseobject.RequestDepartmentEntity;
import Work1.Project1.Package.services.DepartmentServices;
import Work1.Project1.Package.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static Work1.Project1.Package.constants.ApplicationConstants.Update_Success;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentServices departmentService;

    @Autowired
    private EmployeeServices employeeServices;

    @GetMapping("/all")
    public Object getAllDepartmentsDetail() {

         Object departmentEntities = departmentService.getAllDetails();
        if(departmentEntities==null)
        {
            MyResponseEntity myResponseEntity =new MyResponseEntity(200 , "No content!!");
            return  myResponseEntity ;
        }
        else {
            return departmentEntities;
        }
    }

    @RequestMapping(value = "/company-id/{companyId}/department-id/{departmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getDepartmentDetails(@PathVariable("departmentId") Long departmentId,
                                       @PathVariable("companyId") Long companyId){

        return departmentService.getDepartmentDetail(departmentId, companyId);
    }

    @GetMapping(value="/all-employee-details")
     public List<EmployeeEntity> getAllEmployeeeOfDepartment(@RequestBody DepartmentPK departmentPK)
    {
        return employeeServices.getAllEmployeeOfDepartment(departmentPK);
    }

    @DeleteMapping("")
    public Object deleteDepartment(@RequestParam("departmentId") Long departmentId,
                                 @RequestParam("companyId") Long companyId) throws Exception {
        DepartmentPK departmentPK = new DepartmentPK(companyId,departmentId);
        return departmentService.deleteDepartmentDetails(departmentPK);

    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addDepartmentDetails(@RequestBody RequestDepartmentEntity requestDepartmentEntity) {

       return departmentService.addDepartment(requestDepartmentEntity);
    }


    @RequestMapping(value = "/update-details", method = RequestMethod.PUT)
    public MyResponseEntity updateDepartmentDetails(@RequestBody RequestDepartmentEntity departmentEntity) {

        return  departmentService.updateDetails(departmentEntity);
    }




    //completeOrginazation()

}

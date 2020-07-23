package Work1.Project1.Package.controller;

import Work1.Project1.Package.entity.DepartmentEntity;
import Work1.Project1.Package.entity.DepartmentPK;
import Work1.Project1.Package.entity.EmployeeEntity;
import Work1.Project1.Package.services.DepartmentServices;
import Work1.Project1.Package.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static Work1.Project1.Package.constants.ApplicationConstants.Add_Success;
import static Work1.Project1.Package.constants.ApplicationConstants.Update_Success;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentServices departmentService;

    @Autowired
    private EmployeeServices employeeServices;

    @GetMapping("/all")
    public List<DepartmentEntity> getAllDepartmentsDetail() {
        return departmentService.getAllDetails();
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<DepartmentEntity> getDepartmentDetails(@RequestParam("departmentId") Long departmentId,
                                                           @RequestParam("companyId") Long companyId) {

        return departmentService.getDepartmentDetail(departmentId, companyId);
    }

    @GetMapping(value="/all-employee-details")
     public List<EmployeeEntity> getAllEmployeeeOfDepartment(@RequestBody DepartmentPK departmentPK)
    {
        return employeeServices.getAllEmployeeOfDepartment(departmentPK);
    }

    @DeleteMapping("")
    public String deleteDepartment(@RequestParam("departmentId") Long departmentId,
                                 @RequestParam("companyId") Long companyId) throws Exception {
        DepartmentPK departmentPK = new DepartmentPK(departmentId, companyId);
        departmentService.deleteDepartmentDetails(departmentPK);
        return "Deleted successfully";
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addDepartmentDetails(@RequestBody DepartmentEntity departmentEntity) {

       return departmentService.addDepartment(departmentEntity);
    }


    @RequestMapping(value = "/update-details", method = RequestMethod.PUT)
    public String updateDepartmentDetails(@RequestBody DepartmentEntity departmentEntity) {

        departmentService.updateDetails(departmentEntity);
       return Update_Success;
    }




    //completeOrginazation()

}

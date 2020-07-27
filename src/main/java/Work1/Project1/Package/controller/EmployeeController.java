package Work1.Project1.Package.controller;


import Work1.Project1.Package.entity.EmployeeEntity;
import Work1.Project1.Package.entity.EmployeePK;
import Work1.Project1.Package.requestresponseobject.MyResponseEntity;
import Work1.Project1.Package.requestresponseobject.RequestEmployeeEntity;
import Work1.Project1.Package.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static Work1.Project1.Package.constants.ApplicationConstants.Not_Present;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServices employeeService;

    @GetMapping("")
    public Object getAllEmployeesDetail(@RequestParam(defaultValue = "0") Integer pageNo,
                                        @RequestParam(defaultValue = "2") Integer pageSize) {

        return employeeService.getAllDetails(pageNo,pageSize);
    }

    @GetMapping("/one")
    public Object getEmployeeDetail(@RequestBody EmployeePK employeePK) {

        Object empObject= employeeService.getEmployeeDetails(employeePK);
        if(empObject==null){
            MyResponseEntity myResponseEntity =new MyResponseEntity(200 , Not_Present);
            return  myResponseEntity ;
        }
        else{
            return empObject;
        }
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addEmployeeDetails(@RequestBody RequestEmployeeEntity requestEmployeeEntity ){//  EmployeeEntity employeeEntity) {

        return employeeService.addEmployee(requestEmployeeEntity);
    }

    @DeleteMapping("")
    public String deleteEmployee(@RequestParam("employeeId") Long employeeId,
                                 @RequestParam("departmentId") Long departmentId,
                                 @RequestParam("companyId") Long companyId) throws Exception {
        EmployeePK employeePK = new EmployeePK( companyId,departmentId,employeeId);
        return employeeService.deleteEmployeeDetails(employeePK);

    }

     //.....................
    @RequestMapping(value = "/update-details", method = RequestMethod.PUT)
    public void updateDepartmentDetails(@RequestBody EmployeeEntity employeeEntity) {

        employeeService.updateDetails(employeeEntity);
    }

    @RequestMapping(value = "/update-salary", method = RequestMethod.PUT)
    public void updateSalary(@RequestParam("companyId") Long companyId,
                             @RequestParam(value = "departmentId") Long departmentId,
                             @RequestParam(value = "employeeId") Long employeeId,
                             @RequestParam("increment") Long increment,
                             @RequestParam("flag") boolean flag ) {
        if(flag)
        {
            employeeService.updateSalaryByAbsoluteValue(companyId,departmentId,employeeId,increment);
            return;
        }
        else
        {
            employeeService.updateSalaryByPercentage(companyId,departmentId,employeeId,increment);
            return;
        }
    }






}
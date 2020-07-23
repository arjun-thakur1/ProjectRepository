package Work1.Project1.Package.controller;


import Work1.Project1.Package.entity.EmployeeEntity;
import Work1.Project1.Package.entity.EmployeePK;
import Work1.Project1.Package.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServices employeeService;

    @GetMapping("/all")
    public List<EmployeeEntity> getAllEmployeesDetail() {
        return employeeService.getAllDetails();
    }

    @GetMapping("")
    public List<EmployeeEntity> getEmployeesDetail(@RequestBody EmployeePK employeePK) {

        return employeeService.getEmployeeDetails(employeePK);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addEmployeeDetails(@RequestBody EmployeeEntity employeeEntity) {

        return employeeService.addEmployee(employeeEntity);
    }

    @DeleteMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") Long employeeId,
                                 @RequestParam("departmentId") Long departmentId,
                                 @RequestParam("companyId") Long companyId) throws Exception {
        EmployeePK employeePK = new EmployeePK(employeeId, departmentId, companyId);
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
package Work1.Project1.Package.converter;

import Work1.Project1.Package.entity.DepartmentEntity;
import Work1.Project1.Package.entity.EmployeeEntity;
import Work1.Project1.Package.requestresponseobject.ResponseDepartmentEntity;
import Work1.Project1.Package.requestresponseobject.ResponseEmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EmployeeEntityListToResponseEmpList {
    public List<ResponseEmployeeEntity> convert(List<EmployeeEntity> employeeEntities)
    {
        List<ResponseEmployeeEntity> responseEmployeeEntities = new ArrayList<ResponseEmployeeEntity>();
        employeeEntities.forEach((l)->{
            responseEmployeeEntities.add(new ResponseEmployeeEntity(l.getEmployeePK().getCompanyId(),l.getEmployeePK().getDepartmentId(),
                    l.getEmployeePK().getEmployeeId(), l.getEmpName(),l.getPhone(),l.getSalary())); });

        return responseEmployeeEntities;
    }
}

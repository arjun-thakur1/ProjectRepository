package Work1.Project1.Package.converter;

import Work1.Project1.Package.entity.DepartmentEntity;
import Work1.Project1.Package.requestresponseobject.ResponseDepartmentEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DepartmentEntityListToResponseDeptList {

    public List<ResponseDepartmentEntity> convert(List<DepartmentEntity> departmentEntityList)
    {
        List<ResponseDepartmentEntity> responseDepartmentEntities = new ArrayList<ResponseDepartmentEntity>();
       departmentEntityList.forEach((l)->{
          responseDepartmentEntities.add(new ResponseDepartmentEntity(l.getDepartmentPK().getCompanyId(),
                   l.getDepartmentPK().getDepartmentId(), l.getDepartmentName()));
       });

        return responseDepartmentEntities;
    }
}

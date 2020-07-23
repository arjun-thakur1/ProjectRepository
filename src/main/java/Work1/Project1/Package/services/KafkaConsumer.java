package Work1.Project1.Package.services;
import Work1.Project1.Package.entity.EmployeeEntity;
import Work1.Project1.Package.entity.EmployeePK;
import Work1.Project1.Package.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import Work1.Project1.Package.entity.EmployeeEntity;
import Work1.Project1.Package.entity.EmployeePK;
import Work1.Project1.Package.repository.EmployeeRepository;
import java.io.IOException;

//@Service
public class KafkaConsumer {


/*
    @Autowired
    EmployeeServices employeeServices;

    @KafkaListener(topics = "test",  groupId="group_id",
            containerFactory = "userKafkaListenerFactory")
    public void consumeJson(EmployeeEntity employeeEntity) {

        employeeServices.addEmployee(employeeEntity);
        }
*/

}


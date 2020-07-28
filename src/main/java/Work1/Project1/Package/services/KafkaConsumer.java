package Work1.Project1.Package.services;
import Work1.Project1.Package.request.RequestEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {


    @Autowired
    EmployeeServices employeeServices;

    @KafkaListener(topics = "testTopic",  groupId="group_id",containerFactory = "employeeEntityKafkaListenerContainerFactory")
    public void consumeJson(RequestEmployee requestEmployee) {

        employeeServices.addEmployee(requestEmployee);
    }

}


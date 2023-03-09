package com.example.employeeManager;


import com.example.employeeManager.entity.Employee;
import com.example.employeeManager.entity.IncrementLogs;
import com.example.employeeManager.exception.InvalidSalary;
import com.example.employeeManager.repository.EmployeeRepo;
import com.example.employeeManager.repository.IncrementLogsRepo;
import com.example.employeeManager.service.EmployeeServiceImp;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeManagerApplicationTests {
  @InjectMocks
  private EmployeeServiceImp employeeServiceImp;

  @Mock
  private EmployeeRepo employeeRepo;

  @Mock
  private IncrementLogsRepo incrementLogsRepo;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private WebClient webClient;

  @Test
  public void getAllEmpTest() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    List<Employee> emp =
        objectMapper.readValue(new File("./src/test/resources/employee.json"),
            new TypeReference<List<Employee>>() {});

    Mockito.when(employeeRepo.findAll()).thenReturn(emp);

    assertEquals(emp,employeeServiceImp.getAllEmpDetails());


  }

  @Test
  public void getEmpByIDTest() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Employee emp =
        objectMapper.readValue(new File("./src/test/resources/employee_1.json"),
           Employee.class);
    when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));
    Assertions.assertEquals( employeeServiceImp.getEmpDetailById(1),emp);
  }

  @Test
  public void createEmpTest() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Employee emp =
        objectMapper.readValue(new File("./src/test/resources/employee_1.json"),
            Employee.class);

    Employee empNew = new Employee();
    empNew.setId(1);
    empNew.setName("Rajan");
    empNew.setSalary(100);
    empNew.setDesignation("Developer");
    when(employeeRepo.save(any())).thenReturn(empNew);
    Employee savedEmployee = employeeServiceImp.createNewEmp(emp);
    Assertions.assertEquals(savedEmployee,emp);
  }

  @Test
  public void updEmpTest() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Employee emp =
        objectMapper.readValue(new File("./src/test/resources/employee_1.json"),
            Employee.class);

    IncrementLogs logs =
        objectMapper.readValue(new File("./src/test/resources/incrementLogs.json"),
            IncrementLogs.class);
    int prevSal = emp.getSalary();
    when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));
    Employee empNew = new Employee();
    empNew.setId(1);
    empNew.setName("Rajan");
    empNew.setSalary(500);
    empNew.setDesignation("Developer");

    emp.setSalary(empNew.getSalary());
    when(employeeRepo.save(any())).thenReturn(emp);

    IncrementLogs incLog = new IncrementLogs();
    incLog.setId(1);
    incLog.setEmpID(1);
    incLog.setPrevSalary(prevSal);
    incLog.setCurrSalary(empNew.getSalary());

    when(incrementLogsRepo.save(any())).thenReturn(incLog);

    Employee savedEmployee = employeeServiceImp.updateEmpById(1,emp);
    Assertions.assertEquals(savedEmployee,empNew);
    Assertions.assertEquals(incLog,logs);
  }

  @Disabled
  @Test
  public void updEmpTestThrowsException() throws InvalidSalary,IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Employee emp =
        objectMapper.readValue(new File("./src/test/resources/employee_1.json"),
            Employee.class);


  }
  @Test
  public void delEmpById() throws IOException{
    ObjectMapper objectMapper = new ObjectMapper();
    Employee emp =
        objectMapper.readValue(new File("./src/test/resources/employee_1.json"),
            Employee.class);
    Mockito.when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));
    Mockito.doNothing().when(employeeRepo).delete(any());
    String msg = employeeServiceImp.deleteEmpById(1);
    Assertions.assertNotNull(msg);

  }


  @Test
  public void getIncEmpTest() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Employee emp =
        objectMapper.readValue(new File("./src/test/resources/employee_1.json"),
            Employee.class);
    Mockito
        .when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));
    Mockito
        .when(restTemplate.postForEntity(anyString(),any(),any()))
          .thenReturn(new ResponseEntity(emp, HttpStatus.OK));
    Employee empNew = employeeServiceImp.getEmpIncrementById(1);

    assertEquals(empNew,emp);

  }

  @Disabled
  @Test
  public void updIncEmpTest() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Employee emp =
        objectMapper.readValue(new File("./src/test/resources/employee_1.json"),
            Employee.class);
    Mockito
        .when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));

    Mockito
        .when(webClient.post().uri(anyString()).bodyValue(any()).retrieve()
            .bodyToMono(Employee.class)
            .block())
        .thenReturn(emp);
    Mockito
        .when(employeeServiceImp.updateEmpById(anyInt(),any()))
        .thenReturn(emp);
    Employee empNew = employeeServiceImp.getEmpIncrementById(1);

    assertEquals(empNew,emp);

  }


}
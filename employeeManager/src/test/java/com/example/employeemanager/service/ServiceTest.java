package com.example.employeemanager.service;

import com.example.employeemanager.controller.Controller;
import com.example.employeemanager.entity.Employee;
import com.example.employeemanager.entity.IncrementLogs;
import com.example.employeemanager.exception.InvalidSalary;
import com.example.employeemanager.handler.ResponseHandler;
import com.example.employeemanager.repository.EmployeeRepo;
import com.example.employeemanager.repository.IncrementLogsRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
  @InjectMocks
  private EmployeeServiceImp employeeServiceImp;

  @InjectMocks
  private ResponseHandler responseHandler;

  @Mock
  private EmployeeRepo employeeRepo;

  @Mock
  private IncrementLogsRepo incrementLogsRepo;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private WebClient webClient;

  @Mock
  private EmployeeServiceImp service;

  @InjectMocks
  private Controller controller;

  IncrementLogs logs;
  Employee emp;
  List<Employee> empList;

  @BeforeEach
  public void setup() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    empList =
        objectMapper.readValue(new File("./src/test/resources/employee.json"),
            new TypeReference<List<Employee>>() {});

    emp =
        objectMapper.readValue(new File("./src/test/resources/employee_1.json"),
            Employee.class);

    logs =
        objectMapper.readValue(new File("./src/test/resources/incrementLogs.json"),
            IncrementLogs.class);
  }

  @Test
  public void getAllEmpTest() {
    Mockito.when(employeeRepo.findAll()).thenReturn(empList);

    assertEquals(empList,employeeServiceImp.getAllEmpDetails());
  }

  @Test
  public void getEmpByIDTest()  {

    when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));
    Assertions.assertEquals( employeeServiceImp.getEmpDetailById(1),emp);
  }

  @Test
  public void createEmpTest() throws IOException {
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
    int prevSal = emp.getSalary();
    when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));
    Employee empNew = new Employee();
    empNew.setId(1);
    empNew.setName("Rajan");
    empNew.setSalary(500);
    empNew.setDesignation("Developer");

    when(employeeRepo.save(any())).thenReturn(emp);

    IncrementLogs incLog = new IncrementLogs();
    incLog.setId(1);
    incLog.setEmpID(1);
    incLog.setPrevSalary(prevSal);
    incLog.setCurrSalary(empNew.getSalary());

    when(incrementLogsRepo.save(any())).thenReturn(incLog);

    Employee savedEmployee = employeeServiceImp.updateEmpById(1,empNew);
    Assertions.assertEquals(savedEmployee,empNew);
    Assertions.assertEquals(incLog,logs);
  }

  @Test
  public void updEmpTestThrowsException() throws InvalidSalary,IOException {
    when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));
    Employee empNew = new Employee();
    empNew.setId(1);
    empNew.setName("Rajan");
    empNew.setSalary(50);
    empNew.setDesignation("Developer");
    when(employeeRepo.save(any())).thenReturn(emp);
    Assertions.assertThrows(InvalidSalary.class,
        () -> employeeServiceImp.updateEmpById(1,empNew));
  }
  @Test
  public void delEmpById() throws IOException{
    Mockito.when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));
    Mockito.doNothing().when(employeeRepo).delete(any());
    String msg = employeeServiceImp.deleteEmpById(1);
    Assertions.assertNotNull(msg);

  }


  @Test
  public void getIncEmpTest() throws IOException {
    Mockito
        .when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));
    Mockito
        .when(restTemplate.postForEntity(anyString(),any(),any()))
        .thenReturn(new ResponseEntity(emp, HttpStatus.OK));
    Employee empNew = employeeServiceImp.getEmpIncrementById(1);

    assertEquals(empNew,emp);

  }

  @Test
  public void responseHandlerTest() {
    Map<String,Object> map = new HashMap<>();
    map.put("message", "Sucess");
    map.put("status", HttpStatus.OK);
    map.put("data", emp);
    Assertions.assertNotNull(responseHandler.generateResponse("Sucess",HttpStatus.OK,emp));
  }

  @Disabled
  @Test
  public void updIncEmpTest() throws IOException {
    Mockito
        .when(employeeRepo.findById(anyInt())).thenReturn(Optional.ofNullable(emp));

    /** webClient = WebClient.builder()
     .exchangeFunction(clientRequest ->
     Mono.just(ClientResponse.
     .header("content-type", "application/json")
     .body("{ \"key\" : \"value\"}")
     .build())
     ).build();**/

    Employee empNew = employeeServiceImp.incrementEmpSalaryById(1);

    assertNotNull(empNew);
  }
}

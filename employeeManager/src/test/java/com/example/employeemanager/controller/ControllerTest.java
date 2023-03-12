package com.example.employeemanager.controller;

import com.example.employeemanager.entity.Employee;
import com.example.employeemanager.handler.ResponseHandler;
import com.example.employeemanager.service.EmployeeServiceImp;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
  @Mock
  EmployeeServiceImp employeeService;

  @InjectMocks
  Controller controller;

  @Mock
  ResponseHandler responseHandler;

  Employee emp;
  Map<String,Object> map;

  @BeforeEach
  public  void setUp(){
    emp = new Employee();
    emp.setId(1);
    emp.setName("Asmita");
    emp.setSalary(100);
    emp.setDesignation("Developer");

    map = new HashMap<String, Object>();
    map.put("message", "SOME_MSG");
    map.put("status", HttpStatus.OK);
    map.put("data", emp);
    when(responseHandler.generateResponse(Mockito.anyString()
            ,Mockito.any(HttpStatus.class),Mockito.any()))
        .thenReturn(new ResponseEntity<>(map,HttpStatus.MULTI_STATUS));
  }


  @Test
  public void whenGetAllEmployeeThenReturnAllEmployeesTest() throws Exception {
    when(employeeService.getAllEmpDetails()).thenReturn(Arrays.asList(emp));
    assertNotNull(controller.getAllEmp());
    assertEquals(new ResponseEntity<>(map,HttpStatus.MULTI_STATUS),controller.getAllEmp());
  }
  @Test
  public void whenGetAllEmployeeByIDThenReturnEmp() throws Exception {
    when(employeeService.getEmpDetailById(Mockito.anyInt()))
        .thenReturn(emp);
    assertNotNull(controller.getEmpByID(1));
    assertEquals(new ResponseEntity<>(map,HttpStatus.MULTI_STATUS),controller.getEmpByID(1));

  }
  @Test
  public void createEmployeeTest() throws Exception {
    when(employeeService.createNewEmp(Mockito.any()))
        .thenReturn(emp);
    assertNotNull(controller.createEmp(emp));
    assertEquals(new ResponseEntity<>(map,HttpStatus.MULTI_STATUS),controller.createEmp(emp));
  }
  @Test
  public void updateEmployeeTest() throws Exception {
    when(employeeService.updateEmpById(Mockito.anyInt(),Mockito.any()))
        .thenReturn(emp);
    assertNotNull(controller.updateEmp(1,emp));
    assertEquals(new ResponseEntity<>(map,HttpStatus.MULTI_STATUS),controller.updateEmp(1,emp));

  }
  @Test
  public void deleteEmployeeTest() throws Exception {
    when(employeeService.deleteEmpById(Mockito.anyInt()))
        .thenReturn("SOME_MSG");
    assertNotNull(controller.deleteEmp(1));
    assertEquals(new ResponseEntity<>(map,HttpStatus.MULTI_STATUS),controller.deleteEmp(1));
  }
  @Test
  public void getEmployeeIncrementByRestTemplateTest() throws Exception {
    when(employeeService.getEmpIncrementById(Mockito.anyInt()))
        .thenReturn(emp);
    assertNotNull(controller.getEmpSalInc(1));
    assertEquals(new ResponseEntity<>(map,HttpStatus.MULTI_STATUS),controller.getEmpSalInc(1));

  }
  @Disabled
  @Test
  public void webController() throws Exception {
    when(employeeService.incrementEmpSalaryById(Mockito.any()))
        .thenReturn(emp);
    assertNotNull(controller.updateEmpSal(1));
  }
}

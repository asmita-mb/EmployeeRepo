package com.example.employeeManager.controller;


import com.example.employeeManager.entity.Employee;
import com.example.employeeManager.service.EmployeeService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api")
public class Controller {

  @Autowired
  EmployeeService employeeService;


  //Get all the employees present in the database
  @GetMapping("/employees")
  ResponseEntity<List<Employee>> getAllEmp() {
    MDC.put("TxId", UUID.randomUUID().toString());
    log.info("Event Name:{}, Session ID {} ", "GetAllEmployeesDetails", MDC.get("TxId"));
    List<Employee> employees = employeeService.getAllEmpDetails();
    log.info("All the employee details were retrieved successfully." +
        "Event Name:{}, Session ID {} ", "GetAllEmployeesDetails", MDC.get("TxId"));
    MDC.clear();
    return new ResponseEntity<>(employees, HttpStatus.OK);
  }

  //Get the employee details with the given ID
  @GetMapping("/employee/{id}")
  ResponseEntity<Employee> getEmpByID(@PathVariable int id){
    MDC.put("TxId", UUID.randomUUID().toString());
    log.info("Event Name:{}, Session ID {} ", "GetAllEmployeesDetailsByID", MDC.get("TxId"));
    Employee employee = employeeService.getEmpDetailById(id);
    log.info("Employee details with ID {} retrieved successfully." +
        "Event Name:{}, Session ID {} ",id, "GetAllEmployeesDetails", MDC.get("TxId"));
    MDC.clear();
    return new ResponseEntity<>(employee,HttpStatus.OK);
  }

  //Create a new employee record in the database
  @PostMapping("/employee")
  ResponseEntity<Employee> createEmp(@RequestBody Employee emp){
    MDC.put("TxId", UUID.randomUUID().toString());
    log.info("Event Name:{}, Session ID {} ", "CreateNewEmployee", MDC.get("TxId"));
    Employee empNew = employeeService.createNewEmp(emp);
    log.info("New Employee with ID {} Created Successfully." +
        "Event Name:{}, Session ID {} ",empNew.getId(), "GetAllEmployeesDetails", MDC.get("TxId"));
    MDC.clear();
    return new ResponseEntity<>(empNew,HttpStatus.OK);
  }

  //Update Existing employee detail
  @PutMapping("/employee/{id}")
  ResponseEntity<Employee> updateEmp(@PathVariable int id,@RequestBody Employee emp){
    Employee empNew = employeeService.updateEmpById(id,emp);
    log.info("New Employee with ID {} Created Successfully." +
        "Event Name:{}, Session ID {} ",empNew.getId(), "GetAllEmployeesDetails", MDC.get("TxId"));
    MDC.clear();
    return new ResponseEntity<>(empNew,HttpStatus.OK);
  }


}

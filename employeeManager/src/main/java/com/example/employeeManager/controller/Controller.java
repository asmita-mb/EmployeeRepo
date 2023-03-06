package com.example.employeeManager.controller;


import com.example.employeeManager.entity.Employee;
import com.example.employeeManager.handler.ResponseHandler;
import com.example.employeeManager.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class Controller {

  @Autowired
  EmployeeService employeeService;


  //Get all the employees present in the database
  @GetMapping("/employees")
  ResponseEntity<Object> getAllEmp() {
    try {
    MDC.put("TxId", UUID.randomUUID().toString());
    log.info("Event Name:{}, Session ID {} ", "GetAllEmployeesDetails", MDC.get("TxId"));
      Iterable<Employee> employees = employeeService.getAllEmpDetails();
      log.info("All the employee details were retrieved successfully." +
          "Event Name:{}, Session ID {} ", "GetAllEmployeesDetails", MDC.get("TxId"));
      MDC.clear();
      return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, employees);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);

    }
  }

  //Get the employee details with the given ID
  @GetMapping("/employee/{id}")
  ResponseEntity<Object> getEmpByID(@PathVariable int id){
    try {
    MDC.put("TxId", UUID.randomUUID().toString());
    log.info("Event Name:{}, Session ID {} ", "GetEmployeesDetailsByID", MDC.get("TxId"));
    Employee employee = employeeService.getEmpDetailById(id);

    log.info("Employee details with ID {} retrieved successfully." +
        "Event Name:{}, Session ID {} ",id, "GetEmployeesDetailsByID", MDC.get("TxId"));
    MDC.clear();

    return ResponseHandler.generateResponse("Employee Detail retrieved Successfully",HttpStatus.OK,employee);
  } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS,null);
    }
  }

  //Create a new employee record in the database
  @PostMapping("/employee")
  ResponseEntity<Object> createEmp(@RequestBody Employee emp){
    try {
      MDC.put("TxId", UUID.randomUUID().toString());
      log.info("Event Name:{}, Session ID {} ", "CreateNewEmployee", MDC.get("TxId"));

      Employee empNew = employeeService.createNewEmp(emp);
      log.info("New Employee with ID {} Created Successfully." +
          "Event Name:{}, Session ID {} ", empNew.getId(), "CreateNewEmployee", MDC.get("TxId"));
      MDC.clear();
      return ResponseHandler.generateResponse("Employee created successfully",
          HttpStatus.OK,empNew);
    } catch (Exception e){

      return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS,null);
    }
  }

  //Update Existing employee detail
  @PutMapping("/employee/{id}")
  ResponseEntity<Object> updateEmp(@PathVariable int id,@RequestBody Employee emp){
    try {
      MDC.put("TxId", UUID.randomUUID().toString());
      log.info("Event Name:{}, Session ID {} ", "UpdateEmployeeDetail", MDC.get("TxId"));
      Employee empNew = employeeService.updateEmpById(id, emp);
      log.info("Employee with ID {} Updated Successfully." +
          "Event Name:{}, Session ID {} ", empNew.getId(), "UpdateEmployeeDetail", MDC.get("TxId"));
      MDC.clear();
      return ResponseHandler.generateResponse("Employee updated successfully",HttpStatus.OK,empNew);
    } catch (Exception e){
      return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS,null);
    }
  }

  //Delete the Existing employee with ID
  @DeleteMapping("/employee/{id}")
  ResponseEntity<Object> deleteEmp(@PathVariable int id){
    try {
      MDC.put("TxId", UUID.randomUUID().toString());
      log.info("Event Name:{}, Session ID {} ", "DeleteExistingEmployee", MDC.get("TxId"));
      String empMsg = employeeService.deleteEmpById(id);
      log.info("Employee with ID {} Deleted Successfully." +
          "Event Name:{}, Session ID {} ", id, "DeleteExistingEmployee", MDC.get("TxId"));
      MDC.clear();
      return ResponseHandler.generateResponse("Employee Deleted Successfully",HttpStatus.OK,null);
    } catch (Exception e){
      return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS,null);
    }
  }

  //Get Increment of Salary for the Employee
  @GetMapping("/employee/increment/{id}")
  ResponseEntity<Object> getEmpSalInc(@PathVariable int id){
    try {
      MDC.put("TxId", UUID.randomUUID().toString());
      log.info("Event Name:{}, Session ID {} ", "GetSalaryIIncrement", MDC.get("TxId"));
      Employee emp = employeeService.getEmpIncrementById(id);
      log.info("Increment of Employee with ID {} retrieved Successfully." +
          "Event Name:{}, Session ID {} ", id, "GetSalaryIIncrement", MDC.get("TxId"));
      MDC.clear();
      return ResponseHandler.generateResponse("Increment Retrived Successfully",HttpStatus.OK,emp);
    } catch (Exception e){
      return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS,null);
    }
  }

  //Increment Salary Of the Employee
  @PutMapping("/employee/increment/{id}")
  ResponseEntity<Object> updateEmpSal(@PathVariable int id){
    try {
      MDC.put("TxId", UUID.randomUUID().toString());
      log.info("Event Name:{}, Session ID {} ", "UpdateSalaryWithIncrement", MDC.get("TxId"));
      Employee emp = employeeService.incrementEmpSalaryById(id);
      log.info("Salary increased to {} for Employee with ID {}." +
              "Event Name:{}, Session ID {} ", emp.getSalary(), emp.getId(),
          "UpdateSalaryWithIncrement", MDC.get("TxId"));
      MDC.clear();
      return ResponseHandler.generateResponse("Employee Increment added successfully",
          HttpStatus.OK,emp);
    } catch (Exception e){
      return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS,null);
    }
  }


}

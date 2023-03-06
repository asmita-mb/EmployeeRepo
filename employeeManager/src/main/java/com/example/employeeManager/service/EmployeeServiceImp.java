package com.example.employeeManager.service;

import com.example.employeeManager.entity.Employee;
import com.example.employeeManager.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService{

  @Autowired
  EmployeeRepo employeeRepo;

  @Autowired
  RestTemplate restTemplate;

  @Override
  public List<Employee> getAllEmpDetails() {
    return (List<Employee>) employeeRepo.findAll();
  }

  @Override
  public Employee getEmpDetailById(int id) {
    return employeeRepo.findById(id).get();
  }

  @Override
  public Employee createNewEmp(Employee emp) {
    return employeeRepo.save(emp);
  }

  @Override
  public Employee updateEmpById(int id, Employee emp) {

    Optional<Employee> empData = employeeRepo.findById(id);
    if(empData.isPresent()){

      Employee empNew = empData.get();
      empNew.setName(emp.getName());
      empNew.setDesignation(emp.getDesignation());
      empNew.setSalary(emp.getSalary());
      employeeRepo.save(empNew);
      return empNew;
    }
      return employeeRepo.findById(id).get();
  }

  @Override
  public String deleteEmpById(int id) {
    Employee empData = employeeRepo.findById(id).get();
      employeeRepo.delete(empData);
      return "Employee Deleted Successfully";
  }

  @Override
  public Employee getEmpIncrementById(int id) {
    Employee emp = employeeRepo.findById(id).get();

     ResponseEntity<Employee> response
          = restTemplate.getForEntity("http://localhost:8082/api/incremet",Employee.class,emp);
     emp = response.getBody();
    return emp;
  }

  @Override
  public Employee incrementEmpSalaryById(int id) {
    Employee emp = employeeRepo.findById(id).get();

    return emp;
  }
}

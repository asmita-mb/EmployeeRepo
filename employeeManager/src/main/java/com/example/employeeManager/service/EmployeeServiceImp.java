package com.example.employeeManager.service;

import com.example.employeeManager.entity.Employee;
import com.example.employeeManager.entity.IncrementLogs;
import com.example.employeeManager.exception.InvalidSalary;
import com.example.employeeManager.repository.EmployeeRepo;
import com.example.employeeManager.repository.IncrementLogsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeServiceImp implements EmployeeService{
  String incrementUrl = "http://localhost:8082/api/increment";

  @Autowired
  EmployeeRepo employeeRepo;

  @Autowired
  IncrementLogsRepo incrementLogsRepo;

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  WebClient webClient;

  @Override
  public List<Employee> getAllEmpDetails() {

    return (List<Employee>) employeeRepo.findAll();
  }

  @Override
  @Cacheable("Emp")
  public Employee getEmpDetailById(int id) {

    return employeeRepo.findById(id).get();
  }

  @Override
  public Employee createNewEmp(Employee emp) {

    return employeeRepo.save(emp);
  }

  @Override
  @CachePut("Emp")
  @Transactional
  public Employee updateEmpById(int id, Employee emp) {

    Optional<Employee> empData = employeeRepo.findById(id);
    if(empData.isPresent()){

      int prevSal = empData.get().getSalary();
      Employee empNew = empData.get();
      empNew.setName(emp.getName());
      empNew.setDesignation(emp.getDesignation());
      empNew.setSalary(emp.getSalary());
      employeeRepo.save(empNew);

      if(empNew.getSalary()<prevSal){
        throw new InvalidSalary("Salary Should be more than Previous Salary!!");
      }

      IncrementLogs incrementLogs = new IncrementLogs();
      incrementLogs.setEmpID(empNew.getId());
      incrementLogs.setPrevSalary(prevSal);
      incrementLogs.setCurrSalary(emp.getSalary());
      incrementLogsRepo.save(incrementLogs);
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
          = restTemplate.postForEntity(incrementUrl,emp,Employee.class);
     emp = response.getBody();
    return emp;
  }

  @Override
  public Employee incrementEmpSalaryById(int id) {
    Employee emp = employeeRepo.findById(id).get();

    Employee empNew= webClient
        .post()
        .uri(incrementUrl)
        .bodyValue(emp)
        .retrieve()
        .bodyToMono(Employee.class)
        .block();

    return updateEmpById(id,empNew);
  }
}
